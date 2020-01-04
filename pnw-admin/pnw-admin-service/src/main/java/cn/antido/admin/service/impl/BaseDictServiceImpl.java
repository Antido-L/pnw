package cn.antido.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.antido.admin.mapper.BaseDictMapper;
import cn.antido.admin.pojo.BaseDict;
import cn.antido.admin.service.BaseDictService;

/**
 * @Description 数据字典服务实现类
 * @author Antido
 * @date 2018年1月4日 下午8:51:45
 */
@Service
@Transactional(readOnly=true)
public class BaseDictServiceImpl implements BaseDictService {
	
	@Autowired
	private BaseDictMapper baseDictMapper;
	
	/**
	 * 根据项目类型获取字典列表<BR>
	 * order:null 不排序<BR>
	 * order:0 正序<BR>
	 * order:1倒叙<BR>
	 */
	@Override
	public List<BaseDict> getBaseDictByTypeCode(Integer typeCode, Integer order) {
		List<BaseDict> list = null;
		if(order == null) {
			list = baseDictMapper.selectByTypeCode(typeCode);
		} else {
			if(order == 0) {
				list = baseDictMapper.selectAscByTypeCode(typeCode);
			}
			if(order == 1) {
				list = baseDictMapper.selectDescByTypeCode(typeCode);
			}
		}
		return list;
	}

}
