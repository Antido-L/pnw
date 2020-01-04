package cn.antido.admin.service;

import java.util.List;

import cn.antido.admin.pojo.BaseDict;

/**
 * @Description 数据字典服务接口
 * @author Antido
 * @date 2018年1月4日 下午3:44:19
 */
public interface BaseDictService {
	
	/**
	 * 根据项目类型获取字典列表<BR>
	 * order:null 不排序<BR>
	 * order:0 正序<BR>
	 * order:1倒叙<BR>
	 */
	List<BaseDict> getBaseDictByTypeCode(Integer typeCode, Integer order);

}
