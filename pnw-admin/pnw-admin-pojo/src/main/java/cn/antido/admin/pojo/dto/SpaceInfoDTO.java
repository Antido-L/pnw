package cn.antido.admin.pojo.dto;

import java.io.Serializable;

import cn.antido.admin.pojo.Space;
import cn.antido.admin.pojo.User;

/**
 * @Description 客户车位信息查询数据传输对象<br>
 * 根据使用情况封装<br>
 * User对象中的数据需根据用户选择选择性封装
 * @author Antido
 * @date 2018年6月22日 上午10:30:27
 */
public class SpaceInfoDTO implements Serializable {
	private Space space;
	private User user;
	
	private String leavingTimeStr;

	/**
	 * @return the space
	 */
	public Space getSpace() {
		return space;
	}

	/**
	 * @param space the space to set
	 */
	public void setSpace(Space space) {
		this.space = space;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the leavingTimeStr
	 */
	public String getLeavingTimeStr() {
		return leavingTimeStr;
	}

	/**
	 * @param leavingTimeStr the leavingTimeStr to set
	 */
	public void setLeavingTimeStr(String leavingTimeStr) {
		this.leavingTimeStr = leavingTimeStr;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SpaceInfoDTO [space=" + space + ", user=" + user + ", leavingTimeStr=" + leavingTimeStr + "]";
	}
	
	
}
