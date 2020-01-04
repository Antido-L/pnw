package cn.antido.search.solr.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.antido.admin.pojo.District;
import cn.antido.admin.pojo.Park;
import cn.antido.admin.pojo.dto.ParkAdminSearchDTO;
import cn.antido.admin.pojo.dto.ParkSearchDTO;
import cn.antido.common.pojo.PageBean;
import cn.antido.common.pojo.search.SolrQueryResult;
import cn.antido.common.utils.FormatUtils;
import cn.antido.search.mapper.DistrictMapper;
import cn.antido.search.mapper.ParkMapper;
import cn.antido.search.solr.ParkSolrDao;

/**
 * @Description 有关park的索引库操作<br>
 * 只有查询操作，无事物
 * @author Antido
 * @date:2018年2月7日 下午3:51:53
 */
@Repository
public class ParkSolrDaoImpl implements ParkSolrDao {
	
	@Autowired
	private HttpSolrServer httpSolrServer;
	
	@Autowired
	private ParkMapper parkMapper;
	
	@Autowired
	private DistrictMapper districtMapper;
	
	/**
	 * 导入所有停车场数据<br>
	 * 利用pageHelper 分次导入数据<br>
	 * 每次导入的数据条数:pageSize(10000)
	 */
	public void importAll() throws SolrServerException, IOException {
		int count = 0; //导入次数 计数
		int pageSize = 10000; //每次导入的数据条数
		int lastPage = count + 1; //数据总页数
		
		List<Park> list = null;
		PageInfo<Park> pageInfo = null;
	
		
		while(true) {
			if(lastPage == count) { //当最后一页被查询完毕后跳出
				break;
			} else {
				count++;
			}
			// 设置每次获取的数据数
			PageHelper.startPage(count, pageSize); 
			
			list = parkMapper.selectParkInfo();
			pageInfo = new PageInfo<>(list);
			lastPage = pageInfo.getLastPage();
			
			for (Park park : list) {
				//创建一个文档操作对象SolrInputDocument
				SolrInputDocument document = new SolrInputDocument();
				/*
				<field name="park_name" type="text_ik" indexed="true" stored="true"/>
				<field name="park_desc" type="text_ik" indexed="true" stored="true"/>
				<field name="park_type" type="long" indexed="true" stored="true"/>
				<field name="park_state" type="long" indexed="true" stored="true"/>
				<field name="park_price"  type="long" indexed="true" stored="true"/>
				<field name="park_workingCount"  type="long" indexed="true" stored="true"/>
				<field name="park_remainCount"  type="long" indexed="true" stored="true"/>
				<field name="park_east"  type="long" indexed="true" stored="true"/>
				<field name="park_north"  type="long" indexed="true" stored="true"/>
				<field name="park_cityId" type="string" indexed="true" stored="true" />
				<field name="park_cityName" type="string" indexed="true" stored="true" />
				<field name="park_districtId" type="string" indexed="true" stored="true" />
				<field name="park_districtName" type="string" indexed="true" stored="true" />
				*/
				
				//导入时处理NPE问题 尽可能的保证数据导入不出现问题
				document.addField("id", park.getId());
				
				//停车场名
				if(park.getName() == null) {
					document.addField("park_name", "NULL");
				} else {
					document.addField("park_name", park.getName());
				}
				
				//描述
				if(park.getPosition_desc() == null) {
					document.addField("park_desc", "");
				} else{
					document.addField("park_desc", park.getPosition_desc());
				}
				
				//类型 默认1--室外
				if(park.getPark_type() == null) {
					document.addField("park_type", 1);
				} else {
					document.addField("park_type", park.getPark_type());
				}
				
				//状态 默认3--开发中
				if(park.getState() == null) {
					document.addField("park_state", 1);
				} else {
					document.addField("park_state", park.getState());
				}
				
				//价格 默认 0--免费
				if(park.getPrice() == null) {
					document.addField("park_price",0);
				} else {
					document.addField("park_price",park.getPrice());
				}
				
				//运行车位总数 
				if(park.getWorking_count() == null) {
					document.addField("park_workingCount",0);
				} else {
					document.addField("park_workingCount",park.getWorking_count());
				}
				
				//剩余数量
				if(park.getWorking_count() == null) {
					document.addField("park_remainCount",0);
				} else {
					if(park.getUsing_count() == null) {
						document.addField("park_remainCount",park.getWorking_count());
					} else {
						document.addField("park_remainCount",park.getWorking_count() - park.getUsing_count());
					}
				}
				
				//经纬度
				/*if(park.getEast_longitude() == null || park.getNorth_latitude() == null) {
					document.addField("park_east",0);
					document.addField("park_north",0);
				} else {
					//转换为正确格式的经纬度
					Double east = FormatUtils.eastAndNorth2Double(park.getEast_longitude());
					Double north = FormatUtils.eastAndNorth2Double(park.getNorth_latitude());
					
					document.addField("park_east", east);
					document.addField("park_north", north);
				}*/
				if(park.getEast_longitude() == null || park.getNorth_latitude() == null) {
					document.addField("park_position",null);
				} else {
					//转换为正确格式的经纬度
					Double east = FormatUtils.eastAndNorth2Double(park.getEast_longitude());
					Double north = FormatUtils.eastAndNorth2Double(park.getNorth_latitude());
					document.addField("park_position", east + " " + north);
				}
				
				//所属城市
				if(park.getCity_id() == null) {
					document.addField("park_cityId",0);
					document.addField("park_cityName","");
				} else {
					//获取city信息
					District city = districtMapper.selectByPrimaryKey(Integer.parseInt(park.getCity_id()));
					if(city == null) {
						document.addField("park_cityId",0);
						document.addField("park_cityName","");	
					} else {
						document.addField("park_cityId",city.getId());
						//城市名
						document.addField("park_cityName",city.getName());
					}
				}
				
				//所属区县
				if(park.getDistrict_id() == null) {
					document.addField("park_districtId",0);
					document.addField("park_districtName","");
				} else {
					//获取district信息
					District district = districtMapper.selectByPrimaryKey(Integer.parseInt(park.getDistrict_id()));
					if(district == null) {
						document.addField("park_districtId",0);
						document.addField("park_districtName","");	
					} else {
						document.addField("park_districtId",district.getId());
						//城市名
						document.addField("park_districtName",district.getName());
					}
				}
				
				//把文档写入索引库
				httpSolrServer.add(document);
			}
			//提交
			httpSolrServer.commit();
			//System.out.println("commit");
		}
		
	}
	
