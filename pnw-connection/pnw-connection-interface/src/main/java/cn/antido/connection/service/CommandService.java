package cn.antido.connection.service;

import cn.antido.common.CallBackResult;
import cn.antido.connection.pojo.OrderInfoDTO;

/**
 * @Description 指令服务接口<br>
 * 用于控制车位状态
 * @author Antido
 * @date 2018年5月25日 下午2:35:21
 */
public interface CommandService {
	
	/**
	 * 使用一个车位
	 * @param info
	 * @return
	 */
	CallBackResult useSpace(OrderInfoDTO info);
	
	/**
	 * 预约一个车位
	 * @param info
	 * @return
	 */
	CallBackResult reserveSpace(OrderInfoDTO info);

	/**
	 * 释放一个车位
	 * @param info
	 * @return
	 */
	CallBackResult leaveSpace(OrderInfoDTO info);
	
	/**
	 * 确认停车
	 * @param info
	 * @return
	 */
	CallBackResult parkConfirm(String userToken);
	
	/**
	 * 取消当前预约
	 * @param info
	 * @return
	 */
	CallBackResult reserveCancel(String userToken, String opCode);
	
	/**
	 * 取消当前预约
	 * @param info
	 * @return
	 */
	CallBackResult reserveConfirm(String userToken,  String opCode, Integer leavingTime);
}
