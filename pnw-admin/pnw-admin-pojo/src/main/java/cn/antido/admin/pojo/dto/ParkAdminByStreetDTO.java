package cn.antido.admin.pojo.dto;

import java.io.Serializable;
import java.util.List;

import cn.antido.admin.pojo.ParkAdmin;

/**
 * @Description 与street相关的管理员数据传输对象<br>
 * 包含当前street默认负责人和当前street中的所有负责人列表<br>
 * 负责人数据中只包含非敏感的常规字段,用于前端显示
 * @author Antido
 * @date 2018年1月4日 下午6:46:11
 */
public class ParkAdminByStreetDTO implements Serializable {
	
	private ParkAdmin defaultAdmin;
	private List<ParkAdmin> adminList;
	
	public ParkAdmin getDefaultAdmin() {
		return defaultAdmin;
	}
	public void setDefaultAdmin(ParkAdmin defaultAdmin) {
		this.defaultAdmin = defaultAdmin;
	}
	public List<ParkAdmin> getAdminList() {
		return adminList;
	}
	public void setAdminList(List<ParkAdmin> adminList) {
		this.adminList = adminList;
	}
	
	@Override
	public String toString() {
		return "ParkAdminByStreetDTO [defaultAdmin=" + defaultAdmin + ", adminList=" + adminList + "]";
	}
	
}
