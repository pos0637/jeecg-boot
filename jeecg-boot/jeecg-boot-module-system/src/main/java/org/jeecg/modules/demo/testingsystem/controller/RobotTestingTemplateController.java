package org.jeecg.modules.demo.testingsystem.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.testingsystem.entity.RobotTestingTemplateItem;
import org.jeecg.modules.demo.testingsystem.entity.RobotTestingTemplate;
import org.jeecg.modules.demo.testingsystem.vo.RobotTestingTemplatePage;
import org.jeecg.modules.demo.testingsystem.service.IRobotTestingTemplateService;
import org.jeecg.modules.demo.testingsystem.service.IRobotTestingTemplateItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 检测模板
 * @Author: jeecg-boot
 * @Date:   2020-04-14
 * @Version: V1.0
 */
@Api(tags="检测模板")
@RestController
@RequestMapping("/testingsystem/robotTestingTemplate")
@Slf4j
public class RobotTestingTemplateController {
	@Autowired
	private IRobotTestingTemplateService robotTestingTemplateService;
	@Autowired
	private IRobotTestingTemplateItemService robotTestingTemplateItemService;
	
	/**
	 * 分页列表查询
	 *
	 * @param robotTestingTemplate
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "检测模板-分页列表查询")
	@ApiOperation(value="检测模板-分页列表查询", notes="检测模板-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(RobotTestingTemplate robotTestingTemplate,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<RobotTestingTemplate> queryWrapper = QueryGenerator.initQueryWrapper(robotTestingTemplate, req.getParameterMap());
		Page<RobotTestingTemplate> page = new Page<RobotTestingTemplate>(pageNo, pageSize);
		IPage<RobotTestingTemplate> pageList = robotTestingTemplateService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param robotTestingTemplatePage
	 * @return
	 */
	@AutoLog(value = "检测模板-添加")
	@ApiOperation(value="检测模板-添加", notes="检测模板-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody RobotTestingTemplatePage robotTestingTemplatePage) {
		RobotTestingTemplate robotTestingTemplate = new RobotTestingTemplate();
		BeanUtils.copyProperties(robotTestingTemplatePage, robotTestingTemplate);
		robotTestingTemplateService.saveMain(robotTestingTemplate, robotTestingTemplatePage.getRobotTestingTemplateItemList());
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param robotTestingTemplatePage
	 * @return
	 */
	@AutoLog(value = "检测模板-编辑")
	@ApiOperation(value="检测模板-编辑", notes="检测模板-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody RobotTestingTemplatePage robotTestingTemplatePage) {
		RobotTestingTemplate robotTestingTemplate = new RobotTestingTemplate();
		BeanUtils.copyProperties(robotTestingTemplatePage, robotTestingTemplate);
		RobotTestingTemplate robotTestingTemplateEntity = robotTestingTemplateService.getById(robotTestingTemplate.getId());
		if(robotTestingTemplateEntity==null) {
			return Result.error("未找到对应数据");
		}
		robotTestingTemplateService.updateMain(robotTestingTemplate, robotTestingTemplatePage.getRobotTestingTemplateItemList());
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "检测模板-通过id删除")
	@ApiOperation(value="检测模板-通过id删除", notes="检测模板-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		robotTestingTemplateService.delMain(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "检测模板-批量删除")
	@ApiOperation(value="检测模板-批量删除", notes="检测模板-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.robotTestingTemplateService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "检测模板-通过id查询")
	@ApiOperation(value="检测模板-通过id查询", notes="检测模板-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		RobotTestingTemplate robotTestingTemplate = robotTestingTemplateService.getById(id);
		if(robotTestingTemplate==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(robotTestingTemplate);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "检测项目集合-通过id查询")
	@ApiOperation(value="检测项目集合-通过id查询", notes="检测项目-通过id查询")
	@GetMapping(value = "/queryRobotTestingTemplateItemByMainId")
	public Result<?> queryRobotTestingTemplateItemListByMainId(@RequestParam(name="id",required=true) String id) {
		List<RobotTestingTemplateItem> robotTestingTemplateItemList = robotTestingTemplateItemService.selectByMainId(id);
		return Result.ok(robotTestingTemplateItemList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param robotTestingTemplate
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, RobotTestingTemplate robotTestingTemplate) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<RobotTestingTemplate> queryWrapper = QueryGenerator.initQueryWrapper(robotTestingTemplate, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<RobotTestingTemplate> queryList = robotTestingTemplateService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<RobotTestingTemplate> robotTestingTemplateList = new ArrayList<RobotTestingTemplate>();
      if(oConvertUtils.isEmpty(selections)) {
          robotTestingTemplateList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          robotTestingTemplateList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<RobotTestingTemplatePage> pageList = new ArrayList<RobotTestingTemplatePage>();
      for (RobotTestingTemplate main : robotTestingTemplateList) {
          RobotTestingTemplatePage vo = new RobotTestingTemplatePage();
          BeanUtils.copyProperties(main, vo);
          List<RobotTestingTemplateItem> robotTestingTemplateItemList = robotTestingTemplateItemService.selectByMainId(main.getId());
          vo.setRobotTestingTemplateItemList(robotTestingTemplateItemList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "检测模板列表");
      mv.addObject(NormalExcelConstants.CLASS, RobotTestingTemplatePage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("检测模板数据", "导出人:"+sysUser.getRealname(), "检测模板"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
    }

    /**
    * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<RobotTestingTemplatePage> list = ExcelImportUtil.importExcel(file.getInputStream(), RobotTestingTemplatePage.class, params);
              for (RobotTestingTemplatePage page : list) {
                  RobotTestingTemplate po = new RobotTestingTemplate();
                  BeanUtils.copyProperties(page, po);
                  robotTestingTemplateService.saveMain(po, page.getRobotTestingTemplateItemList());
              }
              return Result.ok("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.ok("文件导入失败！");
    }

}
