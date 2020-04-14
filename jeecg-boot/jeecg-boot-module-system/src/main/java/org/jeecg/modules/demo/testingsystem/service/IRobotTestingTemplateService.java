package org.jeecg.modules.demo.testingsystem.service;

import org.jeecg.modules.demo.testingsystem.entity.RobotTestingTemplateItem;
import org.jeecg.modules.demo.testingsystem.entity.RobotTestingTemplate;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 检测模板
 * @Author: jeecg-boot
 * @Date:   2020-04-14
 * @Version: V1.0
 */
public interface IRobotTestingTemplateService extends IService<RobotTestingTemplate> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(RobotTestingTemplate robotTestingTemplate,List<RobotTestingTemplateItem> robotTestingTemplateItemList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(RobotTestingTemplate robotTestingTemplate,List<RobotTestingTemplateItem> robotTestingTemplateItemList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