	/**
	 * 根据关键字搜索<br>
	 * 数据来源于pnw-admin-web<br>
	 * 获取所有状态的停车场信息，无排序<br>
	 * ParkAdminSearchDTO
	 */
	public List<ParkAdminSearchDTO> adminKeywordQuery(SolrQuery query) throws SolrServerException {
		//获取搜索结果
		QueryResponse response = httpSolrServer.query(query);
		
		SolrDocumentList docs = response.getResults();
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		
		ArrayList<ParkAdminSearchDTO> list = new ArrayList<ParkAdminSearchDTO>();
		for (SolrDocument doc : docs) {
			ParkAdminSearchDTO dto = new ParkAdminSearchDTO();
			
			//当有高亮显示时注入高亮项
			List<String> nameList = highlighting.get(doc.get("id")).get("park_name");
			if(nameList != null && nameList.size() > 0) {
				dto.setName(nameList.get(0));
			} else {
				dto.setName(doc.get("park_name"));
			}
			
			List<String> descList = highlighting.get(doc.get("id")).get("park_desc");
			if(descList != null && descList.size() > 0) {
				dto.setDesc(descList.get(0));
			} else {
				dto.setDesc(doc.get("park_desc"));
			}
			
			dto.setId(doc.get("id"));
			dto.setCityName(doc.get("park_cityName"));
			dto.setState(doc.get("park_state"));
			list.add(dto);
		}
		return list;
	}
	
