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

    <title>PNW-注册</title>
	<link rel="shortcut icon" href="http://static.antido.cn/img/favicon.png" type="image/png">
    <!-- Bootstrap core CSS -->
    <link href="http://static.antido.cn/css/bootstrap.min.css" rel="stylesheet">
 
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
	
	<!-- Magnific Popup core CSS file -->
	<%-- <link rel="stylesheet" href="${pageContext.request.contextPath }/css/magnific-popup.css">  --%>
	<style type="text/css">
		.input-group-success > input{
			border-color: #1abc9c;
		}
		.input-group-success > span{
			border-color: #1abc9c;
			background-color: #1abc9c;
		}
		
		.input-group-error > input{
			border-color: #d9534f;
		}
		.input-group-error > span{
			border-color: #d9534f;
			background-color: #d9534f;
		}
		.input-alert{
			padding-left:10px;
			height: 20px;
		}
		.input-alert-msg{
			font-size: 15px; 
			color: #d9534f;
		}
		.form-group{
			margin-bottom: 5px;
		}
		.input-code-button:hover{
			cursor:pointer;
			background-color: #1abc9c;
		}
		
		.input-code-busy:hover{
			cursor:not-allowed;
			background-color: #1abc9c;
		}
		
		.input-code-error{
			border-color: #d9534f;
			background-color: #d9534f;
		}
		.input-code-success{
			border-color: #1abc9c;
			background-color: #1abc9c;
		}
		.regis-step-item{
			width: 32%;
			background-color: #bdc3c7;
		}
		.ckbox, .rdio {
			position: relative;
		}
		.ckbox input[type="checkbox"], .rdio input[type="radio"] {
			opacity: 0;
		}
		.ckbox label, .rdio label {
			padding-left: 10px;
			cursor: pointer;
			margin-bottom: 7px !important;
		}
		.rdio label:before {
			width: 18px;
			height: 18px;
			position: absolute;
			top: 8px;
			left: 0px;
			/* top: 1px;
			left: 0; */
			content: '';
			display: inline-block;
			-moz-border-radius: 50px;
			-webkit-border-radius: 50px;
			border-radius: 50px;
			border: 1px solid #bbb;
			background: #fff;
		}
		
		.rdio input[type="radio"]:disabled+label {
			color: #999;
		}
		
		.rdio input[type="radio"]:disabled+label:before {
			background-color: #eee;
		}
		
		.rdio input[type="radio"]:checked+label::after {
			content: '';
			position: absolute;
			top: 12px;
			left: 4px;
			/* top: 5px;
			left: 4px; */ 
			display: inline-block;
			font-size: 11px;
			width: 10px;
			height: 10px;
			background-color: #444;
			-moz-border-radius: 50px;
			-webkit-border-radius: 50px;
			border-radius: 50px;
		}
		
		.rdio-default input[type="radio"]:checked+label:before {
			border-color: #999;
		}
		
		.rdio-primary input[type="radio"]:checked+label:before {
			border-color: #428BCA;
		}
		
		.rdio-primary input[type="radio"]:checked+label::after {
			background-color: #428BCA;
		}
		
		.rdio-warning input[type="radio"]:checked+label:before {
			border-color: #F0AD4E;
		}
		
		.rdio-warning input[type="radio"]:checked+label::after {
			background-color: #F0AD4E;
		}
		
		.rdio-success input[type="radio"]:checked+label:before {
			border-color: #1CAF9A;
		}
		
		.rdio-success input[type="radio"]:checked+label::after {
			background-color: #1CAF9A;
		}
		
		.rdio-danger input[type="radio"]:checked+label:before {
			border-color: #D9534F;
		}
		
		.rdio-danger input[type="radio"]:checked+label::after {
			background-color: #D9534F;
		}
		.rdio-black input[type="radio"]:checked+label:before {
			border-color: black;
		}
		
		.rdio-black input[type="radio"]:checked+label::after {
			background-color: black;
		}
		
		.rdio-white input[type="radio"]:checked+label:before {
			border-color: black;
		}
		.rdio-white input[type="radio"]:checked+label::after {
			background-color: white;
		}
		
		.rdio-red input[type="radio"]:checked+label:before {
			border-color: #d9534f;
		}
		.rdio-red input[type="radio"]:checked+label::after {
			background-color: #d9534f;
		}
		
		.rdio-blue input[type="radio"]:checked+label:before {
			border-color: #337ab7;
		}
		.rdio-blue input[type="radio"]:checked+label::after {
			background-color: #337ab7;
		}
		
		.rdio-gray input[type="radio"]:checked+label:before {
			border-color: #c0c0c0;
		}
		.rdio-gray input[type="radio"]:checked+label::after {
			background-color: #c0c0c0;
		}
		
		.rdio-yellow input[type="radio"]:checked+label:before {
			border-color: #ffde07;
		}
		.rdio-yellow input[type="radio"]:checked+label::after {
			background-color: #ffde07;
		}
		
		.rdio-khaki input[type="radio"]:checked+label:before {
			border-color: #cb9c0e;
		}
		.rdio-khaki input[type="radio"]:checked+label::after {
			background-color: #cb9c0e;
		}
		
		.rdio-wine input[type="radio"]:checked+label:before {
			border-color: #79030c;
		}
		.rdio-wine input[type="radio"]:checked+label::after {
			background-color: #79030c;
		}
		
		.rdio-green input[type="radio"]:checked+label:before {
			border-color: #5cb85c;
		}
		.rdio-green input[type="radio"]:checked+label::after {
			background-color: #5cb85c;
		}
		
		.use-pwd-space {
			height:40px;
			width: 66%;
			margin: 0px auto 15px auto;
		}
		.use-pwd-input {
			width: 16.6%;
			height: 100%;
    		border: solid;
    		/* border-color: #1abc9c; */
			border-color: #34495e;
			border-right-width: 0px;
			text-align:center
		}
		
		input[name="carDesc"]::-webkit-input-placeholder { /* WebKit browsers */ 
			color: black;
			font-size:15px;
		}
		input[name="carDesc"]:-moz-placeholder { /* Mozilla Firefox 4 to 18 */ 
			color: black;
			font-size:15px;
		} 
		input[name="carDesc"]::-moz-placeholder { /* Mozilla Firefox 19+ */ 
			color: black;
			font-size:15px;
		}
		input[name="carDesc"]:-ms-input-placeholder { /* Internet Explorer 10+ */ 
			color: black;
			font-size:15px;
		}
		
		.open > .dropdown-menu {
		    margin-top: 0px !important;
		    opacity: 1;
		    visibility: visible;
		}
		
	</style>
	<%-- <script src="${pageContext.request.contextPath }/js/modernizr-2.6.2.min.js"></script> --%>
