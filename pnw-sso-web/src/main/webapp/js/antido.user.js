var UserUtils = {
	param:{
		url:"http://localhost:8089",
		cookieName:"PNW_SESSION"
	},
	
	/**
	 * 当前登录的User对象
	 */
	user:null,
	
	/**
	 * 获取当前用户token
	 */
	getToken:function() {
		var token = $.cookie(this.param.cookieName);
		if(token == null || token == "") {
			return null;
		}
		return token;
	},
	
	/**
	 * 根据cookie从后台异步获取用户信息
	 */
	getUserByCookie:function() {
		var token = this.getToken();
		if(token == null) {
			return ;
		}
		$.ajax({
			url:this.param.url + "/user/token",
  			data:{"token":token},
  			dataType:"jsonp",
  			jsonp:"callback",
  			type:"POST",
  			success:function(data){
  				console.log(data);
  				if(data == null) {
  					return;
  				}
  				if(data.code == 0) {
  					//将User对象存入本地
  					UserUtils.user = data.data;
  					//$("#menu-user-name").html(data.data.nice_name);
  					$("#token-userId").val(data.data.id);
  					//更新页面用户显示
  					if(data.data.car == null && window.location.pathname.indexOf("/user/setCar") != 0) { //注册后未绑定车辆
  						window.location.href = "http://localhost:8089/user/setCar";
  					}
  					UserUtils.setUserInfo(data.data);
  					UserUtils.checkState(data.data.state);
  				} else {
  					//未获取到匹配的User删除本地cookie
  					//服务端session过期删除客户端session
  					$.cookie("PNW_SESSION", "", {expires: -1,path:'/'});
  				}
  			},
  			
		});
		//console.log(this);
	},
	
	/**
	 * 当用户登录成功后设置页头用户信息
	 */
	setUserInfo:function(user) {
		$("#header-login").addClass("hidden");
		$("#header-userInfo").removeClass("hidden");

		
		$("#header-userInfo-name").text(user.nick_name);
		$("#header-userInfo-car").attr("href","#");
		$("#header-userInfo-order").attr("href","#");
		$("#header-userInfo-money").attr("href","#");
		$("#header-userInfo-remain").text(parseInt(user.wallet / 100));
		
		//用户状态
		if(user.space == null) {
			$("#header-userInfo-state").text("未停靠");
			$("#header-space").removeClass("hidden");
		}
		else {
			$("#header-mySpace").removeClass("hidden");
			$("#header-mySpace > a").attr("href","http://localhost:8086?parkId=" + user.space.park.id);
			
			if(user.state == 1) $("#header-userInfo-state").text("等待停车确认");
			else if(user.state == 2) $("#header-userInfo-state").text("停车确认超时");
			else if(user.state == 3) $("#header-userInfo-state").text("车辆正在离开");
			else if(user.state == 4) $("#header-userInfo-state").text("车辆离开超时");
			else if(user.state == 5) $("#header-userInfo-state").text("禁止使用");
			else if(user.state == 6) $("#header-userInfo-state").text("已有预约");
		}
		
	},
	
	/**
	 * 根据用户状态码做出响应处理 
	 */
	checkState:function(state) {
		switch(state) {
			case 1:{ //停车等待
				//弹出提示框要求用户确认停车
				var time = 8 - (new Date() - UserUtils.user.space.parked_time) / 1000 / 60;
				
				$("#model-user-checkWait-time").text(parseInt(time));
				$("#model-user-checkWait-name").text(UserUtils.user.nick_name);
				$("#model-user-checkWait-parkName").text(UserUtils.user.space.park.name);
				$("#model-user-checkWait-parkHref").attr("href","http://localhost:8086/?parkId="+UserUtils.user.space.park.id);
				$("#model-user-checkWait-spaceCode").text(UserUtils.user.space.code);
				$('#model-user-checkWait').modal({
					keyboard: false
				});
				break;
			}
			case 2:{ //等待停车确认超时
				//弹出提示框要求用户驶离车位
				$("#alert-model-content-mid").html("<p style='color:#d43f3a'>本次停车已经超时，请空出车位。</p><p>空出车位后该订单会自动取消，不影响您下次使用。<p/>");
				$('#alert-model-mid').modal({
					keyboard: false
				});
				break;
			}
			case 3:{ //等待车辆离开
				//弹出提示框
				$("#alert-model-content-mid").html("<p style='color:#d43f3a'>离开请求已经确认。</p><p>请尽快驶离车位，车辆离开后会自动完成订单结算。<p/>");
				$('#alert-model-mid').modal({
					keyboard: false
				});
				break;
			}
			case 4:{ //车辆离开超时
				break;
			}
			case 5:{ //禁止使用
				//弹出提示框
				$("#alert-model-content-mid").html("<p style='color:#d43f3a'>您的使用权限已被冻结。</p><p>请查看详情或者咨询客服。<p/>");
				$('#alert-model-mid').modal({
					keyboard: false
				});
				break;
			}
			
			case 6:{ //预约中
				var time = 30 - (new Date() - this.user.space.reserve_time) / 1000 / 60;
				//弹出提示框
				$("#alert-model-content-mid").html("<p>您在<a style='color:#3385ff;text-decoration: underline;' href='http://localhost:8086/?parkId="+this.user.space.park.id+"'>"+this.user.space.park.name+"</a>"
						+"已预约"+ this.user.space.code+"号车位。</p>"
						+"<p>预约时间剩余：<code>"+ parseInt(time) +"分钟</code>，请尽快前往目标车位停车。<p/>");
				$('#alert-model-mid').modal({
					keyboard: false
				});
				break;
				//this.user.space.reserve_time
			}
			default:break;
		}
	},
	
	/**
	 * 检测用户cookie,如果cookie已失效跳转到登录页面
	 */
	checkUser:function() {
		var cookie = $.cookie(this.param.cookieName);
		if(cookie != null) return ;
		
		//当前页面为登录成功后的回调页面
		var url = window.location.href;
		//跳转至登录页面
		window.location.href = "http://localhost:8089/login?redirect=" +url;
		
	},
	
	/**
  	 * 登出
  	 */
  	logout:function(){
		//移除本地cookie
  		$.cookie("PNW_SESSION", "", {expires: -1,path:'/'});
  		//刷新浏览器
  		window.location.reload();
  	}
	
}