package cn.antido.admin.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.antido.admin.mapper.BaseDictMapper;
import cn.antido.admin.mapper.DistrictMapper;
import cn.antido.admin.mapper.NodeAddressMapper;
import cn.antido.admin.mapper.NodeMapper;
import cn.antido.admin.mapper.ParkAdminMapper;
import cn.antido.admin.mapper.ParkMapper;
import cn.antido.admin.mapper.ParkModelMapper;
import cn.antido.admin.mapper.SpaceMapper;
import cn.antido.admin.mapper.StreetMapper;
import cn.antido.admin.pojo.BaseDict;
import cn.antido.admin.pojo.District;
import cn.antido.admin.pojo.Node;
import cn.antido.admin.pojo.NodeAddress;
import cn.antido.admin.pojo.Park;
import cn.antido.admin.pojo.ParkAdmin;
import cn.antido.admin.pojo.ParkModel;
import cn.antido.admin.pojo.Street;
import cn.antido.admin.pojo.dto.ParkAddFormDTO;
import cn.antido.admin.pojo.dto.ParkInfoEditEchoDTO;
import cn.antido.admin.pojo.dto.ParkMapDTO;
import cn.antido.admin.pojo.dto.ParkModelDTO;
import cn.antido.admin.pojo.dto.ParkModelItemDTO;
import cn.antido.admin.pojo.filter.DaoFilter;
import cn.antido.admin.service.NodeService;
import cn.antido.admin.service.ParkService;
import cn.antido.common.pojo.PageBean;
import cn.antido.common.utils.FormatUtils;
import cn.antido.common.utils.IPUtils;
import cn.antido.common.utils.UUIDUtils;
/**
 * @Description ParkService实现类
 * @author Antido 
 * @date 2017年12月14日 下午7:09:59
 */
@Service
@Transactional
public class ParkServiceImpl implements ParkService {

	@Autowired
	private ParkMapper parkMapper;
	
	@Autowired
	private SpaceMapper spaceMapper;
	
	@Autowired
	private BaseDictMapper baseDictMapper;
	
	@Autowired
	private ParkAdminMapper parkAdminMapper;
	
	@Autowired
	private StreetMapper streetMapper;
	
	@Autowired
	private DistrictMapper districtMapper;
	
	@Autowired
	private ParkModelMapper parkModelMapper;
	
	@Autowired
	private NodeMapper nodeMapper;
	
	@Autowired
	private NodeAddressMapper nodeAddressMapper;
	
	
	
	//Spring提供的JMS工具类 ，用来发送和接收消息
	@Autowired
	private JmsTemplate jmsTemplate;
	
	//根据id注入 消息名为parkAddTopicDestination
	@Resource
	private Destination parkAddTopicDestination;
	
	@Resource
	private Destination parkDelTopicDestination;
	
	@Resource
	private Destination parkUpdateTopicDestination;
	
	/**
	 * 根据id获取Park对象
	 */
	@Override
	@Transactional(readOnly=true)
	public Park getParkById(Long id) {
		Park park = parkMapper.selectByPrimaryKey(id);
		if(park == null) return null;
		String ipStr = FormatUtils.long2Ip(park.getService_ip());
		park.setServiceIpStr(ipStr);
		return park;
	}
	
