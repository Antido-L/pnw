package cn.antido.search.service.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.antido.admin.pojo.District;
import cn.antido.admin.pojo.Park;
import cn.antido.search.mapper.DistrictMapper;
import cn.antido.search.mapper.ParkMapper;


/*
<field name="park_name" type="text_ik" indexed="true" stored="true"/>
<field name="park_desc" type="text_ik" indexed="true" stored="true"/>
<field name="park_type" type="text_ik" indexed="true" stored="true"/>
<field name="park_price"  type="long" indexed="true" stored="true"/>
<field name="park_workingCount"  type="long" indexed="true" stored="true"/>
<field name="park_remainCount"  type="long" indexed="true" stored="true"/>
<field name="park_east"  type="long" indexed="true" stored="true"/>
<field name="park_north"  type="long" indexed="true" stored="true"/>
<field name="park_cityId" type="string" indexed="true" stored="true" />

<field name="park_keywords" type="text_ik" indexed="true" stored="false" multiValued="true"/>
<copyField source="park_name" dest="park_keywords"/>
<copyField source="park_desc" dest="park_keywords"/>
*/
public class TestSolr {
	
	public static void main(String[] args) throws Exception {
		//创建一个SolrServer对象，创建一个连接。参数solr服务的url
		SolrServer solrServer = new HttpSolrServer("http://192.168.161.128:9080/solr/collection1");
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("park_cityId:210100");
		solrQuery.set("fq", "{!geofilt}");           //距离过滤函数
		solrQuery.set("pt", "123.284153 41.772247"); //当前经纬度
		solrQuery.set("sfield", "park_position"); //经纬度的字段
		solrQuery.set("d", 10); //经纬度的字段
		//params.set("score", "kilometers"); 
		solrQuery.set("sort", "geodist() asc");  //根据距离排序：由近到远
		
		//solrQuery.set("fq", "park_state:3");
		solrQuery.setStart(0);
		solrQuery.setRows(5);
		//solrQuery.addField("park_name"); //设置要取得域
		solrQuery.addFilterQuery("park_cityId:210100");
		
		QueryResponse response = solrServer.query(solrQuery);
		
		SolrDocumentList documentList = response.getResults();
		System.out.println("numFound:"+documentList.getNumFound());
		
		for (SolrDocument doc : documentList) {
			System.out.println((String) doc.get("park_position"));
			System.out.println(doc.get("id"));
			System.out.println(doc.get("park_name"));
			System.out.println(doc.get("park_desc"));
			System.out.println("--------------------");
		}
	}
	
	@Test
	public void addRemain() throws Exception {
		SolrServer httpSolrServer = new HttpSolrServer("http://192.168.161.128:9080/solr/collection1");
		String parkId = "2101060150002";
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
		
		//新建一个doc,减1后执行更新
		SolrInputDocument document = new SolrInputDocument();
		
		document.addField("id", parkId);
		
		//局部更新park_remainCount字段
		Map<String, String> operation = new HashMap<>();
		operation.put("set", remainCount+1+"");
		document.addField("park_remainCount", operation);
		
		httpSolrServer.add(document);
		httpSolrServer.commit();
	}
	
	@Test
	public void update() throws Exception {
		SolrServer httpSolrServer = new HttpSolrServer("http://192.168.161.128:9080/solr/collection1");
		String parkId = "2101060150019";
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
		
		/*Long remainCount = (Long) list.get(0).get("park_remainCount");
		Long workingCount = (Long) list.get(0).get("park_workingCount");
		//剩余车位数不应该大于总车位数的
		if(remainCount >= workingCount) {
			throw new RuntimeException("remainCount >= workingCount!");
		}*/
		
		//新建一个doc,减1后执行更新
		SolrInputDocument document = new SolrInputDocument();
		
		document.addField("id", parkId);
		
		//局部更新park_remainCount字段
		Map<String, String> operation = new HashMap<>();
		operation.put("set", "1");
		document.addField("park_state", operation);
		
		httpSolrServer.add(document);
		httpSolrServer.commit();
	}
		
	

