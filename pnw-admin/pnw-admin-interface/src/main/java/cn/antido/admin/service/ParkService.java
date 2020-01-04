package cn.antido.admin.service;

import java.util.HashMap;
import java.util.List;

import cn.antido.admin.pojo.Park;
import cn.antido.admin.pojo.dto.ParkAddFormDTO;
import cn.antido.admin.pojo.dto.ParkInfoEditEchoDTO;
import cn.antido.admin.pojo.dto.ParkMapDTO;
import cn.antido.admin.pojo.dto.ParkModelDTO;
import cn.antido.common.pojo.PageBean;
/**
 * @Description 对外提供ParkService
 * @author Antido 
 * @date 2017年12月14日  下午7:06:24
 */
public interface ParkService {
	
	/**
	 * 根据主键获取完整Park对象
	 */
	Park getParkById(Long id);
	
	/**
	 * 新增一个停车场<BR>
	 * 返回添加情况<BR>
	 * state:(error,exception,success)
	 * message:(反馈信息)
	 * 失败则返回其他
	 */
	HashMap<String,String> addParkByDTO(ParkAddFormDTO parkAddFormDTO);
	
	/**
	 * 获取停车场分页对象 可能包含分页数据和区域限制
	 * 包含所有字段<br>
	 * 使用PageHelper分页过滤器
	 */
	PageBean<Park> getPageBean(Integer PAGE_SIZE, Integer currentPage, Integer regionId);
	
	/**
	 * 根据街道id获取当前停车场列表<br>
	 * 数据中只包含name,id
	 */
	List<Park> getParkByStreetId(Integer street_id);
	
	/**
	 * 根据parkId封装ParkModelDTO
	 */
	ParkModelDTO getParkModelDTOByParkId(Long parkId);
	
	/**
	 * 获取开发中的停车场分页对象<BR>
	 * 可能含有的参数:regionId
	 */
	PageBean<Park> getDevPageBean(Integer PAGE_SIZE, Integer currentPage, Integer regionId);

	/**
	 * 根据parkId获取停车场编辑页面数据回显对象
	 */
	ParkInfoEditEchoDTO getInfoEditEchoDTO(Long parkId);
	
	/**
	 * 根据DTO中的信息修改一个停车场的基本信息<br>
	 * 返回修改情况
	 */
	HashMap<String,String> updateParkByDTO(ParkAddFormDTO parkAddFormDTO);
	
	/**
	 * 获取停车场模拟图数据传输对象
	 */
	ParkMapDTO getParkMapDTOByParkId(Long parkId);
	
	/**
	 * 根据ID删除Park,同时删除所有与之关联的Space,ParkModel,清空相关Node
	 */
	void deletePark(Long parkId);
	
	/**
	 * 根据ID发布Park,更改park_state,更新solr索引,
	 */
	void publishPark(Long parkId);
	
	/**
	 * 根据ID查询停车场内正在被使用的车位数
	 */
	int getUsingCount(Long parkId);
	
	/**
	 * 下线停车场,更改停车场状态,更新索引库
	 */
	void changeParkState(Long parkId, Byte state);
	
	/**
	 * 获取完整的park对象<br>
	 * 此方法被在前台park.antido.cn加载时调用<br>
	 * 补全park中包含的其他对象<br>
	 * 当park不存在是或者park处于非正常使用状态时返回null<br>
	 * 使用redis做二级缓存
	 */
	Park getCompleteParkById(Long id);
	
}