</head>

<body id="home">

	<!-- Preloader -->
	<div id="preloader">
		<div id="status"></div>
	</div>
	
	<!-- NavBar-->
	<nav class="navbar-default" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#home">PNW</a>
			</div>

			<div class="collapse navbar-collapse navbar-right navbar-ex1-collapse">
				<ul class="nav navbar-nav">
					<li class="menuItem"><a href="http://sso.antido.cn">周围的停车场</a></li>
					<li id="header-login" class="menuItem"><a href="http://sso.antido.cn/login">登录</a></li>
					<li id="header-space" class="menuItem hidden"><a href="http://sso.antido.cn/login">未停靠</a></li>
					<li id="header-mySpace" class="menuItem hidden"><a href="JavaScript:void(0)">我的车位</a></li>
					<li id="header-userInfo" class="dropdown hidden">
			          <a href="JavaScript:void(0)" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
			          	<span id="header-userInfo-name">Antido</span><span class="caret"></span>
		          	  </a>
			          <ul class="dropdown-menu">
			            <li><a id="header-userInfo-car" href="#">我的车辆</a></li>
			            <li><a id="header-userInfo-order" href="#">泊车记录</a></li>
			            <li><a id="header-userInfo-money" href="#">账户余额：<span id="header-userInfo-remain">30.5</span>（元）</a></li>
			            <li role="separator" class="divider"></li>
			            <li><a href="#">状态：<span id="header-userInfo-state">未停靠</span></a></li>
			            <li role="separator" class="divider"></li>
			            <li><a href="javaScript:void(0)" onclick="UserUtils.logout()">退出登录</a></li>
			          </ul>
			        </li>
				</ul>
			</div>
		   
		</div>
	</nav> 
	<!-- 表单输入区域 -->
	<div class="container-fluid">
		<div style="" class="col-md-4"></div>
		<!-- 用户注册区域 -->
		<div style="margin-bottom: 50px;" class="col-md-4">
		<h3 style="text-align: center;margin-bottom: 30px;">PNW用户注册<small>&nbsp;&nbsp;&nbsp;Welcome to PNW</small></h1>
		<!-- 用户注册表单 -->
		<form id="userInfoForm" role="form" action="" method="">
	    	<div class="form-group">
				<div id="input-group-name" class="input-group">
					<input type="text" class="form-control" id="inputName" name="nick_name"  placeholder="设置用户名" autocomplete="off"
						data-toggle="tooltip" data-placement="left" title="请输入2-20个字符，可以包含汉字，字母、数字和下划线"
						onfocus="$(this).tooltip('show')" onblur="RegisUtils.checkName(this)">
					<span class="input-group-addon"><i id="input-name-icon" class="glyphicon glyphicon-user form-control-feedback"></i></span>
				</div>
				<div class="input-alert">
					<span id="input-name-alert" class="input-alert-msg hidden"></span>
				</div>
			</div>
			<div class="form-group">
				<div id="input-group-pwd1" class="input-group">
					<input type="password" class="form-control" id="inputPwd1" name="password"  placeholder="设置登录密码" autocomplete="off"
						data-toggle="tooltip" data-placement="left" title="请输入6-20个字符，至少包含数字和字母"
						onfocus="$(this).tooltip('show')" onblur="RegisUtils.checkPWD_1(this)">
					<span class="input-group-addon"><i id="input-pwd1-icon" class="glyphicon glyphicon-lock form-control-feedback"></i></span>
				</div>
				<div class="input-alert">
					<span id="input-pwd1-alert" class="input-alert-msg hidden"></span>
				</div>
			</div>
	    	<div class="form-group">
				<div id="input-group-pwd2" class="input-group">
					<input type="password" class="form-control" id="inputPwd2" name="" placeholder="请再次输入密码" autocomplete="off"
						data-toggle="tooltip" data-placement="left" title="请再次输入密码，必须与第一次输入保持一致"
						onfocus="$(this).tooltip('show')" onblur="RegisUtils.checkPWD_2(this)">
					<span class="input-group-addon"><i id="input-pwd2-icon" class="glyphicon glyphicon-lock form-control-feedback"></i></span>
				</div>
				<div class="input-alert">
					<span id="input-pwd2-alert" class="input-alert-msg hidden"></span>
				</div>
			</div>
	    	<div class="form-group">
				<div id="input-group-email" class="input-group">
					<input type="text" class="form-control" id="inputEmail" name="email" placeholder="输入您的邮箱" autocomplete="off"
						data-toggle="tooltip" data-placement="left" title="请输入您的邮箱，用于找回和更改密码"
						onfocus="$(this).tooltip('show')" onblur="RegisUtils.checkEmail(this)">
					<span class="input-group-addon"><i id="input-email-icon" class="glyphicon glyphicon-envelope form-control-feedback"></i></span>
				</div>
				<div class="input-alert">
					<span id="input-email-alert" class="input-alert-msg hidden"></span>
				</div>
			</div>
		    <div class="form-group">
				<div id="input-group-phone" class="input-group">
					<span class="input-group-addon" id="phone-startWith">+86</span>
					<input type="text" class="form-control" id="inputPhone" name="phone" placeholder="输入您手机号" autocomplete="off" aria-describedby="phone-startWith"
						 onblur="RegisUtils.checkPhone(this)">
					<span class="input-group-addon"><i id="input-phone-icon" class="glyphicon glyphicon-phone form-control-feedback"></i></span>
				</div>
				<div class="input-alert">
					<span id="input-phone-alert" class="input-alert-msg hidden"></span>
				</div>
			</div>
			<div class="form-group">
				<div id="input-group-code" class="input-group">
					<input type="text" class="form-control" id="inputCode" name="code" placeholder="短信验证码" autocomplete="off"
						onblur="RegisUtils.checkCode(this)">
					<div id="input-code-icon" class="input-group-addon input-code-button" onclick="RegisUtils.sendPhoneMsg()">
						<span id="input-code-info">获取验证码</span>
					</div>					
				</div>
				<div class="input-alert">
					<span id="input-code-alert" class="input-alert-msg hidden"></span>
				</div>
			</div>
			<!--<input type="submit" name="submit" id="submit" value="Submit" class="btn wow tada btn-embossed btn-primary pull-right">-->
			<button id="formSubmit" type="button" class="btn btn-primary btn-lg btn-block" onclick="RegisUtils.userInfoSubmit()">提交</button>				
		</form> <!-- 用户注册表单 -->
		</div> <!-- 用户注册区域 -->
		<div class="col-md-4"></div>
	</div><!-- container-fluid表单输入区域结束 -->
	
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
	<!-- Smoothscroll -->
	<%-- <script src="${pageContext.request.contextPath }/js/jquery.corner.js"></script> --%> 
	<script src="http://static.antido.cn/js/wow.min.js"></script>
	<script src="http://static.antido.cn/js/classie.js"></script>
	<%-- <script src="${pageContext.request.contextPath }/js/uiMorphingButton_inflow.js"></script> --%>
	<!-- Magnific Popup core JS file -->
	<!-- <script src="https://cdn.staticfile.org/magnific-popup.js/1.1.0/jquery.magnific-popup.min.js"></script> -->
	<script src="http://static.antido.cn/js/antido/antido.input.js"></script>
	<script src="http://static.antido.cn/js/antido/antido.user.js"></script>
	<script type="text/javascript">
	$(document).ready( function() {
	  //获取用户cookie
	  UserUtils.getUserByCookie();
	});
	  var RegisUtils = {
			  
	  	InterValObj : null,
	  	codeRemainTime : null,
	  	vaildName : null,
	  	vaildPhone : null,
	  	/**
	  	 * 检测用户名输入的合法性
	  	 * 向后台查询改用户名是否存在
	  	 * @param ths:input标签
	  	 */
	  	checkName:function(ths){
	  		var val = $(ths).val();
	  		if(val == null) {
	  			RegisUtils.inputCallBack(false,"用户名长度应在2到20个字符之间！", "name", "user")
	  			return false;
	  		}
	  		//验证长度
	  		if(val.length > 20 || val.length < 2) {
	  			RegisUtils.inputCallBack(false,"用户名长度应在2到20个字符之间！", "name", "user")
	  			return false;
	  		}

			//验证用户名是否包含非法字符
	  		var reg = new RegExp("[^a-zA-Z0-9\_\u4e00-\u9fa5]","i"); 
	  		if(reg.test(val)) {
	  			RegisUtils.inputCallBack(false,"用户名中请不要包含非法字符！", "name", "user")
	  			return false;
	  		}
	  		
	  		//验证用户名的唯一性
	  		$.ajax({
	  			url:"http://sso.antido.cn/user/register/vaildName",
	  			data:{name:val},
	  			dataType:"json",
	  			type:"POST",
	  			async:false, 
	  			success:function(data){
	  				console.log(data);
	  				if(data.code == 0) {
	  					RegisUtils.vaildName = null;
	  				} else {
	  					RegisUtils.vaildName = data.msg;
	  					RegisUtils.inputCallBack(false, data.msg, "name", "user");
	  				}
	  			}
	  		});	
	  		
	  		console.log(RegisUtils.vaildName);
	  		
	  		if(RegisUtils.vaildName != null) {
	  			RegisUtils.inputCallBack(false, RegisUtils.vaildName, "name", "user")
		  		return false;
	  		}
	  		
	  		RegisUtils.inputCallBack(true, null, "name", "user")
	  		return true;
	  	},
	  	
	  	/**
	  	 * 验证PWD_1的合法性
	  	 * 并给予用户反馈
	  	 * @param ths input标签
	  	 */
	  	checkPWD_1:function(ths){
	  		var val = $(ths).val();
	  		if(val == null) {
	  			RegisUtils.inputCallBack(false,"密码长度应在6到20个字符之间！","pwd1","lock");
	  			return false;
	  		}
	  		if(6 > val.length || val.length > 20) {
	  			RegisUtils.inputCallBack(false,"密码长度应在6到20个字符之间！","pwd1","lock");
	  			return false;
	  		}
	  		var hasletter = new RegExp("[a-zA-z]+");
	  		var hasNum = new RegExp("[0-9]+");
	  		if(!hasletter.test(val) || !hasNum.test(val)) {
	  			RegisUtils.inputCallBack(false,"密码中请至少包含数字和字母！","pwd1","lock");
	  			return false;
	  		}
	  		RegisUtils.inputCallBack(true,null,"pwd1","lock");
	  		return true;
	  	},
	  	
	  	/**
	  	 * 验证PWD_2的合法性
	  	 * 并给予用户反馈
	  	 * @param ths input标签
	  	 */
	  	checkPWD_2:function(ths){
	  		var val = $(ths).val();
	  		if(val == null) {
	  			RegisUtils.inputCallBack(false,"密码长度应在6到20个字符之间！","pwd2","lock");
	  			return false;
	  		}
	  		if(6 > val.length || val.length > 20) {
	  			RegisUtils.inputCallBack(false,"密码长度应在6到20个字符之间！","pwd2","lock");
	  			return false;
	  		}
	  		var hasletter = new RegExp("[a-zA-z]+");
	  		var hasNum = new RegExp("[0-9]+");
	  		if(!hasletter.test(val) || !hasNum.test(val)) {
	  			RegisUtils.inputCallBack(false,"密码中请至少包含数字和字母！","pwd2","lock");
	  			return false;
	  		}
	  		if(val != $("#inputPwd1").val()) {
	  			RegisUtils.inputCallBack(false,"与上一次设置的密码不符!","pwd2","lock");
	  			return false;
	  		}
	  		RegisUtils.inputCallBack(true,null,"pwd2","lock");
	  		return true;
	  	},
	  	
	  	/**
	  	 * 验证邮箱的合法性
	  	 * @param ths email输入框
	  	 */
	  	checkEmail:function(ths){
	  		var val = $(ths).val();
	  		if(val == null) {
	  			RegisUtils.inputCallBack(false,"邮箱不能为空!","email","envelope");
	  			return false;
	  		}
	  		var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
	  		if(!reg.test(val)) {
	  			RegisUtils.inputCallBack(false,"请输入正确的邮箱格式!","email","envelope");
	  			return false;
	  		}
	  		RegisUtils.inputCallBack(true,null,"email","envelope");
	  		return true;
	  	},
	  	
	  	/**
	  	 * 验证输入手机号的合法性
	  	 * @param ths email输入框
	  	 */
	  	checkPhone:function(ths){
	  		var val = $(ths).val();
	  		if(val == null) {
	  			RegisUtils.inputCallBack(false,"手机号不能为空!","phone","phone");
	  			return false;
	  		}
	  		var reg = new RegExp("^[1][3,4,5,7,8][0-9]{9}$");
	  		if(!reg.test(val)) {
	  			RegisUtils.inputCallBack(false,"请输入正确的手机号码格式!","phone","phone");
	  			return false;
	  		}
	  		
	  		//TODO:验证手机号的唯一性
	  		$.ajax({
	  			url:"http://sso.antido.cn/user/register/vaildPhone",
	  			data:{"phone":val},
	  			dataType:"json",
	  			type:"POST",
	  			async:false, 
	  			success:function(data){
	  				if(data.code == 0) {
	  					RegisUtils.vaildPhone = null;
	  				} else {
	  					RegisUtils.vaildPhone = data.msg;
	  				}
	  			}
	  		});	
	  		
	  		if(RegisUtils.vaildPhone != null) {
	  			RegisUtils.inputCallBack(false, RegisUtils.vaildPhone, "phone", "phone")
		  		return false;
	  		}
	  		
	  		RegisUtils.inputCallBack(true,null,"phone","phone");
	  		return true;
	  	},
	  	
	  	/**
	  	 * 验证手机验证码的合法性
	  	 * @param ths email输入框
	  	 */
	  	checkCode:function(ths){
	  		var val = $(ths).val();
	  		if(val == null || val == "") {
	  			$("#input-group-code").removeClass("input-group-success");
	  			$("#input-group-code").addClass("input-group-error");
	  			$("#input-code-icon").removeClass("input-code-success");
	  			$("#input-code-icon").addClass("input-code-error");
	  			$("#input-code-alert").html("请输入验证码!");
	  			$("#input-code-alert").removeClass("hidden");
	  			return false;
	  		}
	  		$("#input-group-code").removeClass("input-group-error");
	  		$("#input-group-code").addClass("input-group-success");
	  		$("#input-code-icon").removeClass("input-code-error");
	  		$("#input-code-icon").addClass("input-code-success");
	  		$("#input-code-alert").addClass("hidden");
	  		return true;
	  	},
	  	
	  	/**
	  	 * 向用户回显用户名输入的合法性
	  	 * @param isVaild 是否有效
	  	 * @param msg 消息
	  	 * @param item 需要改变标签id关键字
	  	 * @param icon 需要改变的图标关键字
	  	 */
	  	inputCallBack:function(isVaild, msg, item, icon) {
	  		if(isVaild) {
	  			$("#input-group-" + item).removeClass("input-group-error");
		  		$("#input-group-" + item).addClass("input-group-success");
		  		$("#input-" + item + "-alert").addClass("hidden");
		  		$("#input-" + item + "-icon").removeClass("glyphicon-" + icon);
		  		$("#input-" + item + "-icon").removeClass("glyphicon-remove");
	  			$("#input-" + item + "-icon").addClass("glyphicon-ok");
	  		} else {
	  			$("#input-group-" + item).removeClass("input-group-success");
	  			$("#input-group-" + item).addClass("input-group-error");
	  			$("#input-" + item + "-alert").html(msg);
	  			$("#input-" + item + "-alert").removeClass("hidden");
	  			$("#input-" + item + "-icon").removeClass("glyphicon-" + icon);
	  			$("#input-" + item + "-icon").removeClass("glyphicon-ok");
	  			$("#input-" + item + "-icon").addClass("glyphicon-remove");
	  		}
	  	},
	  	
	  	/**
	  	 * 发送短信验证码
	  	 * 设置定时倒计时显示重新发送的剩余时间
	  	 * 将发送按钮设置为不可用
	  	 */
	  	sendPhoneMsg:function(){
	  		//检测手机号是否合法
	  		if(!this.checkPhone(document.getElementById("inputPhone"))) {
	  			return;
	  		}
	  		
	  		//检查可用状态
	  		if($("#input-code-icon").attr("disabled") == "disabled") return;
	  		this.codeRemainTime = 60;
	  		//设置定时器
	  		this.InterValObj = window.setInterval(RegisUtils.setRemainTimes, 1000);
	  		//发送按钮不可用
	  		$("#input-code-icon").attr("disabled","disabled"); 
	  		//更改样式
	  		$("#input-code-icon").removeClass("input-code-button");
	  		$("#input-code-icon").addClass("input-code-busy");
	  		
	  		//发送验证码
	  		var phone = $(document.getElementById("inputPhone")).val();
	  		$.ajax({
	  			url:"http://sso.antido.cn/user/register/sendRegisMsg",
	  			data:{"phone":phone},
	  			dataType:"json",
	  			type:"POST",
	  			success:function(data){
	  				if(data.code == 1) {
	  					//更新等待时间 并不会发送短信
	  					RegisUtils.codeRemainTime = parseInt(data.msg);
	  				}
	  			}
	  		});	
	  		
	  	},
	  	
	  	/**
	  	 * 检查并设置短信验证码的发送等待时间
	  	 */
	  	setRemainTimes:function() {
	  		if(RegisUtils.codeRemainTime == 0){ //倒计时结束
	  			window.clearInterval(this.InterValObj); //清空定时器
	  			$("#input-code-icon").removeAttr("disabled"); //发送按钮重新可用
	  			//按钮内容变为重新发送
	  			$("#input-code-icon").removeClass("input-code-busy");
	  			$("#input-code-icon").addClass("input-code-button");
	  			$("#input-code-info").html("重新发送");
	  			
	  		} else {
	  			//更新按钮显示时间
	  			$("#input-code-info").html("重新发送("+RegisUtils.codeRemainTime+"s)");
	  			RegisUtils.codeRemainTime--;
	  		}
	  			
	  	},
	  	
	  	/**
	  	 * 验证所有表单项
	  	 * return isValid
	  	 */
	  	checkUserInfo:function(){
	  		return this.checkName(document.getElementById("inputName")) 
	  		& this.checkPWD_1(document.getElementById("inputPwd1"))
	  		& this.checkPWD_2(document.getElementById("inputPwd2"))
	  		& this.checkEmail(document.getElementById("inputEmail"))
	  		& this.checkPhone(document.getElementById("inputPhone"))
	  		& this.checkCode(document.getElementById("inputCode"));
	  	},
	  	
	  	/**
	  	 * 用户信息表单提交
	  	 */
	  	userInfoSubmit:function() {
	  		if(!this.checkUserInfo()) return;
	  		
	  		//异步提交表单
	  		$.ajax({
	  			url:"http://sso.antido.cn/user/register/addUser",
	  			data:$('#userInfoForm').serialize(),
	  			dataType:"json",
	  			type:"POST",
	  			success:function(data){
	  				//注册成功后跳转至车辆绑定页面
	  				if(data.code == 0) {
	  					//显示注册成功跳转提示
	  					$("#alert-model-content").html("注册成功!<br>马上为您跳转至车辆绑定页面...");
	  					$("#alert-model").modal();
	  					setTimeout("window.location.href='http://sso.antido.cn/user/setCar'",2500);
	  				} else {
	  					$("#alert-model-content").html(data.msg);
	  					$("#alert-model").modal();
	  				}
	  				
	  			}
	  		});	
	  	},
	  	
	  	/**
	  	 * 根据用户所选项展示车辆描述预览
	  	 */
	  	setPreview:function(val) {
	  		if(val == "1") $("#showType2").attr("checked","checked");
	  		//判断品牌和车型是否已经选择
	  		if($("select[name='brand']  option:selected").val() == '0' || $("select[name='model']  option:selected").val() == '0') {
	  			$("#carPreview").val('');
	  			$("input[name='showType']").attr('disabled',true);
	  			$("#carPreview").attr('disabled',true);
	  			return;
	  		} 
	  		
	  		$("input[name='showType']").removeAttr('disabled');
  			$("#carPreview").removeAttr('disabled');
  			
	  		var desc = this.generateCarDesc(val);
	  		if(desc == null || desc == "") {
	  			
	  		}
	  		$("#carPreview").val(desc);
	  	},
	  	
	  	/**
	  	 * 根据所选项生成车俩描述
	  	 */
	  	generateCarDesc:function(val){
	  		var brand = $("select[name='brand'] option:selected").text();
	  		var model = $("select[name='model'] option:selected").text();
	  		var type = $("select[name='model'] option:selected").attr("carType");
	  		var color = $("input[name='color']:checked + label").text();
	  		switch(val){
	  			case '0':{ //保密
	  				return "无";
	  			}
				case '1':{ //精简
	  				return color + type; 				
	  			}
				case '2':{ //普通
	  				return color + brand;
				}
				case '3':{ //详细
					return brand + model + "("+ color +")";
				}
				default:return null;
	  		}
	  	},
	  	
	  	/**
	  	 * 车牌号输入键盘离开事件
	  	 * 删除非法字符,将小写字母自动转换为大写字母,控制字符的长度不超过5
	  	 * ths:input框 js对象
	  	 */
	  	licenseInput:function(ths) {
	  		var val = ths.value;
	  		//只留下字母和数字
	  		var re = /[^a-zA-z0-9]+/g;
	  		val = val.replace(re,"");
	  		//全部大写
	  		val = val.toUpperCase();
	  		//去除超出长度的部分
	  		if(val.length > 5) val = val.substring(0,5);
	  		ths.value = val;
	  	},
	  	
	  	/**
	  	 * 异步加载车辆品牌数据
	  	 */
	  	loadBrand:function() {
	  		$.ajax({
	  			url:"http://sso.antido.cn/user/register/getBrands",
	  			dataType:"json",
	  			type:"GET",
	  			success:function(data){
	  				var select = $("select[name='brand']");
	  				for(var i = 0 ; i < data.length ; i++) {
	  					var option = "<option value="+data[i].id+">"+data[i].name+"</option>";
	  					select.append(option);
	  				}
	  			}
	  		});	
	  	},
	  	
	  	/**
	  	 * 根据所选的品牌异步加载该品牌下所有的车型
	  	 */
	  	loadCarModel:function(){
	  		var brandId = $("select[name='brand'] option:selected").val();
	  		var select = $("select[name='model']");
	  		select.empty();
	  		select.append("<option value='0' selected='selected'>请选择您的车辆型号</option>");
	  		$.ajax({
	  			url:"http://sso.antido.cn/user/register/getModelByBrand",
	  			data:{"brandId":brandId},
	  			dataType:"json",
	  			type:"GET",
	  			success:function(data){
	  				console.log(data);
	  				select = $("select[name='model']");
	  				for(var i = 0 ; i < data.length ; i++) {
	  					var option = "<option value="+data[i].name+" carType='"+ data[i].car_type +"'>"+data[i].name+"</option>";
	  					select.append(option);
	  				}
	  			}
	  		});	
	  		this.setPreview('1');
	  		$("#car-alert-brand").addClass("hidden");
	  	},
	  	
	  	/**
	  	 * 车型更改事件
	  	 */
	  	carModelChange:function(){
	  		this.setPreview('1');
	  		if($("select[name='model'] option:selected").val() == '0') {
	  			$("#car-alert-model").removeClass("hidden");
	  			return;
	  		}
	  		$("#car-alert-model").addClass("hidden");
	  	},
	  	
	  	checkOpCode:function() {
	  		var opcode = InputTools.getCodeInput("user");
	  		
	  	},
	  	
	  	/**
	  	 * 用户提交
	  	 * 验证数据完整性
	  	 */
	  	carInfoSubmit:function() {
	  		if(UserUtils.user == null) {
	  			//重定向到register
	  			window.location.href="http://sso.antido.cn/login"; 
	  		}
	  		//品牌
	  		if($("select[name='brand'] option:selected").val() == '0'){
	  			$("#car-alert-brand").removeClass("hidden");
	  			return;
	  		}
	  		//车型
	  		if($("select[name='model'] option:selected").val() == '0'){
	  			$("#car-alert-model").removeClass("hidden");
	  			return;
	  		}
	  		
	  		//车牌好
	  		if($("input[name='licenseNum']").val() == ''){
	  			$("#car-alert-license").removeClass("hidden");
	  			return;
	  		}
	  		
	  		//opcode
	  		var opcode = InputTools.getCodeInput("use");
	  		var opcode1 = InputTools.getCodeInput("again");
	  		console.log(opcode);
	  		console.log(opcode1);
	  		if(opcode == -1) {
	  			$("#car-alert-opcode").removeClass("hidden");
	  			return;
	  		}
	  		if(opcode != opcode1) {
	  			$("#car-alert-opcodeAg").removeClass("hidden");
	  			return;
	  		}
	  		var brand = $("select[name='brand'] option:selected").text();
	  		var model = $("select[name='model'] option:selected").text();
	  		var carType = $("select[name='model'] option:selected").attr("carType");
	  		var color = $("input[name='color']:checked").val();
	  		var showType = $("input[name='showType']:checked").val();
	  		var licenseRegion = $("select[name='licenseRegion'] option:selected").val();
	  		var licenseletter = $("select[name='licenseletter'] option:selected").val();
	  		var licenseNum = $("input[name='licenseNum']").val();
	  		var license = licenseRegion + licenseletter + licenseNum;
	  		
	  		var city = 0;
	  		var isShowPhone = $("input[name='isShowPhone']:checked").val();
	  		var isShowLicense = $("input[name='isShowLicense']:checked").val();
	  		var isShowName = $("input[name='isShowName']:checked").val();
	  		
	  		/*
	  		console.log(brand);
	  		console.log(model);
	  		console.log(carType);
	  		console.log(color);
	  		console.log(showType);
	  		console.log(licenseRegion);
	  		console.log(licenseletter);
	  		console.log(licenseNum);
	  		console.log(opcode);
	  		console.log(opcode1);
	  		*/
	  		var token = UserUtils.getToken;
	  		var userId = UserUtils.user.id;
	  		
	  		$.ajax({
	  			url:"http://sso.antido.cn/user/register/addCar",
	  			data:{
	  				"token":token,
	  				"userId":userId,
	  				"brand":brand,
	  				"model":model,
	  				"carType":carType,
	  				"color":color,
	  				"showType":showType,
	  				"license":license,
	  				"city":city,
	  				"isShowPhone":isShowPhone,
	  				"isShowLicense":isShowLicense,
	  				"isShowName":isShowName,
	  				"opCode":opCode
	  			},
	  			dataType:"json",
	  			type:"POST",
	  			success:function(data){
	  				if(data.code == 0){ //注册成功跳转至searchWeb
	  					//弹出提示框
	  				}
	  			}
	  		});	
	  	},
	}
	</script>  
</body>

</html>