	/**
	 * 获取完整的park对象<br>
	 * 此方法被在前台park.antido.cn加载时调用<br>
	 * 补全park中包含的其他对象<br>
	 * 当park不存在是或者park处于非正常使用状态时返回null<br>
	 * 使用redis做二级缓存
	 */
	@Override
	@Transactional(readOnly=true)
	public Park getCompleteParkById(Long id) {
		//TODO: 做缓存
		Park park = parkMapper.selectByPrimaryKey(id);
		if(park == null || park.getState() != 1) {
			return null;
		}
		
		//补全管理员对象
		ParkAdmin parkAdmin = park.getParkAdmin();
		if(parkAdmin != null && parkAdmin.getId() != null) {
			parkAdmin = parkAdminMapper.selectSimpleByPrimaryKey(parkAdmin.getId());
			if(parkAdmin != null) 
				park.setParkAdmin(parkAdmin);
		}
		
		//补全街道区域对象对象
		Street street = park.getStreet();
		if(street != null && street.getId() != null) {
			street = streetMapper.selectByPrimaryKey(street.getId());
			if(street != null) {
				park.setStreet(street);
				District district = street.getDistrict();
				if(district != null && district.getId() != null) {
					district = districtMapper.selectByPrimaryKey(district.getId());
					if(district != null)
						street.setDistrict(district);
				}
			}
		}
		
		return park;
	}
	
	
	/**
	 * 获取停车场分页对象<br>
	 * 管理员对象id,name<br>
	 * 对可能包含的区域信息做限制查询
	 */
	@Override
	@Transactional(readOnly=true)
	public PageBean<Park> getPageBean(Integer PAGE_SIZE, Integer currentPage, Integer regionId) {
		//设置分页属性 
		PageHelper.startPage(currentPage, PAGE_SIZE);
		List<Park> list = null;
		
		if(regionId == null || regionId == 0) {
			//没有限制查所有
			list = parkMapper.selectAll();
		} else {
			if(regionId > 999999) { // street级别的搜索
				list = parkMapper.selectAllByStreetId(regionId);
			} else { //district 级别的搜索
				if (regionId % 100 != 0) {  //后两位不为0则位区县一级
					list = parkMapper.selectAllByDistrictId(regionId);
				} else {
					if(regionId % 10000 != 0) { //中间两位不为0则市一级
						list = parkMapper.selectAllByCityId(regionId);
					} else { //后四位都是0则省一级
						list = parkMapper.selectAllByProvinceId(regionId);
					}
				}
			}
		}
		
		PageInfo<Park> pageInfo = new PageInfo<>(list);
		//新建PageBean
		PageBean<Park> pageBean = new PageBean<Park>();
		
		pageBean.setCurrentPage(currentPage);
		pageBean.setPageSize(PAGE_SIZE);
		//通过PageInfo获取数据总数
		pageBean.setTotalCount(pageInfo.getTotal());
		//通过PageInfo获取总页数
		pageBean.setTotalPages(pageInfo.getPages());
		pageBean.setDataList(list);
		
		return pageBean;
	}

	/**
	 * 根据街道id获取当前停车场列表<br>
	 * 数据中只包含name,id
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Park> getParkByStreetId(Integer street_id) {
		if(street_id == null) {
			return null;
		}
		List<Park> list = parkMapper.selectByStreetId(street_id);
		return list;
	}

	/**
	 * 根据parkId封装ParkModelDTO
	 */
	@Override
	@Transactional(readOnly=true)
	public ParkModelDTO getParkModelDTOByParkId(Long parkId) {
		Park park = parkMapper.selectByPrimaryKey(parkId);
		if(park == null) {
			return null;
		}
		
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		String parkState = null;
		String parkPrice = null;
		String parkUsing = null;
		String parkPosition = null;
		String createdStr = null;
		String updatedStr = null;
		String serviceIp = null;
		
		Byte state = park.getState();
		if(state == 0) {
			parkState = "已废弃";
		} else if(state == 1) {
			parkState = "正常使用";
		} else if(state == 2) {
			parkState = "维护中";
		} else {
			parkState = "开发中";
		}
		
		Integer price = park.getPrice();
		if(price == 0) {
			parkPrice = "免费";
		} else {
			//转换为元/小时 结果保留两位小数
			String priceStr = decimalFormat.format(price / 100);
			parkPrice = priceStr + "（元/小时）";
		}
		
		//计算使用情况
		Integer design_count = park.getDesign_count();
		Integer working_count = park.getWorking_count();
		Integer using_count = park.getUsing_count();
		if(design_count != null && working_count != null && using_count != null) {
			if(working_count.equals(0)) {
				parkUsing = "0.00%正常工作，"+"0.00%被使用。";
			} else {
				String workingPercent = decimalFormat.format((double)working_count / design_count * 100);
				String usingPercent = decimalFormat.format((double)using_count / working_count * 100);
				parkUsing = workingPercent+"%正常工作，"+usingPercent+"%被使用。";
			}
		}
		
		Integer east_longitude = park.getEast_longitude();
		Integer north_latitude = park.getNorth_latitude();
		if(east_longitude != null && north_latitude != null) {
			String eastStr = FormatUtils.eastAndNorthFormat(east_longitude);
			String northStr = FormatUtils.eastAndNorthFormat(north_latitude);
			parkPosition = "东经"+eastStr+"°，北纬"+northStr+"°";
		}
		
		createdStr = FormatUtils.dateFormat(park.getCreated());
		updatedStr = FormatUtils.dateFormat(park.getUpdated());
		
		Long service_ip = park.getService_ip();
		if(service_ip != null) {
			serviceIp = IPUtils.long2Ip(service_ip);
		}
		
		ParkModelDTO parkModelDTO = new ParkModelDTO();
		parkModelDTO.setPark(park);
		parkModelDTO.setCreatedStr(createdStr);
		parkModelDTO.setUpdatedStr(updatedStr);
		parkModelDTO.setParkPosition(parkPosition);
		parkModelDTO.setParkPrice(parkPrice);
		parkModelDTO.setParkState(parkState);
		parkModelDTO.setParkUsing(parkUsing);
		parkModelDTO.setServiceIp(serviceIp);
		
		return parkModelDTO;
	}

