package cn.antido.admin.service;

import cn.antido.admin.pojo.dto.ParkAdminByStreetDTO;
/**
 * @Description 管理员服务接口
 * @author Antido
 * @date 2018年1月4日 下午7:08:08
 */
public interface ParkAdminService {
	
	/**
	 * 根据 streetId获取封装完成后的ParkAdminByStreetDTO
	 */
	ParkAdminByStreetDTO getAdminDTOByStreet(Integer streetId);
	
}
