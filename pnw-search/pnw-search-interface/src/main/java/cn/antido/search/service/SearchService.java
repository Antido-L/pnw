package cn.antido.search.service;

import java.util.List;

import cn.antido.admin.pojo.Park;
import cn.antido.admin.pojo.dto.ParkAdminSearchDTO;
import cn.antido.admin.pojo.dto.ParkSearchDTO;
import cn.antido.common.pojo.PageBean;
import cn.antido.common.pojo.search.ParkQueryDTO;
import cn.antido.common.pojo.search.SolrQueryResult;

/**
 * @Description solr搜索服务
 * @author Antido
 * @date:2018年2月2日 下午11:01:22
 */
public interface SearchService {
	
	/**
	 * 把所有停车场数据导入索引库 
	 */
	String importAll();
	
	/**
	 * 根据关键字搜索<br>
	 * 数据来源于pnw-admin-web<br>
	 * 获取所有状态的停车场信息，无排序<br>
	 * ParkAdminSearchDTO
	 */
	List<ParkAdminSearchDTO> keywordQuery(String keyword);
	
	/**
	 * 根据关键字搜索<br>
	 * 数据来源于前台客户的搜索<br>
	 * 获取所有状态的停车场信息，无排序<br>
	 * ParkSearchDTO
	 */
	List<ParkSearchDTO> keywordQuery(String keyword, Integer cityId);
	
	/**
	 * 根据搜索页面传来的条件查询solr索引库,将结果集封装成PageBean返回<br>
	 * 只获取正常使用中的停车场数据
	 * @param parkQuery
	 * @return
	 */
	PageBean<SolrQueryResult> parkQueryByDTO(ParkQueryDTO parkQuery);

}
