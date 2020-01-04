package cn.antido.sso.service;

import java.util.List;

import cn.antido.admin.pojo.CarBrand;
import cn.antido.admin.pojo.CarModel;
import cn.antido.admin.pojo.User;
import cn.antido.admin.pojo.dto.CarAddDTO;
import cn.antido.common.CallBackResult;

/**
 * @Description 用户注册服务接口
 * @author Antido
 * @date 2018年8月10日 下午12:04:20
 */
public interface RegisterService {

	/**
	 * 判断当前用户名是否已经被使用
	 * @param name
	 * @return Boolean
	 */
	Boolean isNameExist(String name);
	
	/**
	 * 判断当前手机号是否已经被使用
	 * @param name
	 * @return Boolean
	 */
	Boolean isPhoneExist(String phone);

	/**
	 * 向目标手机号发送注册验证码<br>
	 * 在发送过程中验证上次发送的时间,当在一分钟内已经向目标发送过验证码返回exception(重发倒计时)<br>
	 * 当发送成功后返回OK()并在redis中保存当前手机注册码设置过期时间5分钟<br>
	 * @param phone
	 * @return CallBackResult
	 */
	CallBackResult sendRegisMsg(String phone);

	/**
	 * 新增一个user<br>
	 * 验证手机验证码的正确性<br>
	 * 当用户注册成功后,生成对应的session<br>
	 * @param user 表单封装的user对象
	 * @param code 手机验证码
	 * @return
	 */
	CallBackResult addUser(User user, String code);

	/**
	 * 获取所有品牌列表
	 * @return
	 */
	List<CarBrand> getBrands();

	/**
	 * 根据品牌获取该品牌下的所有车型
	 * @param brandId
	 * @return
	 */
	List<CarModel> getModelByBrand(Integer brandId);

	/**
	 * 绑定用户车辆等信息
	 * @param dto
	 * @return
	 */
	CallBackResult addCar(CarAddDTO dto);

}
