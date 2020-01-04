package cn.antido.admin.pojo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.antido.admin.pojo.District;
import cn.antido.admin.pojo.Park;
import cn.antido.admin.pojo.Street;

/**
 * @Description 停车位下拉选数据回显传输对象<br>
 * parkEcho:停车场回显数据列表
 * streetEcho:街道回显数据对象
 * districtEcho:区县回显数据对象列表
 * cityEcho:市辖区回显数据对象 列表  TODO:直辖市可能会有BUG
 * provinceEcho: 省/直辖市回显数据对象列表
 * selectedEcho:所有被选项id(key:被选项名(???Selected),value:被选项id)
 * @author Antido
 * @date 2017年12月24日 下午4:48:55
 */
public class SpaceSelectEchoDTO implements Serializable {
	
	private List<Park> parkEcho; 
	private List<Street> streetEcho; 
	private List<District> districtEcho;
	private List<District> cityEcho;
	private List<District> provinceEcho;
	
	/**
	 * HashMapS
	 */
	private Map<String,Number> selectedEcho;

	public List<Park> getParkEcho() {
		return parkEcho;
	}

	public void setParkEcho(List<Park> parkEcho) {
		this.parkEcho = parkEcho;
	}

	public List<Street> getStreetEcho() {
		return streetEcho;
	}

	public void setStreetEcho(List<Street> streetEcho) {
		this.streetEcho = streetEcho;
	}

	public List<District> getDistrictEcho() {
		return districtEcho;
	}

	public void setDistrictEcho(List<District> districtEcho) {
		this.districtEcho = districtEcho;
	}

	public List<District> getCityEcho() {
		return cityEcho;
	}

	public void setCityEcho(List<District> cityEcho) {
		this.cityEcho = cityEcho;
	}

	public List<District> getProvinceEcho() {
		return provinceEcho;
	}

	public void setProvinceEcho(List<District> provinceEcho) {
		this.provinceEcho = provinceEcho;
	}

	public Map<String, Number> getSelectedEcho() {
		return selectedEcho;
	}

	public void setSelectedEcho(Map<String, Number> selectedEcho) {
		this.selectedEcho = selectedEcho;
	}

	
	
}
