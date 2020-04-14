package org.jeecg.modules.demo.testingsystem.mapper;

import java.util.List;
import org.jeecg.modules.demo.testingsystem.entity.RobotTestingTemplateItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 检测项目
 * @Author: jeecg-boot
 * @Date:   2020-04-14
 * @Version: V1.0
 */
public interface RobotTestingTemplateItemMapper extends BaseMapper<RobotTestingTemplateItem> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<RobotTestingTemplateItem> selectByMainId(@Param("mainId") String mainId);
}
