package org.jeecg.modules.demo.testingsystem.service.impl;

import org.jeecg.modules.demo.testingsystem.entity.RobotTestingTemplate;
import org.jeecg.modules.demo.testingsystem.entity.RobotTestingTemplateItem;
import org.jeecg.modules.demo.testingsystem.mapper.RobotTestingTemplateItemMapper;
import org.jeecg.modules.demo.testingsystem.mapper.RobotTestingTemplateMapper;
import org.jeecg.modules.demo.testingsystem.service.IRobotTestingTemplateService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 检测模板
 * @Author: jeecg-boot
 * @Date:   2020-04-14
 * @Version: V1.0
 */
@Service
public class RobotTestingTemplateServiceImpl extends ServiceImpl<RobotTestingTemplateMapper, RobotTestingTemplate> implements IRobotTestingTemplateService {

	@Autowired
	private RobotTestingTemplateMapper robotTestingTemplateMapper;
	@Autowired
	private RobotTestingTemplateItemMapper robotTestingTemplateItemMapper;
	
	@Override
	@Transactional
	public void saveMain(RobotTestingTemplate robotTestingTemplate, List<RobotTestingTemplateItem> robotTestingTemplateItemList) {
		robotTestingTemplateMapper.insert(robotTestingTemplate);
		if(robotTestingTemplateItemList!=null && robotTestingTemplateItemList.size()>0) {
			for(RobotTestingTemplateItem entity:robotTestingTemplateItemList) {
				//外键设置
				entity.setRobotTestingTemplateId(robotTestingTemplate.getId());
				robotTestingTemplateItemMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(RobotTestingTemplate robotTestingTemplate,List<RobotTestingTemplateItem> robotTestingTemplateItemList) {
		robotTestingTemplateMapper.updateById(robotTestingTemplate);
		
		//1.先删除子表数据
		robotTestingTemplateItemMapper.deleteByMainId(robotTestingTemplate.getId());
		
		//2.子表数据重新插入
		if(robotTestingTemplateItemList!=null && robotTestingTemplateItemList.size()>0) {
			for(RobotTestingTemplateItem entity:robotTestingTemplateItemList) {
				//外键设置
				entity.setRobotTestingTemplateId(robotTestingTemplate.getId());
				robotTestingTemplateItemMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		robotTestingTemplateItemMapper.deleteByMainId(id);
		robotTestingTemplateMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			robotTestingTemplateItemMapper.deleteByMainId(id.toString());
			robotTestingTemplateMapper.deleteById(id);
		}
	}
	
}
