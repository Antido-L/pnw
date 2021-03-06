<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 <!-- FlatFy Theme - Andrea Galanti /-->
<!doctype html>
<!--[if lt IE 7]> <html class="no-js ie6 oldie" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js ie7 oldie" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js ie8 oldie" lang="en"> <![endif]-->
<!--[if IE 9]>    <html class="no-js ie9" lang="en"> <![endif]-->
<!--[if gt IE 9]><!--> <html> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0">
    <meta name="description" content="Flatfy Free Flat and Responsive HTML5 Template ">
    <meta name="author" content="">

    <title>PNW-登陆</title>
	<link rel="shortcut icon" href="http://static.antido.cn/img/favicon.png" type="image/png">
	
    <%-- <link href="${pageContext.request.contextPath }/css/bootstrap.min.css" rel="stylesheet"> --%>
 	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
 
    <!-- font-awesome -->
    <%-- <link href="${pageContext.request.contextPath }/font-awesome/css/font-awesome.min.css" rel="stylesheet"> --%>
	<link href="https://cdn.staticfile.org/font-awesome/4.7.0/css/font-awesome.css" rel="stylesheet">
	
    <!-- Custom CSS-->
    <%-- <link href="${pageContext.request.contextPath }/css/general.css" rel="stylesheet"> --%>
    <link href="http://static.antido.cn/css/general.css" rel="stylesheet">
	
	 <!-- Owl-Carousel -->
    <link href="http://static.antido.cn/css/custom.css" rel="stylesheet">
	<link href="https://cdn.staticfile.org/owl-carousel/1.32/owl.carousel.min.css" rel="stylesheet">
    <link href="https://cdn.staticfile.org/owl-carousel/1.32/owl.theme.min.css" rel="stylesheet">
	<link href="http://static.antido.cn/css/style.css" rel="stylesheet">
	<link href="http://static.antido.cn/css/animate.css" rel="stylesheet">
	<!-- Magnific Popup core CSS file -->
    <link href="http://static.antido.cn/css/magnific-popup.css" rel="stylesheet"> 
	<!-- <script src="https://cdn.staticfile.org/modernizr/2010.07.06dev/modernizr.min.js"></script> -->
	
	<style>
		.login-submit-button{
			height: 70px;
			padding-top: 15px;
			padding-bottom: 15px;
		}
		
		.login-method-change:hover{
			background-color: #d9534f !important;
			cursor: pointer;
		}
		
		.content-style-form-4 input[type="password"] {
			border: 2px solid #f0f0f0;
			background-color: #f0f0f0;
		}
		
		.content-style-form-4 input[type="text"] {
			border: 2px solid #f0f0f0;
			background-color: #f0f0f0;
		}
		
		.content-style-form input[type="text"]:focus,
		.content-style-form input[type="password"]:focus {
			border-color: #1abc9c;
			color: #e75854;
		}
	
	</style>
</head>

