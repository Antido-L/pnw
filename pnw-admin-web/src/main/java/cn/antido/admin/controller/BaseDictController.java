package cn.antido.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.antido.admin.pojo.BaseDict;
import cn.antido.admin.service.BaseDictService;
/**
 * @Description 数据字典相关前端控制器
 * @author Antido
 * @date 2018年1月4日 下午6:41:05
 */
@Controller
public class BaseDictController {
	
	@Autowired
	private BaseDictService baseDictService;
	
	/**
	 * 根据项目类型查找数据字典中该类型下的所有条目,order为排序规则(可以为空)<br>
	 * order:null 不排序<BR>
	 * order:0 正序<BR>
	 * order:1倒叙<BR>
	 * 返回JSON格式对象
	 * 
	 */
	@RequestMapping("/baseDict")
	@ResponseBody
	public List<BaseDict> getBaseDictByTypeCode(Integer typeCode, Integer order) {
		if(typeCode == null) {
			return null;
		}
		List<BaseDict> list = baseDictService.getBaseDictByTypeCode(typeCode,order);
		return list;
	}
	
}
