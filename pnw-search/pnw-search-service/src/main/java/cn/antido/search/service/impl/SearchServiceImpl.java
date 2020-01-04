package cn.antido.search.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import cn.antido.admin.pojo.dto.ParkAdminSearchDTO;
import cn.antido.admin.pojo.dto.ParkSearchDTO;
import cn.antido.common.pojo.PageBean;
import cn.antido.common.pojo.search.ParkQueryDTO;
import cn.antido.common.pojo.search.SolrQueryResult;
import cn.antido.common.utils.FormatUtils;
import cn.antido.search.service.SearchService;
import cn.antido.search.solr.ParkSolrDao;

/**
 * @Description SearchServiceImpl
 * @author Antido
 * @date:2018年2月2日 下午11:02:56
 */
@Service
public class SearchServiceImpl implements SearchService {
	
	/*@Autowired
	private HttpSolrServer httpSolrServer;
	
	@Autowired
	private ParkMapper parkMapper;
	
	@Autowired
	private DistrictMapper districtMapper;*/
	
	@Value("${solr.position.D}")
	private Integer D;
	
	@Autowired
	private ParkSolrDao parkSolrDao;
	
	/**
	 * 导入所有停车场数据<br>
	 * 
	 */
	@Override
	public String importAll() {
		try {
			parkSolrDao.importAll();
			return "success";
			
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
			return e.toString();
		}
	}