<body id="home">

	<!-- Preloader -->
	<div id="preloader">
		<div id="status"></div>
	</div>
	
	<div  style="padding-top: 15px;padding-bottom: 40px" class="content-section-c ">
		<div class="container">
			<div class="row">
			<div class="col-md-6 col-md-offset-3 text-center wrap_title white">
				<h2 style="margin-top: 20px">Welcome to PNW</h2>
				<!-- <p style="margin-bottom: 0px;margin-top: 5px" class="lead" style="margin-top:0">Welcome to PNW!</p> -->
			 </div>
			<div class="mockup-content">
				<div class="morph-button wow pulse morph-button-inflow morph-button-inflow-1 active open" style=" height: 800px">
					<button type="button "><span>Subscribe to our Newsletter</span></button>
					<div class="morph-content">
					
						<div id="passwordLogin" class="">
							<div class="content-style-form content-style-form-4 ">
								<h2 class="morph-clone login-method-change" onclick="LoginUtils.showPhoneLogin()">点击使用手机验证码登录</h2>
								<form style="padding-bottom: 10px">
								<p>
									<label id="input-label-username">请输入用户名</label>
									<input style="height: 60px" type="text" name="username"/>
								</p>
								<p><label id="input-label-password">请输入密码</label>
									<input style="height: 60px"  type="password" name="password"/>
								</p>
								<p>
									<label id="input-label-imgCode">请输入图形验证码</label>
									<input style="width:72%; height: 60px" type="text" name="imgCode"/>
									
									<img id="codeImg" style="width:27%; height: 60px; vertical-align: bottom;" src="" onclick="LoginUtils.setCodeImg()"/>
									<!-- <div style="display: inline-block;width:27%; height: 60px;">
									</div> -->
								</p>
								<p>
									<button type="button" id="login-password-submit-button" class="login-submit-button" style="padding-top: 15px;padding-bottom: 15px;" 
										onclick="LoginUtils.passwordLoginSubmit()" >登&nbsp;&nbsp;&nbsp;录</button>
								</p>
								<p style="text-align: center; margin-top: 5px" >
									<a style="color:#428bca" href="javaScript:void(0)">忘记密码</a>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<a style="color:#428bca" href="http://localhost:8089/user/register">免费注册</a>
								</p>										
								</form>
							</div>
						</div>
						
						<div id="phoneLogin" class="hidden">
							<div class="content-style-form content-style-form-4 ">
								<h2 onclick="LoginUtils.showPasswordLogin()" class="morph-clone login-method-change">点击使用账号密码登录</h2>
								<form style="padding-bottom: 10px">
								<p>
									<label id="input-label-phone">请输入手机号</label>
									<input style="height: 60px" type="text" name="phone"/>
								</p>
								<p>
									<label id="input-label-msgCode">请输入短信验证码</label>
									<input style="width:72%; height: 60px" type="text" name="validCode"/>
									<button id="msg-button" style="width:27%; height: 60px; vertical-align: bottom; display: inline;margin: 0;padding: 0;font-size: 16px" 
										onclick="LoginUtils.getLoginMsg()">获取短信验证码</button>
								</p>
								<p><button id="login-phone-submit-button" class="login-submit-button" style="padding-top: 15px;padding-bottom: 15px;" 
									onclick="LoginUtils.phoneLoginSubmit()">登&nbsp;&nbsp;&nbsp;录</button></p>
								<p style="text-align: center; margin-top: 5px" >
									<a style="color:#428bca" href="javaScript:void(0)">忘记密码</a>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<a style="color:#428bca" href="http://localhost:8089/user/register">免费注册</a>
								</p>
								</form>
							</div>
						</div>
						
					</div>
				</div>
			</div>
			</div>	
		</div>
	</div>	
	
	<input type="hidden" id="redirectUrl" value="${redirect}"/>
	
    <footer>
      <div class="container">
        <div class="row">
          <div class="col-md-7">
            <h3 style="margin-bottom: 10px" class="footer-title">PNW自助停车与查询系统</h3>
            <p style="text-indent: 2em;">底层基于Zigbee-CC2530芯片作为最小单元实现车辆停靠情况的检测与车位锁的控制。节点体积小，续航能力强，单节点间通信距离可达百米，多节点通讯可桥接，是目前物联网技术的主流使用手段之一。</p>
           	<p style="text-indent: 2em;">通过对Z-stack协议栈应用层的开发完成停车场内节点间的自组网与无线通讯功能，同时通过在停车场内放置的可接入互联网的嵌入式设备完成与服务器端的通讯功能。可实时监控停车内车位使用状态，与对任意车位的精准控制。 </p>
            <p style="text-indent: 2em;">停车场与车位节点接入无需本地配置，后台注册后即可快速上线使用，扩展性强，易于更换与维修。</p>
            <p>
            Template from 
            	<a href="http://www.cssmoban.com/">模板之家</a> and <a href="http://www.bootcss.com/">Bootstarp</a>
            	<a href="http://www.cssmoban.com/">Flatfy Theme</a> by 
            	<a href="http://www.cssmoban.com/">Andrea Galanti</a> is licensed to the public under the 
            	<a href="https://creativecommons.org/licenses/by-nc/3.0/it/deed.it">Creative Commons Attribution 3.0 License - NOT COMMERCIAL.</a>
            </p>
			<!-- LICENSE -->
			<p><img style="height: 17px;margin-bottom: 6px;" src="http://static.antido.cn/img/icp.png"/><a href="http://www.miitbeian.gov.cn/">非经营信互联网信息服务备案：辽ICP备18013979号</a></p>
          </div> <!-- /col-xs-7 -->
          <div class="col-md-5">
            <div class="footer-banner">
              <h3 class="footer-title">关于我们</h3>
              <ul>
                <li>沈阳工业大学-检测技术与自动装置-物联网实验室</li>
                <li>作者：Antido、Mh</li>
                <li>微信/QQ：1063576810</li>
                <li>邮箱：1063576810@qq.com</li>
                <li>此网站只作为测试与学习使用，无任何实际资金交易</li>
                <li>期待您的反馈与交流</li>
                <li>友情链接：<a href="http://www.sut.edu.cn/" target="_blank" style="">沈阳工业大学</a></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </footer>
    
    <!-- ALERT-MODEL-SM -->
	<div id="alert-model"  class="modal fade bs-example-modal-sm alertModal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
	  <div class="modal-dialog modal-sm" role="document">
	    <div class="modal-content">
	        <h4 style="text-align: center;margin-bottom: 25px;margin-top: 25px" id="alert-model-content">你好啊同学!</h4>
	      <!-- <h3 id="alert-model-content">你好啊同学!</h3> -->
	    </div>
	  </div>
	</div>
	
    <!-- JavaScript -->
    <script src="https://cdn.staticfile.org/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://static.antido.cn/js/jquery-migrate-1.2.1.min.js"></script>
    <script src="https://cdn.staticfile.org/jquery-cookie/1.1/jquery.cookie.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	<script src="https://cdn.staticfile.org/owl-carousel/1.32/owl.carousel.js"></script>
	<%-- <script src="${pageContext.request.contextPath }/js/uiMorphingButton_inflow.js"></script> --%>
	<script src="http://static.antido.cn/js/script.js"></script>
	<!-- StikyMenu -->
	<%-- <script src="${pageContext.request.contextPath }/js/stickUp.min.js"></script> --%>
	<!-- <script src="http://static.antido.cn/js/jquery.corner.js"></script> --> 
	<script src="http://static.antido.cn/js/wow.min.js"></script>
	
	<script src="http://static.antido.cn/js/classie.js"></script>
	<%-- <script src="${pageContext.request.contextPath }/js/uiMorphingButton_inflow.js"></script> --%>
	<!-- Magnific Popup core JS file -->
	<script src="https://cdn.staticfile.org/magnific-popup.js/1.1.0/jquery.magnific-popup.min.js"></script>
	<script src="http://static.antido.cn/js/antido/antido.input.js"></script>
	<script src="http://static.antido.cn/js/antido/antido.user.js"></script>
    <!-- JavaScript -->
	<script type="text/javascript">
	 	jQuery(function($) {
			$(document).ready( function() {
			  LoginUtils.setCodeImg();
			});
		  });
	 	
	 	/*
		 * 用户登录工具类
		 */
	 	LoginUtils = {
	 			
	 		time : "",
	 		codeRemainTime:null,
	 		InterValObj:null,
	 		
	 		/*
			 * 设置图形验证码
			 * 将图形验证码的时间戳放置在this.time中
			 */
	 		setCodeImg:function() {
	 			var currTime = new Date().valueOf();
	 			this.time = currTime;
	 			$("#codeImg").attr("src","http://localhost:8089/login/imgCode?time=" + currTime);
	 		},
	 		
	 		/*
			 * 短信验证码发送单击事件
			 * 更改发送按钮的样式,设置倒计时时间
			 */
	 		getLoginMsg:function() {
	 			//检查可用状态
	 			if($("#msg-button").attr("busy") == "busy") return;
	 			
	 			//检查手机号的合法性
	 			var phone = $("input[name='phone']").val();
	 			if(phone == null || phone == '') {
	 				$("#input-label-phone").css({"color":"#e75854"});
	 				return;
	 			}
	 			var reg = new RegExp("^[1][3,4,5,7,8][0-9]{9}$");
		  		if(!reg.test(phone)) {
		  			$("#input-label-phone").text("请输入正确的手机号!")
		  			$("#input-label-phone").css({"color":"#e75854"});
		  			return ;
		  		}
	 			
		  		//验证通过恢复初始状态
		  		$("#input-label-phone").text("请输入手机号")
	  			$("#input-label-phone").css({"color":"#34495e"});
		  		
		  		//更改按钮状态
	 			$("#msg-button").css({"background-color":"#d9534f"});
	 			$("#msg-button").css({"cursor":"not-allowed"});
	 			$("#msg-button").attr("busy","busy");
	 			this.codeRemainTime = 60;
		  		//设置定时器
		  		this.InterValObj = window.setInterval(LoginUtils.setRemainTimes, 1000);
		  		$("#msg-button").text("重新发送(" + this.codeRemainTime + "s)");
		  		
		  		//发送验证码
		  		$.ajax({
		  			url:"${pageContext.request.contextPath}/user/login/sendLoginMsg",
		  			data:{"phone":phone},
		  			dataType:"json",
		  			type:"POST",
		  			success:function(data){
		  				if(data.code == 1) { //在60s内刷新页面提交短信发送请求
		  					//更新等待时间 并不会发送短信
		  					LoginUtils.codeRemainTime = parseInt(data.msg);
		  				}
		  			}
		  		});	
	 		},
	 		
	 		/*
			 * 定时更新短信重发倒计时
			 * 当倒计时结束时恢复短信发送按钮的可用状态
			 */
	 		setRemainTimes:function() {
	 			//console.log(this.codeRemainTime);
	 			if(LoginUtils.codeRemainTime > 0) {
	 				LoginUtils.codeRemainTime--;
	 				$("#msg-button").text("重新发送(" + LoginUtils.codeRemainTime + "s)");
	 			} else { //倒计时结束
	 				$("#msg-button").text("获取短信验证码");
	 				$("#msg-button").css({"background-color":"#34495e"});
	 				$("#msg-button").css({"cursor":"pointer"});
	 				$("#msg-button").removeAttr("busy");
 				 	//清空定时器
	 				window.clearInterval(LoginUtils.InterValObj);
	 			}
	 		},
	 		
	 		/*
			 * 显示手机验证码登录方式
			 */
	 		showPhoneLogin:function() {
	 			$("#phoneLogin").removeClass("hidden");
	 			$("#passwordLogin").addClass("hidden");
	 		},
	 		
	 		/*
			 * 显示密码登录方式
			 */
	 		showPasswordLogin:function() {
	 			$("#passwordLogin").removeClass("hidden");
	 			$("#phoneLogin").addClass("hidden");
	 		},
	 		
	 		
	 		/*
			 * 短信验证码登录提交
			 */
	 		phoneLoginSubmit:function() {
	 			//检查手机号的合法性
	 			var phone = $("input[name='phone']").val();
	 			if(phone == null || phone == '') {
	 				$("#input-label-phone").css({"color":"#e75854"});
	 				return;
	 			}
	 			var reg = new RegExp("^[1][3,4,5,7,8][0-9]{9}$");
		  		if(!reg.test(phone)) {
		  			$("#input-label-phone").text("请输入正确的手机号!")
		  			$("#input-label-phone").css({"color":"#e75854"});
		  			return ;
		  		}
		  		
		  		//验证通过恢复初始状态
		  		$("#input-label-phone").text("请输入手机号")
	  			$("#input-label-phone").css({"color":"#34495e"});
		  		
		  		//检测验证码是否为空
		  		var code = $("input[name='validCode']").val();
		  		if(code == null  || code == '') {
		  			//$("#input-label-msgCode").text("请输入手机号");
		  			$("#input-label-msgCode").css({"color":"#e75854"});
		  			return;
		  		}
		  		$("#input-label-msgCode").css({"color":"#34495e"});
		  		
		  		//更改提交按钮样式
		  		$("#login-phone-submit-button").text("正在验证...");
		  		
		  		//发送请求
		  		$.ajax({
		  			url:"${pageContext.request.contextPath}/user/phoneLogin",
		  			data:{
		  				"phone":phone,
		  				"code":code
	  				},
		  			dataType:"json",
		  			type:"POST",
		  			success:function(data){
		  				//恢复按钮样式
		  				$("#login-phone-submit-button").html("登&nbsp;&nbsp;&nbsp;录");
		  				
		  				if(data.code == 0) { //登录成功
		  					//获取回调url
		  					var redirect = $("#redirectUrl").val();
		  					$("#alert-model-content").html("登录成功!<br>正在为您跳转...");
		  					$("#alert-model").modal();
		  					if(redirect == null || redirect == "")
		  						setTimeout("window.location.href='http://localhost:8083'",1500);
		  					else
		  						setTimeout("window.location.href='"+redirect+"'",1500);
		  					
		  				} else if(data.code == 1) { 
		  					//当手机号验证成功后发现并未注册时 提醒注册
		  					if(data.msg == "notExist") {
		  						var regisUrl = "<a href='http://localhost:8089/user/register'>前往免费注册</a>"
		  						$("#alert-model-content").html("您的手机号还未注册！<br>" + regisUrl);
		  					} else {
		  						$("#alert-model-content").html(data.msg);
		  					}
		  					$("#alert-model").modal();
		  					
		  				} else if(data.code == 2) {
	  						$("#alert-model-content").html(data.msg);
		  					$("#alert-model").modal();
		  				}
		  			}
		  		});	
	 		},
	 		
	 		/*
			 * 账号密码登录提交
			 */
	 		passwordLoginSubmit:function() {
	 			var username = $("input[name='username']").val();
	 			var password = $("input[name='password']").val();
	 			var imgCode = $("input[name='imgCode']").val();
	 			
	 			//验证用户名
	 			if(username == null || username == '') {
	 				$("#input-label-username").css({"color":"#e75854"});
		  			return;
	 			}
	 			$("#input-label-username").css({"color":"#34495e"});
	 			
	 			//password
	 			if(password == null || password == '') {
	 				$("#input-label-password").css({"color":"#e75854"});
		  			return;
	 			}
	 			$("#input-label-password").css({"color":"#34495e"});
	 			
	 			//imgCode
	 			if(imgCode == null || imgCode == '') {
	 				$("#input-label-imgCode").css({"color":"#e75854"});
		  			return;
	 			}
	 			$("#input-label-imgCode").css({"color":"#34495e"});
	 			
	 			//更改提交按钮
	 			$("#login-password-submit-button").text("正在验证...");
	 			
	 			//提交请求
	 			$.ajax({
		  			url:"${pageContext.request.contextPath}/user/passwordLogin",
		  			data:{
		  				"username":username,
		  				"password":password,
		  				"code":imgCode,
		  				"timestamp":LoginUtils.time
	  				},
		  			dataType:"json",
		  			type:"POST",
		  			success:function(data){
		  				
		  				//恢复按钮样式
		  				$("#login-password-submit-button").html("登&nbsp;&nbsp;&nbsp;录");
		  				if(data.code == 0) { //登陆成功
		  					//获取回调url
		  					var redirect = $("#redirectUrl").val();
		  					$("#alert-model-content").html("登录成功!<br>正在为您跳转...");
		  					$("#alert-model").modal();
		  					if(redirect == null || redirect == "")
		  						setTimeout("window.location.href='http://localhost:8083'",1500);
		  					else
		  						setTimeout("window.location.href='"+redirect+"'",1500);
		  				} else {
		  					$("#alert-model-content").html(data.msg);
		  					$("#alert-model").modal();
		  				} 
		  			}
		  		});	
	 			
	 		},
	 		
	 	};
	 	
	 	
	 	
		/*
		 * 表单提交按钮单击事件
		 * 判断cookie是否存在 -验证cookie
		 * cookie不存在提交登录请求
		 */
		$("#submitForm").click(function(){
			var token = $.cookie('PNW_SESSION');
			if(token != null) {
				//当前浏览器中已存在用户token
				$.ajax({
					url:"http://localhost:8089/user/token",
		  			data:{"token":token},
		  			dataType:"jsonp",
		  			jsonp:"callback",
		  			type:"POST",
		  			success:function(data){
		  				if(data.code == 0) {
		  					//当前用户已经有登录消息
		  					alert("已经登录过,是否以当前信息重新登录?");
		  					
		  				} else {
		  					doLogin();
		  				}
		  			},
		  			
				});
			} else {
				doLogin();
			}
			
		});
		
		/*
		 * 提交登录
		 */
		function doLogin() {
			$.ajax({ 
                type:"POST", 
                data:$("#loginForm").serialize(), 
                url:"${pageContext.request.contextPath}/user/login",
                dataType:"JSON", 
                success: function (data) {
                    console.log(data);
                    if(data.code == 0) {
                    	var redirect = $("#redirectUrl").val();
                    	console.log(redirect);
                    	alert();
                    	if(redirect != null && redirect != "") { //跳转回之前访问的页面
                    		window.location.href = redirect;
                    		return;
                    	} else {
                    		window.location.href = "http://www.antido.cn"; //跳转回首页
                    		return;
                    	}
                    } 
                    alert("错误");
                    
                }   
            });
		}
	</script>
</body>
</html>
