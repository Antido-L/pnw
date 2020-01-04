package cn.antido.sso.test;

import cn.antido.admin.pojo.User;
import cn.antido.common.utils.JsonUtils;

public class JsonTest {
	public static void main(String[] args) {
		User user = new User();
		user.setName("antido");
		user.setPhone("138999");
		System.out.println(user);
		String json = JsonUtils.obj2Json(user);
		System.out.println(json);
		
		User user2 = JsonUtils.json2Obj(json, User.class);
		
		System.out.println(user2);
	}
}
