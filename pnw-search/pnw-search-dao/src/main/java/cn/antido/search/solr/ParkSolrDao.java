package cn.antido.search.solr;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;

import cn.antido.admin.pojo.Park;
import cn.antido.admin.pojo.dto.ParkAdminSearchDTO;
import cn.antido.admin.pojo.dto.ParkSearchDTO;
import cn.antido.common.pojo.PageBean;
import cn.antido.common.pojo.search.SolrQueryResult;

/**
 * @Description ParkSolrDao接口
 * @author Antido
 * @date:2018年2月7日 下午4:44:53
 */
public interface ParkSolrDao {
	
	/**
	 * 导入所有park数据到索引库中
	 */
	void importAll() throws SolrServerException, IOException;
	
	/**
	 * 根据admin传来的关键字封装索引库中匹配的对象
	 */
	List<ParkAdminSearchDTO> adminKeywordQuery(SolrQuery query) throws SolrServerException;

	/**
	 * 新增park
	 */
	void addPark(Park park) throws SolrServerException, IOException;
	
	/**
	 * 根据id删除索引库中的数据
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	void delPark(Long parkId) throws SolrServerException, IOException;


	/**
	 * 根据id将目标park_type 设置为1
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	@Deprecated
	void publishPark(Long parkId) throws SolrServerException, IOException;
	
	/**
	 * 根据park更新索引库中的信息,实现类似addPark
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	void updatePark(Park park) throws SolrServerException, IOException;
	
	/**
	 * 根据客户传来的关键字封装索引库中匹配的对象
	 * @param query
	 * @return
	 * @throws SolrServerException 
	 */
	List<ParkSearchDTO> keywordQuery(SolrQuery query) throws SolrServerException;
	
	/**
	 * 将指定ID的停车场剩余车位数加1
	 * @param parkId
	 * @throws SolrServerException 
	 * @throws IOException 
	 */
	void addRemain(Long parkId) throws SolrServerException, IOException;
	
	/**
	 * 将指定ID的停车场剩余车位数加1
	 * @param parkId
	 */
	void subRemain(Long parkId) throws SolrServerException, IOException;
	
	/**
	 * 通过查询条件查询索引库,将数据按照正确格式放入PageBean中
	 * @param query
	 * @throws SolrServerException
	 */
	void queryByDTO(SolrQuery query, PageBean<SolrQueryResult> pageBean) throws SolrServerException;
	
	/**
	 * 将指定ID的停车场正常工作车位数加1
	 * @param parkId
	 * @throws SolrServerException 
	 * @throws IOException 
	 */
	void addWorkingCount(Long parkId) throws SolrServerException, IOException;

	/**
	 * 将指定ID的停车场正常工作车位数减1
	 * @param parkId
	 * @throws SolrServerException 
	 * @throws IOException 
	 */
	void subWorkingCount(Long parkId) throws SolrServerException, IOException;
	
}

