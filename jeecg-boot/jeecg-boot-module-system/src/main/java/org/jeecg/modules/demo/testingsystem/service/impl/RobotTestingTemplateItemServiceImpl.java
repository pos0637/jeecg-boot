package org.jeecg.modules.demo.testingsystem.service.impl;

import org.jeecg.modules.demo.testingsystem.entity.RobotTestingTemplateItem;
import org.jeecg.modules.demo.testingsystem.mapper.RobotTestingTemplateItemMapper;
import org.jeecg.modules.demo.testingsystem.service.IRobotTestingTemplateItemService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 检测项目
 * @Author: jeecg-boot
 * @Date:   2020-04-14
 * @Version: V1.0
 */
@Service
public class RobotTestingTemplateItemServiceImpl extends ServiceImpl<RobotTestingTemplateItemMapper, RobotTestingTemplateItem> implements IRobotTestingTemplateItemService {
	
	@Autowired
	private RobotTestingTemplateItemMapper robotTestingTemplateItemMapper;
	
	@Override
	public List<RobotTestingTemplateItem> selectByMainId(String mainId) {
		return robotTestingTemplateItemMapper.selectByMainId(mainId);
	}
}