	/**
	 * 新增一个停车场数据到索引库
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	@Override
	public void addPark(Park park) throws SolrServerException, IOException {
		SolrInputDocument document = new SolrInputDocument();
		/*"id": "2101060150001",
        "park_name": "翟家街道停车场1",
        "park_desc": "这是一段停车场的描述",
        "park_type": 1,
        "park_state": 1,
        "park_price": 600,
        "park_workingCount": 30,
        "park_remainCount": 12,
        "park_east": 0,
        "park_north": 0,
        "park_cityId": "210100",
        "park_cityName": "沈阳市",
        "park_districtId": "210106",
        "park_districtName": "铁西区",*/
	
		document.addField("id", park.getId());
		
		//停车场名
		if(park.getName() == null) {
			document.addField("park_name", "NULL");
		} else {
			document.addField("park_name", park.getName());
		}
		
		//描述
		if(park.getPosition_desc() == null) {
			document.addField("park_desc", "");
		} else{
			document.addField("park_desc", park.getPosition_desc());
		}
		
		//类型 默认1--室外
		if(park.getPark_type() == null) {
			document.addField("park_type", 1);
		} else {
			document.addField("park_type", park.getPark_type());
		}
		
		//状态 默认3--开发中
		if(park.getState() == null) {
			document.addField("park_state", 1);
		} else {
			document.addField("park_state", park.getState());
		}
		
		//价格 默认 0--免费
		if(park.getPrice() == null) {
			document.addField("park_price",0);
		} else {
			document.addField("park_price",park.getPrice());
		}
		
		//运行车位总数 
		if(park.getWorking_count() == null) {
			document.addField("park_workingCount",0);
		} else {
			document.addField("park_workingCount",park.getWorking_count());
		}
		
		//剩余数量
		if(park.getWorking_count() == null) {
			document.addField("park_remainCount",0);
		} else {
			if(park.getUsing_count() == null) {
				document.addField("park_remainCount",park.getWorking_count());
			} else {
				document.addField("park_remainCount",park.getWorking_count() - park.getUsing_count());
			}
		}
		
		
		//经纬度
		if(park.getEast_longitude() == null || park.getNorth_latitude() == null) {
			document.addField("park_position",null);
		} else {
			//转换为正确格式的经纬度
			Double east = FormatUtils.eastAndNorth2Double(park.getEast_longitude());
			Double north = FormatUtils.eastAndNorth2Double(park.getNorth_latitude());
			document.addField("park_position", east + " " + north);
		}
		
		//所属城市
		if(park.getCity_id() == null) {
			document.addField("park_cityId",0);
			document.addField("park_cityName","");
		} else {
			//获取city信息
			District city = districtMapper.selectByPrimaryKey(Integer.parseInt(park.getCity_id()));
			if(city == null) {
				document.addField("park_cityId",0);
				document.addField("park_cityName","");	
			} else {
				document.addField("park_cityId",city.getId());
				//城市名
				document.addField("park_cityName",city.getName());
			}
		}
		
		//所属区县
		if(park.getDistrict_id() == null) {
			document.addField("park_districtId",0);
			document.addField("park_districtName","");
		} else {
			//获取district信息
			District district = districtMapper.selectByPrimaryKey(Integer.parseInt(park.getDistrict_id()));
			if(district == null) {
				document.addField("park_districtId",0);
				document.addField("park_districtName","");	
			} else {
				document.addField("park_districtId",district.getId());
				//城市名
				document.addField("park_districtName",district.getName());
			}
		}

		
		httpSolrServer.add(document);
		