	@Test
	public void addTest() throws SolrServerException, IOException {
		//创建一个SolrServer对象，创建一个连接。参数solr服务的url
		SolrServer solrServer = new HttpSolrServer("http://solr.antido.cn:70/solr/collection1");
		//创建一个文档对象SolrInputDocument
		SolrInputDocument document = new SolrInputDocument();
		//向文档对象中添加域。文档中必须包含一个id域，所有的域的名称必须在schema.xml中定义。
		document.addField("id", "test_1");
		document.addField("park_name", "solr远程连接测试");
		document.addField("park_desc", "Dubbo商业化-支持RPC服务,全新一代互联网微服务框架,支撑阿里巴巴99%以上的大规模应用");
		document.addField("park_cityId","210100");
		//把文档写入索引库
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
	
	@Test
	public void updateTest() throws SolrServerException, IOException {
		//创建一个SolrServer对象，创建一个连接。参数solr服务的url
		SolrServer solrServer = new HttpSolrServer("http://192.168.225.128:8090/solr/collection1");
		//创建一个文档对象SolrInputDocument
		SolrInputDocument document = new SolrInputDocument();
		//向文档对象中添加域。文档中必须包含一个id域，所有的域的名称必须在schema.xml中定义。
		document.addField("id", "test2");
		document.addField("park_name", "update");
		document.addField("park_desc", "Dubbo商业化-支持RPC服务,全新一代互联网微服务框架,支撑阿里巴巴99%以上的大规模应用");
		document.addField("park_cityId","210100");
		document.addField("park_price",null);
		//把文档写入索引库
		solrServer.add(document);
		//提交
		solrServer.commit();
		/*try {
			int a = 1/0;
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
	}
	
	@Test
	public void queryTest() throws SolrServerException, IOException {
		//创建一个SolrServer对象，创建一个连接。参数solr服务的url
		SolrServer solrServer = new HttpSolrServer("http://192.168.225.128:8090/solr/collection1");
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("park_name:搜索");
		QueryResponse response = solrServer.query(solrQuery);
		
		SolrDocumentList documentList = response.getResults();
		
		for (SolrDocument doc : documentList) {
			System.out.println(doc.get("park_name"));
			System.out.println(doc.get("park_desc"));
			System.out.println(doc.get("park_price"));
			System.out.println(doc.get("park_type"));
		}
		
	}
	
	@Test
	public void queryHeightTest() throws SolrServerException, IOException {
		//创建一个SolrServer对象，创建一个连接。参数solr服务的url
		SolrServer solrServer = new HttpSolrServer("http://192.168.225.128:8090/solr/collection1");
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("park_keywords:一");
		
		//设置高亮
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("park_name,park_desc");
		solrQuery.setHighlightSimplePre("<em style='color:red'>");
		solrQuery.setHighlightSimplePost("</em>");
		
	
		QueryResponse response = solrServer.query(solrQuery);
		
		SolrDocumentList documentList = response.getResults();
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		for (SolrDocument doc : documentList) {
			System.out.println("name:"+doc.get("park_name"));
			
			List<String> nameList = highlighting.get(doc.get("id")).get("park_name");
			System.out.println(nameList);
			
			List<String> descList = highlighting.get(doc.get("id")).get("park_desc");
			System.out.println(descList);
			
			/*System.out.println(highlighting.get(doc.get("id")).get("park_name") );
			
			
			System.out.println(doc.get("park_name"));
			System.out.println(doc.get("park_desc"));
			System.out.println(doc.get("park_price"));
			System.out.println(doc.get("park_type"));*/
		}
		
	}
	
	@Test
	public void queryTest2() throws Exception {
		//创建一个SolrServer对象，创建一个连接。参数solr服务的url
		SolrServer solrServer = new HttpSolrServer("http://192.168.225.128:8090/solr/collection1");
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("park_keywords:停车场");
		//solrQuery.set("fq", "park_state:3");
		solrQuery.setStart(0);
		solrQuery.setRows(5);
		//solrQuery.addField("park_name"); //设置要取得域
		solrQuery.addFilterQuery("park_state:3");
		
		QueryResponse response = solrServer.query(solrQuery);
		
		SolrDocumentList documentList = response.getResults();
		System.out.println("numFound:"+documentList.getNumFound());
		
		for (SolrDocument doc : documentList) {
			System.out.println(doc.get("id"));
			if (doc.get("id") instanceof String) {
				System.out.println("id is str");
			} else if(doc.get("id") instanceof Number) {
				System.out.println("id is num");
			}
			System.out.println(doc.get("park_districtName"));
			System.out.println(doc.get("park_name"));
			
			
			System.out.println(doc.get("park_desc"));
			
			System.out.println(doc.get("park_price"));
			if (doc.get("park_price") instanceof String) {
				System.out.println("park_price is str");
			} else if(doc.get("park_price") instanceof Number) {
				System.out.println("park_price is num");
			}
			
			System.out.println(doc.get("park_type"));
			System.out.println(doc.get("park_state"));
			System.out.println("--------------------");
		}
		
	}
	
	@Test
	public void importTest() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		ParkMapper mapper = applicationContext.getBean(ParkMapper.class);
		DistrictMapper Dismapper = applicationContext.getBean(DistrictMapper.class);
		SolrServer solrServer = new HttpSolrServer("http://192.168.225.128:8090/solr/collection1");
		
		List<Park> list = null;
		PageInfo<Park> pageInfo = null;
		
		int count = 0; //导入次数 计数
		int pageSize = 10000; //每次导入的数据条数
		int lastPage = count + 1; //数据总页数
	
		while(true) {
			if(lastPage == count) { //当最后一页被查询完毕后跳出
				break;
			} else {
				count++;
			}
			// 设置每次获取的数据数
			PageHelper.startPage(count, pageSize); 
			list = mapper.selectParkInfo();
			pageInfo = new PageInfo<>(list);
			///nextPage = pageInfo.getNextPage();
			lastPage = pageInfo.getLastPage();
			System.out.println("---"+lastPage+"---");
			System.out.println("---count:"+count+"---");
			System.out.println("---"+list.size());
			
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
				<field name="park_DistirctId" type="string" indexed="true" stored="true" />
				<field name="park_DistirctName" type="string" indexed="true" stored="true" />
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
				if(park.getEast_longitude() == null || park.getNorth_latitude() == null) {
					document.addField("park_east",0);
					document.addField("park_north",0);
				} else {
					document.addField("park_east",park.getEast_longitude());
					document.addField("park_north",park.getNorth_latitude());
				}
				
				//所属城市
				if(park.getCity_id() == null) {
					document.addField("park_cityId",0);
					document.addField("park_cityName","");
				} else {
					//获取city信息
					District city = Dismapper.selectByPrimaryKey(Integer.parseInt(park.getCity_id()));
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
					document.addField("park_DistirctId",0);
					document.addField("park_DistirctName","");
				} else {
					//获取district信息
					District district = Dismapper.selectByPrimaryKey(Integer.parseInt(park.getDistrict_id()));
					if(district == null) {
						document.addField("park_DistirctId",0);
						document.addField("park_DistirctName","");	
					} else {
						document.addField("park_DistirctId",district.getId());
						//城市名
						document.addField("park_DistirctName",district.getName());
					}
				}
				
				//把文档写入索引库
				solrServer.add(document);
			}
			//提交
			solrServer.commit();
			System.out.println("commit");
			//Thread.sleep(60000L);
		}
	}
	
}