	/**
	 * 新增一个停车场<BR>
	 * 返回添加情况<BR>
	 * "success"表示成功<BR>
	 * 失败则返回其他
	 */
	@Override
	public HashMap<String,String> addParkByDTO(ParkAddFormDTO parkAddFormDTO) {
		//将DTO数据正确封装到Park对象中
		HashMap<String, String> callback = new HashMap<String,String>();
		StringBuffer message = new StringBuffer();
		String state = null;
		Park park = new Park();
		
		final Long parkId; //该变量只会被赋值一次，同时作为JMSTemplate发送消息的匿名内部类使用
				
		Street street = streetMapper.selectByPrimaryKey(parkAddFormDTO.getStreetId());
		if(street != null) {
			park.setStreet(street);
			
			//根据当前街道获取最大id
			Long maxIdByStreetId = parkMapper.selectMaxIdByStreetId(street.getId());
			
			if(maxIdByStreetId != null) {
				//当前街道或区域内有其他注册过的停车场
				//新停车场id为最大id加1
				parkId = maxIdByStreetId + 1;
				park.setId(parkId);
			} else {
				//当前街道或区域内没有任何停车场
				//新停车场id为街道id加后补0000加1(streetId * 10000 + 1)
				Integer streetId = street.getId();
				parkId = streetId * 10000L + 1L;
				park.setId(parkId);
			}
		} else {
			/**
			 * 如果绑定街道失败则返回error,不添加任何数据
			 */
			state = "error";
			message.append("未找到当前街道/区域,添加失败.<BR>");
			callback.put("state", state);
			callback.put("message", message.toString());
			return callback;
		}
		
		
		
		//封装负责人
		if(parkAddFormDTO.getRadioAdmin() == 0) {  //0-默认区域管理员 , 1-暂不选择 , 2-选择其他管理员
			ParkAdmin parkAdmin = parkAdminMapper.selectSimpleByPrimaryKey(parkAddFormDTO.getDefaultParkAdmin());
			if(parkAdmin != null) {
				park.setParkAdmin(parkAdmin);
			} else {
				message.append("未找到该管理员,绑定失败.<BR>");
			}
			
		} else if(parkAddFormDTO.getRadioAdmin() == 2) {
			ParkAdmin parkAdmin = parkAdminMapper.selectSimpleByPrimaryKey(parkAddFormDTO.getSelectParkAdmin());
			if(parkAdmin != null) {
				park.setParkAdmin(parkAdmin);
			} else {
				message.append("未找到该管理员,绑定失败.<BR>");
			}
		}
		
		//封装经纬度
		//将xxx.xxxxxx格式的坐标数据转换为Integer类型存储
		Integer intEast = (int) (parkAddFormDTO.getEast()*1000000);
		Integer intNorth = (int) (parkAddFormDTO.getNorth()*1000000);
		park.setEast_longitude(intEast);
		park.setNorth_latitude(intNorth);
		
		//封装价格参数
		if(parkAddFormDTO.getIsFree() == 0) {   //0-不免费 , 1-免费
			Integer free_time = parkAddFormDTO.getFreeTime()*60;
			park.setFree_time(free_time);
			Integer price = new Double(parkAddFormDTO.getPrice()*100).intValue();
			park.setPrice(price);
		} else {
			park.setPrice(0);
			park.setFree_time(0);
		}
		
		//封装IP
		String ipStr = parkAddFormDTO.getServiceIp();
		Long ip = IPUtils.ip2Long(ipStr);
		park.setService_ip(ip);
		
		//根据ID获取控制器的节点父ID
		NodeAddress nodeAddress = nodeAddressMapper.selectByAddress(ipStr);
		
		if(nodeAddress != null) {
			Node node = new Node();
			node.setId(nodeAddress.getNode_id());
			park.setNode(node);
		} else { 
			/**
			 * 当该控制器地址不存在时
			 * 1.当前新增停车场暂不绑定底层节点控制器
			 * 2.判断当前IP是否为虚拟IP(虚拟的停车场做测试使用,并未真正与硬件相连)
			 *   新增一个虚拟的控制器地址,并根据停车场所需的车型数在目标控制器下新增相应数量的虚拟节点
			 *   新增的节点会一直保持正常状态
			 * 这里对第二种情况做处理
			 */
			//以192.168.0.开头的
			if(ipStr.startsWith("192.168.0.")) {
				NodeAddress addr = new NodeAddress();
				Node father = new Node(); //这个父节点是不需要存入DB,是address与node沟通的桥梁
				father.setId(UUIDUtils.getUUID32());
				
				//新的节点通讯地址
				addr.setAddress(ipStr);
				addr.setConn_key("FAKE");
				addr.setCreated(new Date());
				addr.setNode_id(father.getId());
				addr.setPort(0);
				addr.setState((byte)1);
				nodeAddressMapper.insert(addr);
				
				//根据车位数新建节点
				for (int i = 0; i < parkAddFormDTO.getDesignCount(); i++) {
					Node node = new Node();
					node.setCreated(new Date());
					node.setId(UUIDUtils.getUUID32());
					node.setIs_close(false);
					node.setIs_lock(false);
					node.setIs_online(true);
					node.setNode_desc(parkAddFormDTO.getName());
					node.setParent_id(father.getId());
					nodeMapper.insertByNode(node);
				}
				
				park.setNode(father);
			}
			
		}
		
		
		//保存model数据
		Integer row = parkAddFormDTO.getModRow();
		Integer col = parkAddFormDTO.getModCol();
		String info = parkAddFormDTO.getModInfo();
		
		park.setDirection(parkAddFormDTO.getModDirect());
		park.setModel_col(col);
		park.setModel_row(row);
		
		//info中的数据是先遍历行内每个元素 依次遍历所有行
		String[] split = info.split(",");
		int currentRow = 0;
		int currentCol = 0;
		StringBuffer sb = new StringBuffer();
		String ParkId = park.getId().toString();
		for (int i = 0; i < split.length; i++) {
			if(col.equals(currentCol)) { //超过当前行的最后一个元素
				currentCol = 0;
				currentRow++;
			}
			if(!"0".equals(split[i])) { //入库
				
				ParkModel parkModel = new ParkModel();
				
				//把整型数据高位补0
				sb.append(currentRow);
				while (sb.length() < 3) {
					sb.insert(0, "0");
				}
				sb.append(currentCol);
				while (sb.length() < 6) {
					sb.insert(3, "0");
				}
				
				parkModel.setId(ParkId+sb.toString());
				parkModel.setState(new Byte(split[i]));
				parkModelMapper.insertParkModel(parkModel);
				
				sb.delete(0, sb.length());
			}
			currentCol++;
		}
		
		park.setCity_id(parkAddFormDTO.getCityId().toString());
		park.setDistrict_id(parkAddFormDTO.getDistrictId().toString());
		park.setProvince_id(parkAddFormDTO.getProvinceId().toString());
		park.setDesign_count(parkAddFormDTO.getDesignCount());
		park.setName(parkAddFormDTO.getName());
		park.setPosition_desc(parkAddFormDTO.getParkPositionDesc());
		park.setPark_type(parkAddFormDTO.getParkType());
		
		//添加后默认状态为开发中
		park.setState((byte)3); //0-废弃,1-使用中,2-维护中,3-开发中
		park.setCreated(new Date());
		park.setUpdated(new Date());
		
		parkMapper.insertPark(park);
		
		if(message.length() > 0) {
			state = "exception";
		} else {
			state = "success";
		}
		
		callback.put("state",state);
		callback.put("message",message.toString());
		
		
		//发送消息在索引库中新增park
		jmsTemplate.send(parkAddTopicDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				//将新增的parkId作为消息内容
				TextMessage textMessage = session.createTextMessage(parkId+"");
				return textMessage;
			}
		});
		
		return callback;
	}

	
	/**
	 * 封装开发中的停车场pageBean
	 * 
	 */
	@Override
	@Transactional(readOnly = true)
	public PageBean<Park> getDevPageBean(Integer PAGE_SIZE, Integer currentPage, Integer regionId) {
		//设置分页属性 
		PageHelper.startPage(currentPage, PAGE_SIZE);
		List<Park> list = null;
		//新增state = 3 过滤查询条件
		DaoFilter filter = new DaoFilter();
		filter.addAndCriteria("state = ", 3);
		
		if(regionId == null || regionId == 0) {
			//没有限制查所有
			list = parkMapper.selectByFilter(filter);
		} else {
			if(regionId > 999999) { // street级别的搜索
				filter.addAndCriteria("street_id = ", regionId);
				list = parkMapper.selectByFilter(filter);
			} else { //district 级别的搜索
				if (regionId % 100 != 0) {  //后两位不为0则位区县一级
					filter.addAndCriteria("district_id = ", regionId);
					list = parkMapper.selectByFilter(filter);
				} else {
					if(regionId % 10000 != 0) { //中间两位不为0则市一级
						filter.addAndCriteria("city_id = ", regionId);
						list = parkMapper.selectByFilter(filter);
					} else { //后四位都是0则省一级
						filter.addAndCriteria("province_id = ", regionId);
						list = parkMapper.selectByFilter(filter);
					}
				}
			}
		}
		
		PageInfo<Park> pageInfo = new PageInfo<>(list);
		//新建PageBean
		PageBean<Park> pageBean = new PageBean<Park>();
		
		pageBean.setCurrentPage(currentPage);
		pageBean.setPageSize(PAGE_SIZE);
		//通过PageInfo获取数据总数
		pageBean.setTotalCount(pageInfo.getTotal());
		//通过PageInfo获取总页数
		pageBean.setTotalPages(pageInfo.getPages());
		pageBean.setDataList(list);
		
		return pageBean;
	}

	/**
	 * 封装需要编辑的停车场表单数据传输对象
	 */
	@Override
	@Transactional(readOnly=true)
	public ParkInfoEditEchoDTO getInfoEditEchoDTO(Long parkId) {
		Park park = parkMapper.selectByPrimaryKey(parkId);
		if(park == null) {
			return null;
		}
		
		Long id = park.getId();
		String parkName = park.getName();
		
		//时间回显数据
		String createdStr = FormatUtils.dateFormat(park.getCreated());
		String updatedStr =FormatUtils.dateFormat(park.getUpdated());
		
		//区域回显数据
		int provinceSelected = Integer.parseInt(park.getProvince_id());
		int citySelected = Integer.parseInt(park.getCity_id());
		int districtSelected = Integer.parseInt(park.getDistrict_id());
		int streetSelected = park.getStreet().getId();
		List<District> provinceEcho = districtMapper.selectByParentIdIsNull();
		List<District> cityEcho = districtMapper.selectByParentId(provinceSelected);
		List<District> districtEcho = districtMapper.selectByParentId(citySelected);
		District districtTemp = new District();
		districtTemp.setId(districtSelected);
		List<Street> streetEcho = streetMapper.selectByDistrict(districtTemp);
		
		//停车场类型回显数据
		int parkType = (int) park.getPark_type();
		List<BaseDict> parkTypeDict = baseDictMapper.selectByTypeCode(1); //1-停车场类型
		
		//停车场状态回显数据
		int state = (int) park.getState();
		List<BaseDict> stateDict = baseDictMapper.selectByTypeCode(2); // 2-停车场状态
		
		Integer designCount = park.getDesign_count();
		Integer workingCount = park.getWorking_count();
		String positionDesc = park.getPosition_desc();
		
		//负责人回显数据
		Integer adminId = park.getParkAdmin().getId();
		String adminName = park.getParkAdmin().getName();
		
		//价格回显数据
		String priceStr = null;
		String freeTimeStr = null;
		Integer price = park.getPrice();
		boolean isFree = true;
		if(price > 0) {
			isFree = false;
			priceStr = FormatUtils.priceFormat(price);
			Integer freeTime = park.getFree_time();
			freeTimeStr = freeTime / 60 + "";
		}
		
		//经纬度回显数据
		String east = FormatUtils.eastAndNorthFormat(park.getEast_longitude());
		String north = FormatUtils.eastAndNorthFormat(park.getNorth_latitude());
		
		//IP回显数据
		String serviceIp = FormatUtils.long2Ip(park.getService_ip());
		
		//封装
		ParkInfoEditEchoDTO echoDTO = new ParkInfoEditEchoDTO();
		echoDTO.setAdminId(adminId);
		echoDTO.setAdminName(adminName);
		echoDTO.setCreatedStr(createdStr);
		echoDTO.setDesignCount(designCount);
		echoDTO.setEast(east);
		echoDTO.setFreeTime(freeTimeStr);
		echoDTO.setIsFree(isFree);
		echoDTO.setParkName(parkName);
		echoDTO.setNorth(north);
		echoDTO.setParkId(id);
		echoDTO.setParkType(parkType);
		echoDTO.setParkTypeDict(parkTypeDict);
		echoDTO.setPositionDesc(positionDesc);
		echoDTO.setPrice(priceStr);
		echoDTO.setServiceIp(serviceIp);
		echoDTO.setState(state);
		echoDTO.setStateDict(stateDict);
		echoDTO.setUpdatedStr(updatedStr);
		echoDTO.setWorkingCount(workingCount);
		
		echoDTO.setStreetEcho(streetEcho);
		echoDTO.setProvinceEcho(provinceEcho);
		echoDTO.setDistrictEcho(districtEcho);
		echoDTO.setCityEcho(cityEcho);
		
		HashMap<String, Number> selectedEcho = new HashMap<String,Number>();
		selectedEcho.put("provinceSelected", provinceSelected);
		selectedEcho.put("citySelected", citySelected);
		selectedEcho.put("districtSelected", districtSelected);
		selectedEcho.put("streetSelected", streetSelected);
		echoDTO.setSelectedEcho(selectedEcho);
		
		return echoDTO;
	}


	/**
	 * 根据传输对象中的数据更新park
	 */
	@Override
	public HashMap<String,String> updateParkByDTO(ParkAddFormDTO parkAddFormDTO) {
		HashMap<String, String> callback = new HashMap<String,String>();
		StringBuffer message = new StringBuffer();
		String state = null;
		
		//确认是否存在的park
		Park park = parkMapper.selectByPrimaryKey(parkAddFormDTO.getParkId());
		if(park == null) {
			message.append("未找到当前街道/区域,");
			callback.put("state", "error");
			callback.put("message", message.toString());
			return callback;
		}
		if(park.getState() != 3) {
			message.append("修改非法的停车场,");
			callback.put("state", "error");
			callback.put("message", message.toString());
			return callback;
		}
		
		Street street = streetMapper.selectByPrimaryKey(parkAddFormDTO.getStreetId());
		if(street != null) {
			park.setStreet(street);
			if( ! street.getDistrict().getId().equals(parkAddFormDTO.getDistrictId())) { //区域数据异常
				message.append("区域数据异常,");
				callback.put("state", "error");
				callback.put("message", message.toString());
				return callback;
			}
		} else {
			message.append("区域数据异常");
			callback.put("state", "error");
			callback.put("message", message.toString());
			return callback;
		}
		
		//封装负责人
		if(parkAddFormDTO.getRadioAdmin() == 0) {  //0-默认区域管理员 , 1-暂不选择 , 2-选择其他管理员
			ParkAdmin parkAdmin = parkAdminMapper.selectSimpleByPrimaryKey(parkAddFormDTO.getDefaultParkAdmin());
			if(parkAdmin != null) {
				park.setParkAdmin(parkAdmin);
			} else {
				message.append("未找到该管理员,");
				callback.put("state", "error");
				callback.put("message", message.toString());
				return callback;
			}
			
		} else if(parkAddFormDTO.getRadioAdmin() == 2) {
			ParkAdmin parkAdmin = parkAdminMapper.selectSimpleByPrimaryKey(parkAddFormDTO.getSelectParkAdmin());
			if(parkAdmin != null) {
				park.setParkAdmin(parkAdmin);
			} else {
				message.append("未找到该管理员,");
				callback.put("state", "error");
				callback.put("message", message.toString());
				return callback;
			}
		}
		
		//封装经纬度
		//将xxx.xxxxxx格式的坐标数据转换为Integer类型存储
		Integer intEast = (int) (parkAddFormDTO.getEast()*1000000);
		Integer intNorth = (int) (parkAddFormDTO.getNorth()*1000000);
		park.setEast_longitude(intEast);
		park.setNorth_latitude(intNorth);
		
		//封装价格参数
		if(parkAddFormDTO.getIsFree() == 0) {   //0-不免费 , 1-免费
			Integer free_time = parkAddFormDTO.getFreeTime()*60;
			park.setFree_time(free_time);
			Integer price = new Double(parkAddFormDTO.getPrice()*100).intValue();
			park.setPrice(price);
		} else {
			park.setPrice(0);
			park.setFree_time(0);
		}
		
		//封装IP
		String ipStr = parkAddFormDTO.getServiceIp();
		Long ip = IPUtils.ip2Long(ipStr);
		park.setService_ip(ip);
		
		//根据ID获取控制器的节点父ID
		NodeAddress nodeAddress = nodeAddressMapper.selectByAddress(ipStr);
		
		if(nodeAddress != null) {
			Node node = new Node();
			node.setId(nodeAddress.getNode_id());
			park.setNode(node);
		}
		
		//保存model数据
		Integer row = parkAddFormDTO.getModRow();
		Integer col = parkAddFormDTO.getModCol();
		String info = parkAddFormDTO.getModInfo();
		//先清空再输入
		parkModelMapper.deleteParkModelByParkId(park.getId());
		
		park.setDirection(parkAddFormDTO.getModDirect());
		park.setModel_col(col);
		park.setModel_row(row);
		
		//info中的数据是先遍历行内每个元素 依次遍历所有行
		String[] split = info.split(",");
		int currentRow = 0;
		int currentCol = 0;
		StringBuffer sb = new StringBuffer();
		String ParkId = park.getId().toString();
		for (int i = 0; i < split.length; i++) {
			if(col.equals(currentCol)) { //超过当前行的最后一个元素
				currentCol = 0;
				currentRow++;
			}
			if(!"0".equals(split[i])) { //入库
				
				ParkModel parkModel = new ParkModel();
				
				//把整型数据高位补0
				sb.append(currentRow);
				while (sb.length() < 3) {
					sb.insert(0, "0");
				}
				sb.append(currentCol);
				while (sb.length() < 6) {
					sb.insert(3, "0");
				}
				
				parkModel.setId(ParkId+sb.toString());
				parkModel.setState(new Byte(split[i]));
				parkModelMapper.insertParkModel(parkModel);
				
				sb.delete(0, sb.length());
			}
			currentCol++;
		}
		
		
		park.setCity_id(parkAddFormDTO.getCityId().toString());
		park.setDistrict_id(parkAddFormDTO.getDistrictId().toString());
		park.setProvince_id(parkAddFormDTO.getProvinceId().toString());
		park.setDesign_count(parkAddFormDTO.getDesignCount());
		park.setName(parkAddFormDTO.getName());
		park.setPosition_desc(parkAddFormDTO.getParkPositionDesc());
		park.setPark_type(parkAddFormDTO.getParkType());
		
		//更改停车场状态
		park.setState(parkAddFormDTO.getState()); //0-废弃,1-使用中,2-维护中,3-开发中
	
		park.setUpdated(new Date());
		
		parkMapper.updateByPark(park);
		
		//更新索引库中的park信息
		final Long parkId = park.getId();
		jmsTemplate.send(parkAddTopicDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(parkId + "");
				return textMessage;
			}
		});
		
		if(message.length() > 0) {
			state = "exception";
		} else {
			state = "success";
		}
		
		callback.put("state",state);
		callback.put("message",message.toString());
				
		return callback;
	}


	/**
	 * 封装ParkMapDTO
	 */
	@Override
	@Transactional(readOnly = true)
	public ParkMapDTO getParkMapDTOByParkId(Long parkId) {
		
		//获取停车场Map基本信息
		Park park = parkMapper.selectMapInfoById(parkId);
		if(park == null) {
			return null;
		}
		ParkMapDTO parkMapDTO = new ParkMapDTO();
		parkMapDTO.setDirection(park.getDirection());
		parkMapDTO.setRow(park.getModel_row());
		parkMapDTO.setCol(park.getModel_col());
		
		List<ParkModel> list = parkModelMapper.selectAllModelByParkId(parkId);
		List<ParkModelItemDTO> items = new ArrayList<ParkModelItemDTO>();
		for (ParkModel parkModel : list) {
			String id = parkModel.getId();
			Integer xAxis = Integer.parseInt(id.substring(13, 13+3));
			Integer yAxis = Integer.parseInt(id.substring(13+3));
			ParkModelItemDTO item = new ParkModelItemDTO();
			item.setxAxis(xAxis);
			item.setyAxis(yAxis);
			item.setState(parkModel.getState());
			items.add(item);
		}
		parkMapDTO.setItems(items);
		return parkMapDTO;
	}

	/**
	 * 根据ID删除Park,同时删除所有与之关联的Space,ParkModel,清空相关Node
	 */
	@Override
	public void deletePark(Long id) {
		Park park = parkMapper.selectByPrimaryKey(id);
		if(park == null) {
			return ;
		}
		final Long parkId = park.getId();
		
		//删除Node
		Node node = park.getNode();
		if(node != null && node.getId() != null) {
			//将停车场绑定的协调器下所用的子节点全变为自由状态(spaceId = null)
			nodeMapper.removeSpaceIdByparentId(node.getId());
		}
		//删除Park
		parkMapper.deleteById(parkId);
		//删除Space
		spaceMapper.deleteSpaceByParkId(parkId);
		//删除ParkMode
		parkModelMapper.deleteParkModelByParkId(parkId);
	
		///发送消息删除索引库中的Park有关信息
		jmsTemplate.send(parkDelTopicDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				//将需要删除的parkId作为消息内容
				TextMessage textMessage = session.createTextMessage(parkId+"");
				return textMessage;
			}
		});
		
	}

	/**
	 * 根据ID发布Park,更改park_state,更新solr索引,
	 */
	@Override
	public void publishPark(Long id) {
		Park park = parkMapper.selectByPrimaryKey(id);
		if(park == null) return;
		final Long parkId = park.getId();
		
		park.setState((byte) 1); //将状态改为使用中
		park.setUpdated(new Date());                                   
		
		parkMapper.updateByPark(park); //更新park
		
		//更新索引库,使park可以被用户搜索
		jmsTemplate.send(parkUpdateTopicDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(parkId+"");
				return textMessage;
			}
		});
		//TODO 更新缓存
	}

	/**
	 * 获取当前停车场中正在被使用的车位数
	 */
	@Override
	public int getUsingCount(Long parkId) {
		Park park = parkMapper.selectByPrimaryKey(parkId);
		if(park == null) return -1;
		
		return park.getUsing_count();
	}

	
	/**
	 * 更改停车场状态
	 */
	@Override
	public void changeParkState(Long id, Byte state) {
		Park park = parkMapper.selectByPrimaryKey(id);
		if (park == null) throw new RuntimeException("未找到此park"+id);
		final Long parkId = park.getId();
		park.setState(state);
		park.setCreated(new Date());
		parkMapper.updateByPark(park);
		
		//更新索引库
		jmsTemplate.send(parkUpdateTopicDestination, new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage(parkId+"");
				return message;
			}
		});
		
	}
	

	
	
	
	
	
	
	
	
	
	
}