		httpSolrServer.commit();
		
	}
	
	/**
	 * 根据Id删除索引库中的数据
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	@Override
	public void delPark(Long parkId) throws SolrServerException, IOException {
		httpSolrServer.deleteById(parkId+"");
		//httpSolrServer.deleteByQuery("id:parkId");
		//提交
		httpSolrServer.commit();
	}
	
	/**
	 * 根据id将目标park_type 设置为1
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	@Override
	public void publishPark(Long parkId) throws SolrServerException, IOException {
		
		QueryResponse query = httpSolrServer.query(new SolrQuery("id:"+parkId));
		SolrDocumentList docList = query.getResults();
		
		//索引库中有该item
		if(docList != null && docList.size() != 0) {
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", parkId);
			
			//只更新park_type字段
			Map<String, String> operation = new HashMap<>();
			operation.put("set", "1");
			doc.addField("park_state", operation);
			
			httpSolrServer.add(doc);
			httpSolrServer.commit();
		}
		
	}

	/**
	 * 更新索引库信息
	 * @throws IOException 
	 * @throws SolrServerException 
	 */
	@Override
	public void updatePark(Park park) throws SolrServerException, IOException {
		SolrInputDocument document = new SolrInputDocument();
	
		document.addField("id", park.getId());
		
		//停车场名
		if(park.getName() == null) {
			document.addField("park_name", "NULL");
		} else {
			document.addField("park_name", park.getName());
		}
		
		//描述
		if(park.getPosition_desc() == null) {
			document.addField("park_desc", "");
		} else{
			document.addField("park_desc", park.getPosition_desc());
		}
		
		//类型 默认1--室外
		if(park.getPark_type() == null) {
			document.addField("park_type", 1);
		} else {
			document.addField("park_type", park.getPark_type());
		}
		
		//状态 默认3--开发中
		if(park.getState() == null) {
			document.addField("park_state", 3);
		} else {
			document.addField("park_state", park.getState());
		}
		
		//价格 默认 0--免费
		if(park.getPrice() == null) {
			document.addField("park_price",0);
		} else {
			document.addField("park_price",park.getPrice());
		}
		
		//运行车位总数 
		if(park.getWorking_count() == null) {
			document.addField("park_workingCount",0);
		} else {
			document.addField("park_workingCount",park.getWorking_count());
		}
		
		//剩余数量
		if(park.getWorking_count() == null) {
			document.addField("park_remainCount",0);
		} else {
			if(park.getUsing_count() == null) {
				document.addField("park_remainCount",park.getWorking_count());
			} else {
				document.addField("park_remainCount",park.getWorking_count() - park.getUsing_count());
			}
		}
		
		//经纬度
		if(park.getEast_longitude() == null || park.getNorth_latitude() == null) {
			document.addField("park_position",null);
		} else {
			//转换为正确格式的经纬度
			Double east = FormatUtils.eastAndNorth2Double(park.getEast_longitude());
			Double north = FormatUtils.eastAndNorth2Double(park.getNorth_latitude());
			document.addField("park_position", east + " " + north);
		}
		
		//所属城市
		if(park.getCity_id() == null) {
			document.addField("park_cityId",0);
			document.addField("park_cityName","");
		} else {
			//获取city信息
			District city = districtMapper.selectByPrimaryKey(Integer.parseInt(park.getCity_id()));
			if(city == null) {
				document.addField("park_cityId",0);
				document.addField("park_cityName","");	
			} else {
				document.addField("park_cityId",city.getId());
				//城市名
				document.addField("park_cityName",city.getName());
			}
		}
		
		//所属区县
		if(park.getDistrict_id() == null) {
			document.addField("park_districtId",0);
			document.addField("park_districtName","");
		} else {
			//获取district信息
			District district = districtMapper.selectByPrimaryKey(Integer.parseInt(park.getDistrict_id()));
			if(district == null) {
				document.addField("park_districtId",0);
				document.addField("park_districtName","");	
			} else {
				document.addField("park_districtId",district.getId());
				//城市名
				document.addField("park_districtName",district.getName());
			}
		}
		
		httpSolrServer.add(document);
		httpSolrServer.commit();
	}
	
	/**
	 * 关键字搜索
	 */
	@Override
	public List<ParkSearchDTO> keywordQuery(SolrQuery query) throws SolrServerException {
		//获取搜索结果
		QueryResponse response = httpSolrServer.query(query);
		
		SolrDocumentList docs = response.getResults();
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		
		ArrayList<ParkSearchDTO> list = new ArrayList<ParkSearchDTO>();
		for (SolrDocument doc : docs) {
			ParkSearchDTO dto = new ParkSearchDTO();
			
			//当有高亮显示时注入高亮项
			List<String> nameList = highlighting.get(doc.get("id")).get("park_name");
			if(nameList != null && nameList.size() > 0) {
				dto.setName(nameList.get(0));
			} else {
				dto.setName(doc.get("park_name"));
			}
			//当有高亮显示时注入高亮项
			List<String> descList = highlighting.get(doc.get("id")).get("park_desc");
			if(descList != null && descList.size() > 0) {
				dto.setDesc(descList.get(0));
			} else {
				dto.setDesc(doc.get("park_desc"));
			}
			
			dto.setId(doc.get("id"));
			dto.setCityName(doc.get("park_cityName"));
			dto.setDistrictName(doc.get("park_districtName"));
			dto.setRemainCount((Long)doc.get("park_remainCount"));
			list.add(dto);
		}
		return list;
	}
	
	/**
	 * 根据ID将剩余车位数+1<br>
	 * 先获取索引库中的count数据,加一后提交更新
	 * @throws SolrServerException 
	 * @throws IOException 
	 */
	@Override
	public void addRemain(Long parkId) throws SolrServerException, IOException {
		//创建Solr查询对象
		SolrQuery query = new SolrQuery();
		//查询过滤条件
		query.setQuery("id:"+parkId);
		
		query.addField("id");
		query.addField("park_remainCount");
		query.addField("park_workingCount");
		
		QueryResponse response = httpSolrServer.query(query);
		
		SolrDocumentList list = response.getResults();
		
		//没找到结果
		if(list.size() == 0) {
			throw new RuntimeException("未找到ID:"+parkId+"的记录");
		}
		
		Long remainCount = (Long) list.get(0).get("park_remainCount");
		Long workingCount = (Long) list.get(0).get("park_workingCount");
		//剩余车位数不应该大于总车位数的
		if(remainCount >= workingCount) {
			throw new RuntimeException("remainCount >= workingCount!");
		}
		
		//新建一个doc,加1后执行更新
		SolrInputDocument document = new SolrInputDocument();
		
		document.addField("id", parkId);
		
		//局部更新park_remainCount字段
		Map<String, String> operation = new HashMap<>();
		operation.put("set", remainCount+1+"");
		document.addField("park_remainCount", operation);
		
		httpSolrServer.add(document);
		httpSolrServer.commit();
		
	}

	/**
	 * 根据ID将剩余车位数-1<br>
	 * 先获取索引库中的count数据,减一后提交更新
	 * @throws SolrServerException 
	 * @throws IOException 
	 */
	@Override
	public void subRemain(Long parkId) throws SolrServerException, IOException {
		//创建Solr查询对象
		SolrQuery query = new SolrQuery();
		//查询过滤条件
		query.setQuery("id:"+parkId);
		
		query.addField("id");
		query.addField("park_remainCount");
		//query.addField("park_workingCount");
		
		QueryResponse response = httpSolrServer.query(query);
		
		SolrDocumentList list = response.getResults();
		
		//没找到结果
		if(list.size() == 0) {
			throw new RuntimeException("未找到ID:"+parkId+"的记录");
		}
		
		Long remainCount = (Long) list.get(0).get("park_remainCount");
		//Integer workingCount = (Integer) list.get(0).get("park_workingCount");
		//剩余车位数不应该等于0
		if(remainCount == 0) {
			throw new RuntimeException("remainCount == 0!");
		}
		
		//新建一个doc,减1后执行更新
		SolrInputDocument document = new SolrInputDocument();
		
		document.addField("id", parkId);
		
		//局部更新park_remainCount字段
		Map<String, String> operation = new HashMap<>();
		operation.put("set", remainCount-1+"");
		document.addField("park_remainCount", operation);
		
		httpSolrServer.add(document);
		httpSolrServer.commit();
		
	}

	/**
	 * 查询索引库,封装PageBean
	 * @throws SolrServerException 
	 */
	@Override
	public void queryByDTO(SolrQuery query, PageBean<SolrQueryResult> pageBean) throws SolrServerException {
		//查询
		QueryResponse response = httpSolrServer.query(query);
		SolrDocumentList results = response.getResults();
		
		long numFound = results.getNumFound();
		
		pageBean.setTotalCount(numFound);
		
		//创建数据列表,使用与pageSize相同的长度
		ArrayList<SolrQueryResult> dataList = new ArrayList<SolrQueryResult>(pageBean.getPageSize());
		
		//遍历结果集
		for (SolrDocument doc : results) { 
			SolrQueryResult solrQueryResult = new SolrQueryResult();
			
			solrQueryResult.setId((String) doc.get("id"));
			solrQueryResult.setDesc((String) doc.get("park_desc"));
			solrQueryResult.setDistrictName((String) doc.get("park_districtName"));
			solrQueryResult.setName((String) doc.get("park_name"));
			solrQueryResult.setPrice((Long) doc.get("park_price"));
			solrQueryResult.setRemainCount((Long) doc.get("park_remainCount"));
			solrQueryResult.setWorkingCount((Long) doc.get("park_workingCount"));
			solrQueryResult.setType((Long) doc.get("park_type"));
			
			//解析地址
			String position = (String) doc.get("park_position");
			if(position != null) {
				String[] positionArr = position.split(" ");
				if(positionArr.length == 2) {
					solrQueryResult.setEast(Double.parseDouble(positionArr[0]));
					solrQueryResult.setNorth(Double.parseDouble(positionArr[1]));
				} 
			}
			dataList.add(solrQueryResult);
		}
		
		pageBean.setDataList(dataList);
		
	}

	/**
	 * 将指定ID的停车场正常工作车位数加1
	 * @param parkId
	 * @throws SolrServerException 
	 * @throws IOException 
	 */
	@Override
	public void addWorkingCount(Long parkId) throws SolrServerException, IOException {
		//创建Solr查询对象
		SolrQuery query = new SolrQuery();
		//查询过滤条件
		query.setQuery("id:"+parkId);
		
		query.addField("id");
		query.addField("park_remainCount");
		query.addField("park_workingCount");
		
		QueryResponse response = httpSolrServer.query(query);
		
		SolrDocumentList list = response.getResults();
		
		//没找到结果
		if(list.size() == 0) {
			throw new RuntimeException("未找到ID:"+parkId+"的记录");
		}
		
		Long remainCount = (Long) list.get(0).get("park_remainCount");
		Long workingCount = (Long) list.get(0).get("park_workingCount");
		//剩余车位数不应该大于总车位数的
		if(remainCount >= workingCount) {
			throw new RuntimeException("remainCount >= workingCount!");
		}
		
		//新建一个doc,加1后执行更新
		SolrInputDocument document = new SolrInputDocument();
		
		document.addField("id", parkId);
		
		//局部更新park_remainCount字段
		Map<String, String> operation = new HashMap<>();
		operation.put("set", workingCount+1+"");
		document.addField("park_workingCount", operation);
		
		httpSolrServer.add(document);
		httpSolrServer.commit();
		
	}

	/**
	 * 将指定ID的停车场正常工作车位数加1
	 * @param parkId
	 * @throws SolrServerException 
	 * @throws IOException 
	 */
	@Override
	public void subWorkingCount(Long parkId) throws SolrServerException, IOException {
		//创建Solr查询对象
		SolrQuery query = new SolrQuery();
		//查询过滤条件
		query.setQuery("id:"+parkId);
		
		query.addField("id");
		query.addField("park_remainCount");
		query.addField("park_workingCount");
		
		QueryResponse response = httpSolrServer.query(query);
		
		SolrDocumentList list = response.getResults();
		
		//没找到结果
		if(list.size() == 0) {
			throw new RuntimeException("未找到ID:"+parkId+"的记录");
		}
		
		Long remainCount = (Long) list.get(0).get("park_remainCount");
		Long workingCount = (Long) list.get(0).get("park_workingCount");
		//剩余车位数不应该大于总车位数的
		if(remainCount > workingCount) {
			throw new RuntimeException("remainCount > workingCount!");
		}
		
		//新建一个doc,加1后执行更新
		SolrInputDocument document = new SolrInputDocument();
		
		document.addField("id", parkId);
		
		//局部更新park_remainCount字段
		Map<String, String> operation = new HashMap<>();
		operation.put("set", workingCount-1+"");
		document.addField("park_workingCount", operation);
		
		httpSolrServer.add(document);
		httpSolrServer.commit();
		
	}
}
