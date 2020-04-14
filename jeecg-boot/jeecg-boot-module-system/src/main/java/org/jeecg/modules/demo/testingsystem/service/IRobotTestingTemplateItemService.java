package org.jeecg.modules.demo.testingsystem.service;

import org.jeecg.modules.demo.testingsystem.entity.RobotTestingTemplateItem;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 检测项目
 * @Author: jeecg-boot
 * @Date:   2020-04-14
 * @Version: V1.0
 */
public interface IRobotTestingTemplateItemService extends IService<RobotTestingTemplateItem> {

	public List<RobotTestingTemplateItem> selectByMainId(String mainId);
}