	/**
	 * 根据关键字搜索<br>
	 * 数据来源于pnw-admin-web<br>
	 * 获取所有状态的停车场信息，无排序<br>
	 * ParkAdminSearchDTO
	 */
	@Override
	public List<ParkAdminSearchDTO> keywordQuery(String keyword) {
		SolrQuery query = new SolrQuery();
		
		query.setQuery("park_keywords:"+keyword);
		
		//只查询部分域
		query.addField("id");
		query.addField("park_name");
		query.addField("park_desc");
		query.addField("park_cityName");
		query.addField("park_state");
		
		query.setStart(0);
		query.setRows(8);
		
		query.setHighlight(true);
		//desc和name都高亮显示
		query.addHighlightField("park_name,park_desc");
		query.setHighlightSimplePre("<span style='color:red'>");
		query.setHighlightSimplePost("</span>");
		try {
			List<ParkAdminSearchDTO> list = parkSolrDao.adminKeywordQuery(query);
			return list;
		} catch (SolrServerException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 前台客户的关键字搜索
	 */
	@Override
	public List<ParkSearchDTO> keywordQuery(String keyword, Integer cityId) {
		//创建Solr查询对象
		SolrQuery query = new SolrQuery();
		
		//查询过滤条件
		query.setQuery("park_keywords:"+keyword
				+" AND " + "park_cityId:"+cityId
				+" AND " + "park_state:1");
		
		//query.addFilterQuery("park_cityId:"+cityId);
		//query.addFilterQuery("park_state:1"); //正在使用中
		
		//只查询部分域
		query.addField("id");
		query.addField("park_name");
		query.addField("park_desc");
		query.addField("park_cityName");
		query.addField("park_districtName");
		query.addField("park_remainCount");
		
		query.setStart(0);
		query.setRows(8);
		
		query.setHighlight(true);
		//desc和name都高亮显示
		query.addHighlightField("park_name,park_desc");
		query.setHighlightSimplePre("<span style='color:red'>");
		query.setHighlightSimplePost("</span>");
		try {
			List<ParkSearchDTO> list = parkSolrDao.keywordQuery(query);
			return list;
		} catch (SolrServerException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据搜索页面传来的条件查询solr索引库,将结果集封装成PageBean返回<br>
	 * 只获取正常使用中的停车场数据
	 */
	@Override
	public PageBean<SolrQueryResult> parkQueryByDTO(ParkQueryDTO parkQuery) {
		SolrQuery query = new SolrQuery();
		StringBuilder sb = new StringBuilder();
		
		//只获取正常使用中的停车场数据
		sb.append("park_state:1 AND ");
		
		//遍历所有限制条件,将有效的限制条件拼接为查询语句
		if(parkQuery.getParkType() != null) {
			if(parkQuery.getParkType() == 1)
				sb.append("park_type:0 AND ");
			if(parkQuery.getParkType() == 2)
				sb.append("park_type:1 AND ");
			if(parkQuery.getParkType() == 3)
				sb.append("park_type:2 AND ");
		}
		
		//城市ID
		if(parkQuery.getCityCode() != null) {
			sb.append("park_cityId:" + parkQuery.getCityCode() + " AND "); 
		}
		
		/*
		//TODO: 车位类型功能还未添加
		if(parkQuery.getSpaceType() != null) {
			sb.append("park_type:"+parkQuery.getParkType()+" AND ");
		}
		*/
		
		/*
		//TODO: 服务类型功能还未添加
		if(parkQuery.getServiceType() != null) {
			sb.append("park_type:"+parkQuery.getParkType()+" AND ");
		}
		*/
		
		//是否收费
		if(parkQuery.getChargeType() != null) {
			if(parkQuery.getChargeType() == 1){ //免费
				sb.append("park_price:0 AND ");
			} else if(parkQuery.getChargeType() == 2) { //收费
				sb.append("park_price:[1 TO *] AND ");
			}
		}
		
		//TODO: 离我最近
		if(parkQuery.getDistanceOrder() != null)
		
		//价格升序
		if(parkQuery.getPriceOrder() != null) {
			query.setSort("park_price", ORDER.asc);
		} 
		
		//剩余车位倒序
		if(parkQuery.getRemainOrder() != null) {
			query.setSort("park_remainCount", ORDER.desc);
		}
		
		//价格区间
		if(parkQuery.getPriceLimit() != null) {
			try {
				String v[] = parkQuery.getPriceLimit().split("-");
				Integer loPrice = null;
				Integer hiPrice = null;
				
				//当价格区间出现非数字时 会发生类型转换异常,出现异常后捕捉打印,而不会在query中新增价格区间搜索的限制条件
				//不影响其他查询条件的添加
				if(v.length == 1) {
					loPrice = FormatUtils.priceFormat(v[0]);
				} else if(v.length == 2) {
					hiPrice = FormatUtils.priceFormat(v[0]);
				} 
				
				if(v.length == 1) {
					sb.append("park_price:["+loPrice+" TO *] AND ");
				} else if(v.length == 2) {
					sb.append("park_price:["+ loPrice + " TO "+ hiPrice +"] AND ");
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		//去除sb中最后的" AND ";
		if(sb.length() > 5) {
			sb.delete(sb.length() - 5, sb.length());
			query.setQuery(sb.toString());
		} else {
			query.setQuery("*:*");
		}
		
		//设置距离排序
		if(parkQuery.getX() != null && parkQuery.getY() != null) {
			query.set("fq", "{!geofilt}"); //距离过滤函数
			query.set("pt", parkQuery.getX()+" "+parkQuery.getY()); //当前经纬度
			query.set("sfield", "park_position"); //经纬度的字段
			query.set("d", D); //获取多少千米范围内的数据
			query.set("sort", "geodist() asc");  //根据距离排序：由近到远
		}
		
		//设置分页
		Integer currentPage = parkQuery.getCurrentPage();
		Integer pageSize = parkQuery.getPageSize();
		query.setStart((currentPage - 1) * pageSize);
		query.setRows(pageSize);
		
		//获取的字段
		query.addField("id");
		query.addField("park_name");
		query.addField("park_type");
		query.addField("park_desc");
		query.addField("park_cityName");
		query.addField("park_districtName");
		query.addField("park_price");
		query.addField("park_workingCount");
		query.addField("park_remainCount");
		query.addField("park_position");
		
		PageBean<SolrQueryResult> pageBean = new PageBean<SolrQueryResult>();
		pageBean.setCurrentPage(currentPage);
		pageBean.setPageSize(pageSize);
		//通过DAO封pageBean中的数据 (我也不知道为啥就是想在DAO层封装)
		try {
			parkSolrDao.queryByDTO(query,pageBean);
			int totalPages;
			
			if(pageBean.getTotalCount() % pageBean.getPageSize() == 0)
				totalPages = pageBean.getTotalCount().intValue() / pageBean.getPageSize();
			else 
				totalPages = pageBean.getTotalCount().intValue() / pageBean.getPageSize() + 1;
			
			pageBean.setTotalPages(totalPages);
			
		} catch (SolrServerException e) {
			//如果出现异常返回没有数据的PageBean
			pageBean.setTotalCount(0L);
			pageBean.setTotalPages(0);
			//e.printStackTrace();
			System.err.println(e);
		}
		
		return pageBean;
	}

}
