package cn.antido.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.antido.admin.mapper.ParkAdminMapper;
import cn.antido.admin.mapper.ParkMapper;
import cn.antido.admin.mapper.StreetMapper;
import cn.antido.admin.pojo.ParkAdmin;
import cn.antido.admin.pojo.Street;
import cn.antido.admin.pojo.dto.ParkAdminByStreetDTO;
import cn.antido.admin.pojo.filter.DaoFilter;
import cn.antido.admin.service.ParkAdminService;

/**
 * @Description 管理员服务实现类
 * @author Antido
 * @date 2018年1月4日 下午8:51:17
 */
@Service
@Transactional
public class ParkAdminServiceImpl implements ParkAdminService{
	
	@Autowired
	private StreetMapper streetMapper;
	@Autowired
	private ParkAdminMapper parkAdminMapper;
	@Autowired 
	private ParkMapper parkMapper; 
	
	/**
	 * 根据streetId封装ParkAdminByStreetDTO
	 */
	@Override
	@Transactional(readOnly=true)
	public ParkAdminByStreetDTO getAdminDTOByStreet(Integer streetId) {
		Street street = streetMapper.selectByPrimaryKey(streetId);
		if(street == null) {
			return null;
		}
		
		ParkAdminByStreetDTO parkAdminByStreetDTO = new ParkAdminByStreetDTO();
		
		//根据streetId 获取默认负责人
		ParkAdmin parkAdmin = street.getParkAdmin();
		if(parkAdmin != null) {
			Integer defaultAdminId = parkAdmin.getId();
			ParkAdmin defaultAdmin = parkAdminMapper.selectSimpleByPrimaryKey(defaultAdminId);
			parkAdminByStreetDTO.setDefaultAdmin(defaultAdmin);
		}
		
		//根据street查找所有park 对park_admin_id做去重得到所有所有负责人id列表
		List<Integer> adminIdList = parkMapper.selectAdminByStreetId(street.getId());
		if(adminIdList == null || adminIdList.size() == 0) {
			parkAdminByStreetDTO.setAdminList(null);
		} else {
			//根据id列表获取负责人列表
			DaoFilter filter = new DaoFilter();
			filter.addAndCriteria("id in", adminIdList);
			List<ParkAdmin> adminList = parkAdminMapper.selectSimpleByDaoFilter(filter);
			parkAdminByStreetDTO.setAdminList(adminList);
		}
		
		return parkAdminByStreetDTO;
	}

}
