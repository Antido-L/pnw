package cn.antido.search.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.antido.admin.pojo.ConnCallBack;
import cn.antido.admin.pojo.dto.SpaceModelDTO;
import cn.antido.admin.service.SpaceService;


/**
 * @Description 车位数据控制器
 * @author Antido
 * @date 2018年4月2日 下午2:36:27
 */
@Controller
public class SpaceController {
	
	@Autowired
	private SpaceService spaceService;
	
	/**
	 * 根据当前车位id异步加载车位信息模态框数据<br>
	 * 返回封装好的json格式的DTO对象
	 */
	@RequestMapping(value="/space/modelJsonBySpaceId")
	@ResponseBody
	public SpaceModelDTO getModelJsonBySpaceId(Long spaceId) {
		SpaceModelDTO spaceModelDTO = spaceService.getSpaceModelDTO(spaceId);
		return spaceModelDTO;
	}
	
	@RequestMapping(value="/space/lockOn")
	@ResponseBody
	public ConnCallBack lockOnSpace(Long spaceId, String nodeId, String parent_id) {
		ConnCallBack connCallBack = spaceService.lockOnSpace(spaceId, nodeId, parent_id);
		return connCallBack;
	}
	
	@RequestMapping(value="/space/lockOff")
	@ResponseBody
	public ConnCallBack lockOffSpace(Long spaceId, String nodeId, String parent_id) {
		ConnCallBack connCallBack = spaceService.lockOffSpace(spaceId, nodeId, parent_id);
		return connCallBack;
	}
	
}
