package cn.antido.common.utils;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.antido.common.pojo.PageBean;

/**
 * @Description pageBean封装工具类
 * @author Antido
 * @date 2017年12月19日 下午5:24:41
 */
public class PageBeanUtils {
	
	/**
	 * 根据PageHelper封装过的list对象自动封装PageBean
	 */
	public static <T> PageBean<T> getPageBean(List<T> list, Integer currentPage) {
		//获取当前list的PageInfo
		PageInfo<T> pageInfo = new PageInfo<>(list);
		
		PageBean<T> pageBean = new PageBean<T>();
		//设置属性
		pageBean.setCurrentPage(currentPage);
		pageBean.setPageSize(pageInfo.getPageSize());
		pageBean.setTotalCount(pageInfo.getTotal());
		pageBean.setTotalPages(pageInfo.getPages());
		
		pageBean.setDataList(list);
		
		return pageBean;
	}
}
