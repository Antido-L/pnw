package cn.antido.park.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.antido.admin.pojo.Park;
import cn.antido.admin.pojo.Space;
import cn.antido.admin.pojo.dto.ParkMapDTO;
import cn.antido.admin.pojo.dto.SpaceInfoDTO;
import cn.antido.admin.pojo.dto.SpaceModelDTO;
import cn.antido.admin.service.ParkService;
import cn.antido.admin.service.SpaceService;
import cn.antido.common.CallBackResult;
import cn.antido.connection.pojo.OrderInfoDTO;
import cn.antido.connection.service.CommandService;

/**
 * @Description park.antido.cn页面控制器<br>
 * @author Antido
 * @date 2018年5月23日 上午10:51:08
 */
@Controller
public class ParkController {
	
	@Autowired
	private ParkService parkService;
	
	@Autowired
	private SpaceService spaceService;
	
	@Autowired
	private CommandService commandService;
	
	
	/**
	 * 处理404
	 */
	@RequestMapping(value="*")
	public String showNotFound() {
		return "notFound";
	}
	
	/**
	 * 展示停车场模拟图<br>
	 * 数据异常时返回notFound页面<br>
	 * 将park数据放入request域中
	 * @return
	 */
	@RequestMapping(value="/")
	public String showPage(Model model, Long parkId) {
		if(parkId == null) 
			return "notFound";
		
		Park park = parkService.getCompleteParkById(parkId);
		if(park == null) 
			return "notFound";
		//放入request域中
		model.addAttribute("park",park);
		
		return "index";
	}
	
	/**
	 * 获取停车场模拟图数据
	 */
	@RequestMapping(value="/getParkMapByParkId")
	@ResponseBody
	public ParkMapDTO getParkMap(Long parkId) {
		if(parkId == null) return null;
		ParkMapDTO parkMapDTO = parkService.getParkMapDTOByParkId(parkId);
		
		return parkMapDTO;
	}
	
	/**
	 * 根据parkId获取所有停车位<br>
	 * 返回JSON格式数据
	 */
	@RequestMapping(value="/allSpaceByParkId")
	@ResponseBody
	public List<Space> getAllSpaceByParkId(Long parkId) {
		if(parkId == null) {
			return null;
		}
		List<Space> list = spaceService.getSpaceListByParkID(parkId);
		return list;
	}
	
	/**
	 * 根据spaceId获取当前车位的详细信息<br>
	 * 如果该车位已被人使用,返回结果中将包含User对象<br>
	 * 如果是当前用户调用 直接从前端UserUtils中获取车位信息<br>
	 * @param spaceId
	 */
	@RequestMapping(value="/modelJsonBySpaceId")
	@ResponseBody
	public SpaceInfoDTO modelJsonBySpaceId(Long spaceId) {
		if(spaceId == null) {
			return null;
		}
		//SpaceModelDTO spaceModelDTO = spaceService.getSpaceModelDTO(spaceId);
		SpaceInfoDTO spaceInfoDTO = spaceService.getSpaceInfoDTO(spaceId);
		return spaceInfoDTO;
	}
	
	
	/**
	 * 预约确认<br>
	 * 当用户已经到达车位 需要确认预约打开目标车位锁<br>
	 * 进入停车确认状态<br>
	 * 在调用此方法前必须保证用户已经登录,使用拦截器确保token未过期<br>
	 */
	@RequestMapping(value="/reserveConfirm")
	@ResponseBody
	public CallBackResult reserveConfirm(String token,String opCode,Integer leavingTime) {
		
		CallBackResult res = commandService.reserveConfirm(token,opCode,leavingTime);
		return res;
	}
	
	/**
	 * 预约取消<br>
	 * 取消用户的预约完成订单,将车位释放变为可用状态<br>
	 * 在调用此方法前必须保证用户已经登录,使用拦截器确保token未过期<br>
	 */
	@RequestMapping(value="/reserveCancel")
	@ResponseBody
	public CallBackResult reserveCancel(String token,String opCode) {
		
		CallBackResult res = commandService.reserveCancel(token,opCode);
		return res;
	}
	
	/**
	 * 预约一个指定的车位<br>
	 * 在调用此方法前必须保证用户已经登录,使用拦截器确保token未过期<br>
	 */
	@RequestMapping(value="/reserveSpace")
	@ResponseBody
	public CallBackResult reserveSpace(OrderInfoDTO info) {
		if(info.hasEmptyItem()) {
			return CallBackResult.error("数据不全");
		}
		
		CallBackResult res = commandService.reserveSpace(info);
		return res;
	}
	
	/**
	 * 停靠一个指定的车位<br>
	 * 在调用此方法前必须保证用户已经登录,使用拦截器确保token未过期<br>
	 * 开启车位锁
	 * 在redis中存放当前停靠信息 设置过期时间
	 * 启动定时线程 使用定时线程监听停靠时间 当时间过期后 执行过期操作
	 * 在规定时间内完成停靠后 取消对此事件的监听 生成订单完成一次停车操作
	 */
	@RequestMapping(value="/useSpace")
	@ResponseBody
	public CallBackResult useSpace(OrderInfoDTO info) {
		if(info.hasEmptyItem()) {
			return CallBackResult.error("数据不全");
		}
		
		CallBackResult res = commandService.useSpace(info);
		return res;
	}
	
	/**
	 * 离开一个指定的车位<br>
	 * 在调用此方法前必须保证用户已经登录,使用拦截器确保token未过期<br>
	 */
	@RequestMapping(value="/leaveSpace")
	@ResponseBody
	public CallBackResult leaveSpace(OrderInfoDTO info) {
		if(info.hasEmptyItem()) {
			return CallBackResult.error("数据不全");
		}
		
		CallBackResult res = commandService.leaveSpace(info);
		return res;
	}
	
	/**
	 * 用户的停车确认请求<br>
	 * 当用户使用车位后并确认停靠完毕时发送该请求<br>
	 * 当车位检测到车辆正确停靠后返回OK<br>
	 * 当车位未检测到停靠时返回异常EXCEPTION
	 */
	@RequestMapping(value="/parkConfirm")
	@ResponseBody
	public CallBackResult parkConfirm(String token) {
		if(token == null || token.equals("")) {
			return CallBackResult.error("token为空");
		}
		
		CallBackResult res = commandService.parkConfirm(token);
		return res;
	}
	
}
