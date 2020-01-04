<!-- FlatFy Theme - Andrea Galanti /-->
<!-- <!doctype html> -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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

    <title>PNW-停车场</title>
	<link rel="shortcut icon" href="http://static.antido.cn/img/favicon.png" type="image/png">
    <!-- Bootstrap core CSS -->
    <%-- <link href="${pageContext.request.contextPath }/css/bootstrap.min.css" rel="stylesheet"> --%>
 	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
 	
    <!-- font-awesome -->
    <%-- <link href="${pageContext.request.contextPath }/font-awesome/css/font-awesome.min.css" rel="stylesheet"> --%>
	<link href="https://cdn.staticfile.org/font-awesome/4.7.0/css/font-awesome.css" rel="stylesheet">
	
    <!-- Custom CSS-->
    <%-- <link href="${pageContext.request.contextPath }/css/general.css" rel="stylesheet"> --%>
    <link href="http://static.antido.cn/css/general.css" rel="stylesheet">
	
	<!-- Carousel -->
    <%-- <link href="${pageContext.request.contextPath }/css/custom.css" rel="stylesheet"> --%>
    <link href="http://static.antido.cn/css/custom.css" rel="stylesheet">
	<%-- <link href="${pageContext.request.contextPath }/css/owl.carousel.css" rel="stylesheet"> --%>
    <%-- <link href="${pageContext.request.contextPath }/css/owl.theme.css" rel="stylesheet"> --%>
    
	<%-- <link href="${pageContext.request.contextPath }/css/style.css" rel="stylesheet"> --%>
	<%-- <link href="${pageContext.request.contextPath }/css/animate.css" rel="stylesheet"> --%>
	<link href="http://static.antido.cn/css/style.css" rel="stylesheet">
	<link href="http://static.antido.cn/css/animate.css" rel="stylesheet">
	
	<%-- <link href="${pageContext.request.contextPath}/css/jquery-ui.css" rel="stylesheet"> --%>
	<link href="https://cdn.staticfile.org/jqueryui/1.10.4/css/jquery-ui.css" rel="stylesheet">
	
	<link href="http://static.antido.cn/css/antido/map.css" rel="stylesheet">
	<link href="http://static.antido.cn/css/antido/mod.css" rel="stylesheet">
	<link href="http://static.antido.cn/css/antido/search.css" rel="stylesheet">
	
	
	<!-- Magnific Popup core CSS file -->
	<link href="http://static.antido.cn/css/magnific-popup.css" rel="stylesheet">	
	<style type="text/css">
		.panel-park-name{
			/* margin-bottom: 0px;
			margin-top: 0px;
			text-align: center;
			font-size: 20px;
			font-family: fantasy; */
			margin-bottom: 0px;
			margin-top: 0px;
			text-align: center;
			font-size: 20px;
			color: inherit;
			line-height: 1.43;
		}
		.use-model-inputGroup {
			width:27%; 
			float: left; 
			padding-left: 10px;
			margin: 0 auto;
		}
		.use-model-timeInput {
			border-color:#1abc9c; 
			height: 30px;
			padding-top: 0;padding-bottom: 0;
		}
		.use-model-inputAddon {
			border-color:#1abc9c; 
			padding: 0; 
			background-color: #1abc9c;
		}
		.space-model-p {
			font-size: 15px;
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
		.alertModal {
			/*position: absolute;
			top: 50%;
			left: 50%; 
			transform: translateX(-50%) translateY(-50%); */
    	}
	</style>
	
</head>

<body id="home">
	<!-- Preloader -->
	<div id="preloader">
		<div id="status"></div>
	</div>
	
	<!-- 导航条-->
	<nav class="navbar-default" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="JavaScript:void(0)">PNW</a>
			</div>

			<div class="collapse navbar-collapse navbar-right navbar-ex1-collapse">
				<ul class="nav navbar-nav">
					<li class="menuItem"><a href="http://search.antido.cn">周围的停车场</a></li>
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
			            <li><a href="#" onclick="UserUtils.logout()">退出登录</a></li>
			          </ul>
			        </li>
				</ul>
				<input type="hidden" name="" id="token-userId"/>
			</div>
			<form id="antido-search-form" class="navbar-form navbar-left">
			    <div style="width: 100%" class="form-group">
			      <input id="searchInput" style="width: 100%" type="text" class="form-control" placeholder="停车场关键字..." autocomplete="off"
			      	onkeydown="if(event.keyCode==13){return false;}">
			    </div>
			</form>
		</div>
	</nav> 
	
	<!-- 内容面板 -->
	<div id="" class="container-fluid">
		<div class="panel panel-default">
		  <div style="padding:5px; background-color: white;" class="panel-heading">
		    <%-- <p class="panel-park-name">${park.name } - ${park.street.district.name }
				<span>（剩余：${park.working_count - park.using_count}）</span>
			</p> --%>
			
			<h4 class="panel-park-name">${park.name } - ${park.street.district.name }
				<span>（剩余：${park.working_count - park.using_count}）</span>
			</h4>
		  </div>
		  <div class="panel-body">
		  <!-- 左侧图面板 -->
		  <div class="col-sm-9">
			<div class="panel panel-default">
				<div style="padding:5px;" class="panel-heading">
			    	 <div class="btn-group btn-group-justified" role="group" aria-label="...">
					  <div class="btn-group" role="group">
					    <button id="button-showMod" type="button" class="btn btn-default">模拟图</button>
					  </div>
					  <div class="btn-group" role="group">
					    <button id="button-showMap" type="button" class="btn btn-default">位置信息</button>
					  </div>
					  <div class="btn-group" role="group">
					    <button id="button-showComment" type="button" class="btn btn-default">留言评论</button>
					  </div>
					</div>
			 	</div>
			 	
			  	<!-- 模拟图 -->
				<div style="padding: 0px 0px" class="panel-body">
				  	<div id="panel-mod" class="mod-space dragscroll"></div>
				    <div id="map-container" style="padding-left: 0px;padding-right: 0px;" id="map-container" class="antido-baiduMap hidden">
						 <div id="baiduMap" class="col-sm-12"></div>
						 <input id="map-xPoint" type="hidden" value="${park.east_longitude }"/>
						 <input id="map-yPoint" type="hidden" value="${park.north_latitude }"/>
					</div>
					<div id="panel-comment" style="height: 500px" class="col-sm-12 hidden">
						<h3>正在开发中...</h3>
					</div>
				</div>
			</div>
		  </div><!-- 左侧结束 -->
		  <!-- 右侧信息面板 -->
		  <div class="col-sm-3">
		  	<div class="panel panel-default">
			  <div style="padding:5px;" class="panel-heading">
			  	<div class="btn-group btn-group-justified" role="group" aria-label="...">
				  <div class="btn-group" role="group">
				    <button type="button" class="btn btn-default">停车场信息</button>
				  </div>
				</div>
			  </div>
			  <div class="panel-body">
			    <p class="mb15"><span style="font-size: 18px;font-style: italic;font-weight: 600" class="mb15">描述：</span>${park.position_desc }</p>
			    <p>
			    	<span style="font-size: 18px;font-style: italic;font-weight: 600">类型：</span>
			    	<span>室外停车场</span>
			    </p>
			    <p>
			    	<span style="font-size: 18px;font-style: italic;font-weight: 600">价格：</span>	
			    	<c:if test="${park.price > 0}">
				    	<span class="mb15">${park.price / 100 }元/小时-前${park.free_time / 60}分钟免费</span>
			    	</c:if>
			    	<c:if test="${park.price == 0}">
				    	<span style="color: green" class="mb15">免费</span>
			    	</c:if>
			    	
			    </p>
			    
                 <input type="hidden" id="parkId" value="${park.id }"/>
                 <input type="hidden" id="point-x" value="${park.east_longitude }"/>
                 <input type="hidden" id="point-y" value="${park.north_latitude }"/>
                 
                 <span class="sublabel">车位状态：
                  	空闲数：<span class="num-carItemCount">${park.working_count - park.using_count}</span>
                  	/总车位数：<span class="num-designCount">${park.working_count }</span>
                 	  </span>
               	  <!-- progress -->
                  <div style="height:18px;border-radius:3px" class="progress">
                    <div id="progress-used" style="width: 0%" class="progress-bar progress-bar-danger"></div>
                    <div id="progress-reserved" style="width: 0%" class="progress-bar progress-bar-warning progress-bar-striped"></div>
                    <div id="progress-free" style="width: 0%" class="progress-bar progress-bar-success"></div>
                  </div>
                  
                   <div class="col-sm-12" style="padding-left: 0 ;padding-bottom: 0px">
                  	<div class="col-sm-4 " style="padding-left: 0">
                  		<img style="width: 60px;" src="${pageContext.request.contextPath }/images/car-success-x2.png">
                  	</div>
                  	<h5 class=" col-sm-8">您的车位</h5>
                  </div>
                 
                  <div class="col-sm-12" style="padding-left: 0 ;padding-bottom: 0px">
                  	<div class="col-sm-4 " style="padding-left: 0">
                  		<img style="width: 60px;" src="${pageContext.request.contextPath }/images/car-error-x2.png">
                  	</div>
                  	<h5 class=" col-sm-8">已经被使用</h5>
                  </div>
                  
                  
                  <div class="col-sm-12" style="padding-left: 0 ;padding-bottom: 0px">
                  	<div class="col-sm-4 " style="padding-left: 0">
                  		<img style="width: 60px;" src="${pageContext.request.contextPath }/images/car-warning-x.png">
                  	</div>
                  	<h5 class=" col-sm-8">已经被预约</h5>
                  </div>
                 
                  
                  <div class="col-sm-12" style="padding-left: 0 ;padding-bottom: 0px">
                  	<div class="col-sm-4 " style="padding-left: 0">
                  		<img style="width: 60px;" src="${pageContext.request.contextPath }/images/car-empty-x.png">
                  	</div>
                  	<h5 class=" col-sm-8">空闲车位</h5>
                  </div>
	
				  <div class="col-sm-12" style="padding-left: 0;padding-bottom: 0px">
                  	<div class="col-sm-4 " style="padding-left: 0">
                  		<img style="width: 60px;" src="${pageContext.request.contextPath }/images/car-unable-x.png">
                  	</div>
                  	<h5 class=" col-sm-8">不可使用</h5>
                  </div>
				
				<div class="col-sm-12" style="padding-left: 0;">
                  <p style="margin-top: 15px">
			    	<span style="font-size: 18px;font-style: italic;font-weight: 600">车场负责人：</span>
			    	<a style="color:#428bca" href="#">${park.parkAdmin.name }</a>
			      </p>
			      <p>
			    	<span style="font-size: 18px;font-style: italic;font-weight: 600">联系电话：</span>
			    	<span>${park.parkAdmin.phone }</span>
			      </p>
			      </div>
			  </div>
			</div>
		  </div>
		  
		  
		  </div><!-- panel-body结束 -->
		</div>
	</div> <!-- 内容面板 结束 -->
	
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
    <!-- <div class="form-group">
	<label for="InputName">Your Name</label>
		<div class="input-group">
			<input type="text" class="form-control" name="InputName" id="InputName" placeholder="Enter Name" required>
			<span class="input-group-addon"><i class="glyphicon glyphicon-ok form-control-feedback"></i></span>
		</div>
	</div> -->
    
    <!-- item-freeCheck-model(空闲车位提示框) -->
	<div class="modal fade bs-example-modal-sm" id="item-freeCheck-model" tabindex="-1" role="dialog" aria-labelledby="item-freeCheck-model">
	  <div class="modal-dialog modal-sm" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="">车位编号：<span style="color: #e74c3c"  id="model-title-spaceCode"></span></h4>
	      </div>
	      <div class="modal-body">
      		<input type="hidden" id="model-nodeId" />
      		<input type="hidden" id="model-controllerId" />
      		<input type="hidden" id="model-spaceId" />
	        <button id="model-btn-use" style="width: 80%; margin: auto" type="button" class="btn btn-danger btn-block">使用车位</button>
	        <p></p>
	        <button id="model-btn-reserve" style="width: 80%; margin: auto" type="button" class="btn btn-warning btn-block">预约车位</button>
	      </div>
	    </div>
	  </div>
	</div><!-- item-freeCheck-model(空闲车位提示框)结束 -->
	
	<!-- item-reserveCheck-model(被预约车位提示框) -->
	<div class="modal fade bs-example-modal-sm" id="item-reserveCheck-model" tabindex="-1" role="dialog" aria-labelledby="item-reserveCheck-model">
	  <div class="modal-dialog modal-sm" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="">车位编号：<span style="color: #e74c3c" id="reserveCheck-title-spaceCode"></span></h4>
	      </div>
	      <div class="modal-body">
	        <h4>该车位已经被人预约</h4>
	      </div>
	    </div>
	  </div>
	</div><!-- item-reserveCheck-model(被预约车位提示框)结束 -->
	
	<!-- item-usingCheck-model(被使用车位提示框) -->
	<div class="modal fade bs-example-modal-md" id="item-usingCheck-model" tabindex="-1" role="dialog" aria-labelledby="item-usingCheck-model">
	  <div class="modal-dialog modal-md" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="">车位编号：<span style="color: #e74c3c" id="usingCheck-title-spaceCode"></span></h4>
	      </div>
	      <div class="modal-body" id="usingCheck-content">
	        <!-- <h4>该车位正在被使用</h4> -->
	        
	      </div>
	    </div>
	  </div>
	</div><!-- item-reserveCheck-model(被使用车位提示框)结束 -->
	
	<!-- item-unableCheck-model(无法使用车位提示框) -->
	<div id="item-unableCheck-model" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="item-unableCheck-model">
	  <div class="modal-dialog modal-sm" role="document">
	    <div class="modal-content">
	      <div class="modal-body">
	      	<h4 style="text-align: center;">该车位目前无法使用</h4>
	      </div>
	    </div>
	  </div>
	</div><!-- item-unableCheck-model(无法使用车位操作框)结束 -->
	
	<!-- space-use-model(车位使用操作框) -->
	<div class="modal fade" id="space-use-model" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header" style="padding-top:12px; padding-bottom: 12px;">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 style="font-size: 20px;" class="modal-title" >车位使用规则</h4>
	      </div>
	      <div class="modal-body">
	        <!-- <h4 style="font-size: 16px">车位使用规则</h4> -->
	        <p class="space-model-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当提交使用请求时该车位上的锁装置将会被打开，需要您在5分钟内将车子停靠在此车位上，当停靠完毕后请重新点击您的车位确认停车完毕。如果在五分钟内没有确认停车，那么本次使用将会作废。</p>
	        <p class="space-model-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车位上的传感器会检测您的车子是否已经停靠成功，在没有正确停靠之前您是无法确认停车的。</p>
	        <p class="space-model-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当停车时间超时后，车位检测到有车但一直未收到您的停车确认时，会有管理员来确认停靠车辆，这可能会对您的信誉造成影响。望注意...<p>
	        <p class="space-model-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您可以选择公开您离开的大概时间，这可能会在车位紧缺时给他人一些希望，当然这不是必须遵守的。</p>
	        <p class="space-model-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当设定离开时间后，如果在设定时间的前后10分钟内释放车位，您将会获得信用积分，该积分将有利于您使用车位预约功能，详情请查看<a href="#" style="color:#428bca;">信用积分规则</a></p>
	        <h4 style="font-size: 15px">预计停靠时间</h4>
	        <div style="height: 30px;margin-bottom: 15px; margin-left: 50px;margin-right: 50px" class="form-group">
		        <div class="input-group use-model-inputGroup">
				  <input id="useModel-days" type="text" class="form-control use-model-timeInput" aria-describedby="useModel-days-addon" 
				  	value="------" onkeyup="InputTools.daysInput(this)" onfocus="InputTools.daysInputFocus(this)" onblur="InputTools.daysInputBlur(this)">
				  <span class="input-group-addon use-model-inputAddon" id="useModel-days-addon">&nbsp;&nbsp;天&nbsp;&nbsp;</span>
				</div>
				
				<span style="float: left; margin:5px 0 0 10px;" class="glyphicon glyphicon-plus" aria-hidden="true"></span>
				
				<div class="input-group use-model-inputGroup">
				  <!-- <input id="useModel-hours" type="text" class="form-control use-model-timeInput" placeholder="几小时" aria-describedby="useModel-hours-addon" onkeyup="InputTools.hoursInput(this)"> -->
				  <select id="useModel-hours" class="form-control use-model-timeInput" aria-describedby="useModel-hours-addon">
				  	<option value="0" selected="selected">------</option>
				  	<option value="1">1</option>
				  	<option value="2">2</option>
				  	<option value="3">3</option>
				  	<option value="4">4</option>
				  	<option value="5">5</option>
				  	<option value="6">6</option>
				  	<option value="7">7</option>
				  	<option value="8">8</option>
				  	<option value="9">9</option>
				  	<option value="10">10</option>
				  	<option value="11">11</option>
				  	<option value="12">12</option>
				  	<option value="13">13</option>
				  	<option value="14">14</option>
				  	<option value="15">15</option>
				  	<option value="16">16</option>
				  	<option value="17">17</option>
				  	<option value="18">18</option>
				  	<option value="19">19</option>
				  	<option value="20">20</option>
				  	<option value="21">21</option>
				  	<option value="22">22</option>
				  	<option value="23">23</option>
				  </select>
				  <span class="input-group-addon use-model-inputAddon" id="useModel-hours-addon">小时</span>
				</div>
				
				<span style="float: left; margin:5px 0 0 10px;" class="glyphicon glyphicon-plus" aria-hidden="true"></span>
				
				<div class="input-group use-model-inputGroup">
				  <!-- <input id="useModel-mins" type="text" class="form-control use-model-timeInput" placeholder="几分钟" aria-describedby="useModel-mins-addon" onkeyup="InputTools.minsInput(this)"> -->
				  <select id="useModel-mins" class="form-control use-model-timeInput" aria-describedby="useModel-mins-addon">
				  	<option value="0" selected="selected">------</option>
				  	<option value="1">1</option>
				  	<option value="2">2</option>
				  	<option value="3">3</option>
				  	<option value="4">4</option>
				  	<option value="5">5</option>
				  	<option value="6">6</option>
				  	<option value="7">7</option>
				  	<option value="8">8</option>
				  	<option value="9">9</option>
				  	<option value="10">10</option>
				  	<option value="11">11</option>
				  	<option value="12">12</option>
				  	<option value="13">13</option>
				  	<option value="14">14</option>
				  	<option value="15">15</option>
				  	<option value="16">16</option>
				  	<option value="17">17</option>
				  	<option value="18">18</option>
				  	<option value="19">19</option>
				  	<option value="20">20</option>
				  	<option value="21">21</option>
				  	<option value="22">22</option>
				  	<option value="23">23</option>
				  	<option value="24">24</option>
				  	<option value="25">25</option>
				  	<option value="26">26</option>
				  	<option value="27">27</option>
				  	<option value="28">28</option>
				  	<option value="29">29</option>
				  	<option value="30">30</option>
				  	<option value="31">31</option>
				  	<option value="32">32</option>
				  	<option value="33">33</option>
				  	<option value="34">34</option>
				  	<option value="35">35</option>
				  	<option value="36">36</option>
				  	<option value="37">37</option>
				  	<option value="38">38</option>
				  	<option value="39">39</option>
				  	<option value="40">40</option>
				  	<option value="41">41</option>
				  	<option value="42">42</option>
				  	<option value="43">43</option>
				  	<option value="44">44</option>
				  	<option value="45">45</option>
				  	<option value="46">46</option>
				  	<option value="47">47</option>
				  	<option value="48">48</option>
				  	<option value="49">49</option>
				  	<option value="50">50</option>
				  	<option value="51">51</option>
				  	<option value="52">52</option>
				  	<option value="53">53</option>
				  	<option value="54">54</option>
				  	<option value="55">55</option>
				  	<option value="56">56</option>
				  	<option value="57">57</option>
				  	<option value="58">58</option>
				  	<option value="59">59</option>
				  </select>
				  
				  <span class="input-group-addon use-model-inputAddon" id="useModel-mins-addon">分钟</span>
				</div>
			</div>
	        <h4 id="use-codeInput-title" style="height:15px;font-size: 15px">
	        	请输入您的6位操作码
	        	<span id="use-codeInput-alert" class="hidden" style="color: #e74c3c">(请输入数字)</span>
        	</h4>
	        <div class="input-group  use-pwd-space">
			  <input id="use-codeInput-0" style="height: 100%" class="use-pwd-input" type="password" onkeyup="InputTools.codeInput(0,'use')" onfocus="InputTools.clean(this)"/>
			  <input id="use-codeInput-1" class="use-pwd-input" type="password" onkeyup="InputTools.codeInput(1,'use')" onfocus="InputTools.clean(this)"/>
			  <input id="use-codeInput-2" class="use-pwd-input" type="password" onkeyup="InputTools.codeInput(2,'use')" onfocus="InputTools.clean(this)"/>
			  <input id="use-codeInput-3" class="use-pwd-input" type="password" onkeyup="InputTools.codeInput(3,'use')" onfocus="InputTools.clean(this)"/>
			  <input id="use-codeInput-4" class="use-pwd-input" type="password" onkeyup="InputTools.codeInput(4,'use')" onfocus="InputTools.clean(this)"/>
			  <input id="use-codeInput-5" style="border-right-width: 3px;" class="use-pwd-input" type="password" onkeyup="InputTools.codeInput(5,'use')" onfocus="InputTools.clean(this)"/>
			</div>
	        <p class="space-model-p" style="margin-bottom: 0px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<code>当前被操作的车位编号为：<span style="font-size: 20px" id="use-model-spaceCode"></span>，请仔细确认防止错位。</code>本次停车将从发送停车请求后计费，计费时包括停车场免费时间。</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	        <button id="useSpaceSubmit" style="padding-left: 45px; padding-right: 45px;" type="button" class="btn btn-primary">提&nbsp;&nbsp;&nbsp;交</button>
	        <!-- <button style="padding-left: 150px; padding-right: 150px; margin-right: 118px; type="button" class="btn btn-primary">提交</button> -->
	      </div>
	    </div>
	  </div>
	</div><!-- space-use-model结束(车位使用操作框) -->
	
	<!-- space-reserve-model(车位预约操作框) -->
	<div class="modal fade" id="space-reserve-model" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header" style="padding-top:12px; padding-bottom: 12px;">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 style="font-size: 20px;" class="modal-title" >车位预约规则</h4>
	      </div>
	      <div class="modal-body">
	        <!-- <h4 style="font-size: 16px">车位使用规则</h4> -->
	        <p class="space-model-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当提交预约请求时该车位上的锁装置将会被关闭，其他人无法使用您预约的车位。您需要在30分钟内来到目标停车场，再次提交停车请求时锁装置将会打开，方便您停车。</p>
	        <p class="space-model-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;十分建议您在到达车位之前不要提交停车请求，已防止他人占用您的车位。</p>
	        <p class="space-model-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车位在您提交预约请求后便会进入计费时间。<p>
	        <p class="space-model-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在预约的三十分钟内随时可以取消本次预约，但不会退还在预约阶段产生的费用，当超过规定时间后预约会被自动取消，您将在一段时间内无法预约其他车位。</p>
	        
	        <h4 id="reserve-codeInput-title" style="height:15px;font-size: 15px">
	        	请输入您的6位操作码
	        	<span id="reserve-codeInput-alert" class="hidden" style="color: #e74c3c">(请输入数字)</span>
        	</h4>
	        <div class="input-group  use-pwd-space">
			  <input id="reserve-codeInput-0" style="height: 100%" class="use-pwd-input" type="password" onkeyup="InputTools.codeInput(0,'reserve')" onfocus="InputTools.clean(this)"/>
			  <input id="reserve-codeInput-1" class="use-pwd-input" type="password" onkeyup="InputTools.codeInput(1,'reserve')" onfocus="InputTools.clean(this)"/>
			  <input id="reserve-codeInput-2" class="use-pwd-input" type="password" onkeyup="InputTools.codeInput(2,'reserve')" onfocus="InputTools.clean(this)"/>
			  <input id="reserve-codeInput-3" class="use-pwd-input" type="password" onkeyup="InputTools.codeInput(3,'reserve')" onfocus="InputTools.clean(this)"/>
			  <input id="reserve-codeInput-4" class="use-pwd-input" type="password" onkeyup="InputTools.codeInput(4,'reserve')" onfocus="InputTools.clean(this)"/>
			  <input id="reserve-codeInput-5" style="border-right-width: 3px;" class="use-pwd-input" type="password" onkeyup="InputTools.codeInput(5,'reserve')" onfocus="InputTools.clean(this)"/>
			</div>
	        <p class="space-model-p" style="margin-bottom: 0px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<code>当前被操作的车位编号为：<span style="font-size: 20px" id="reserve-model-spaceCode"></span>，请仔细确认防止错位。</code>本次预约将从发送请求后开始计费，计费时包括停车场免费时间。</p>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	        <button id="reserveSpaceSubmit" style="padding-left: 45px; padding-right: 45px;" type="button" class="btn btn-primary">提&nbsp;&nbsp;&nbsp;交</button>
	        <!-- <button style="padding-left: 150px; padding-right: 150px; margin-right: 118px; type="button" class="btn btn-primary">提交</button> -->
	      </div>
	    </div>
	  </div>
	</div><!-- space-reserve-model(车位预约操作框)结束 -->
	
	<!-- space-reserveConfirm-model(车位预约结束操作框) -->
	<div class="modal fade" id="space-reserveConfirm-model" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header" style="padding-top:12px; padding-bottom: 12px;">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 style="font-size: 20px;" class="modal-title" >您预约的车位:<span id="space-reserveConfirm-spaceCode"></span></h4>
	      </div>
	      <div class="modal-body">
	        <!-- <h4 style="font-size: 16px">车位使用规则</h4> -->
	        <p class="space-model-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;提交使用申请后车位锁将会打开，请尽快将您的车辆停好。</p>
	        <p class="space-model-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;十分建议您在到达车位之前不要提交停车请求，已防止他人占用您的车位。</p>
	        <p class="space-model-p">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在预约的三十分钟内随时可以取消本次预约，但不会退还在预约阶段产生的费用，当超过规定时间后预约会被自动取消，您将在一段时间内无法预约其他车位。</p>
	        <h4 style="font-size: 15px">预计停靠时间</h4>
	        <div style="height: 30px;margin-bottom: 15px; margin-left: 50px;margin-right: 50px" class="form-group">
		        <div class="input-group use-model-inputGroup">
				  <input id="reserveModel-days" type="text" class="form-control use-model-timeInput" aria-describedby="reserveModel-days-addon" 
				  	value="------" onkeyup="InputTools.daysInput(this)" onfocus="InputTools.daysInputFocus(this)" onblur="InputTools.daysInputBlur(this)">
				  <span class="input-group-addon use-model-inputAddon" id="reserveModel-days-addon">&nbsp;&nbsp;天&nbsp;&nbsp;</span>
				</div>
				
				<span style="float: left; margin:5px 0 0 10px;" class="glyphicon glyphicon-plus" aria-hidden="true"></span>
				
				<div class="input-group use-model-inputGroup">
				  <!-- <input id="useModel-hours" type="text" class="form-control use-model-timeInput" placeholder="几小时" aria-describedby="useModel-hours-addon" onkeyup="InputTools.hoursInput(this)"> -->
				  <select id="reserveModel-hours" class="form-control use-model-timeInput" aria-describedby="reserveModel-hours-addon">
				  	<option value="0" selected="selected">------</option>
				  	<option value="1">1</option>
				  	<option value="2">2</option>
				  	<option value="3">3</option>
				  	<option value="4">4</option>
				  	<option value="5">5</option>
				  	<option value="6">6</option>
				  	<option value="7">7</option>
				  	<option value="8">8</option>
				  	<option value="9">9</option>
				  	<option value="10">10</option>
				  	<option value="11">11</option>
				  	<option value="12">12</option>
				  	<option value="13">13</option>
				  	<option value="14">14</option>
				  	<option value="15">15</option>
				  	<option value="16">16</option>
				  	<option value="17">17</option>
				  	<option value="18">18</option>
				  	<option value="19">19</option>
				  	<option value="20">20</option>
				  	<option value="21">21</option>
				  	<option value="22">22</option>
				  	<option value="23">23</option>
				  </select>
				  <span class="input-group-addon use-model-inputAddon" id="reserveModel-hours-addon">小时</span>
				</div>
				
				<span style="float: left; margin:5px 0 0 10px;" class="glyphicon glyphicon-plus" aria-hidden="true"></span>
				
				<div class="input-group use-model-inputGroup">
				  <!-- <input id="useModel-mins" type="text" class="form-control use-model-timeInput" placeholder="几分钟" aria-describedby="useModel-mins-addon" onkeyup="InputTools.minsInput(this)"> -->
				  <select id="reserveModel-mins" class="form-control use-model-timeInput" aria-describedby="reserveModel-mins-addon">
				  	<option value="0" selected="selected">------</option>
				  	<option value="1">1</option>
				  	<option value="2">2</option>
				  	<option value="3">3</option>
				  	<option value="4">4</option>
				  	<option value="5">5</option>
				  	<option value="6">6</option>
				  	<option value="7">7</option>
				  	<option value="8">8</option>
				  	<option value="9">9</option>
				  	<option value="10">10</option>
				  	<option value="11">11</option>
				  	<option value="12">12</option>
				  	<option value="13">13</option>
				  	<option value="14">14</option>
				  	<option value="15">15</option>
				  	<option value="16">16</option>
				  	<option value="17">17</option>
				  	<option value="18">18</option>
				  	<option value="19">19</option>
				  	<option value="20">20</option>
				  	<option value="21">21</option>
				  	<option value="22">22</option>
				  	<option value="23">23</option>
				  	<option value="24">24</option>
				  	<option value="25">25</option>
				  	<option value="26">26</option>
				  	<option value="27">27</option>
				  	<option value="28">28</option>
				  	<option value="29">29</option>
				  	<option value="30">30</option>
				  	<option value="31">31</option>
				  	<option value="32">32</option>
				  	<option value="33">33</option>
				  	<option value="34">34</option>
				  	<option value="35">35</option>
				  	<option value="36">36</option>
				  	<option value="37">37</option>
				  	<option value="38">38</option>
				  	<option value="39">39</option>
				  	<option value="40">40</option>
				  	<option value="41">41</option>
				  	<option value="42">42</option>
				  	<option value="43">43</option>
				  	<option value="44">44</option>
				  	<option value="45">45</option>
				  	<option value="46">46</option>
				  	<option value="47">47</option>
				  	<option value="48">48</option>
				  	<option value="49">49</option>
				  	<option value="50">50</option>
				  	<option value="51">51</option>
				  	<option value="52">52</option>
				  	<option value="53">53</option>
				  	<option value="54">54</option>
				  	<option value="55">55</option>
				  	<option value="56">56</option>
				  	<option value="57">57</option>
				  	<option value="58">58</option>
				  	<option value="59">59</option>
				  </select>
				  
				  <span class="input-group-addon use-model-inputAddon" id="reserveModel-mins-addon">分钟</span>
				</div>
			</div>
	        <h4 id="reserveComfirm-codeInput-title" style="height:15px;font-size: 15px">
	        	请输入您的6位操作码
	        	<span id="reserve-codeInput-alert" class="hidden" style="color: #e74c3c">(请输入数字)</span>
        	</h4>
	        <div class="input-group  use-pwd-space">
			  <input id="reserveConfirm-codeInput-0" style="height: 100%" class="use-pwd-input" type="password" onkeyup="InputTools.codeInput(0,'reserveConfirm')" onfocus="InputTools.clean(this)"/>
			  <input id="reserveConfirm-codeInput-1" class="use-pwd-input" type="password" onkeyup="InputTools.codeInput(1,'reserveConfirm')" onfocus="InputTools.clean(this)"/>
			  <input id="reserveConfirm-codeInput-2" class="use-pwd-input" type="password" onkeyup="InputTools.codeInput(2,'reserveConfirm')" onfocus="InputTools.clean(this)"/>
			  <input id="reserveConfirm-codeInput-3" class="use-pwd-input" type="password" onkeyup="InputTools.codeInput(3,'reserveConfirm')" onfocus="InputTools.clean(this)"/>
			  <input id="reserveConfirm-codeInput-4" class="use-pwd-input" type="password" onkeyup="InputTools.codeInput(4,'reserveConfirm')" onfocus="InputTools.clean(this)"/>
			  <input id="reserveConfirm-codeInput-5" style="border-right-width: 3px;" class="use-pwd-input" type="password" onkeyup="InputTools.codeInput(5,'reserveConfirm')" onfocus="InputTools.clean(this)"/>
			</div>
	      </div>
	      <div class="modal-footer">
	        <button id="reserveCancelSubmit" style="padding-left: 45px; padding-right: 45px;" type="button" class="btn btn-default">我要取消预约</button>
	        <button id="reserveConfirmSubmit" style="padding-left: 45px; padding-right: 45px;" type="button" class="btn btn-primary">我要使用车位</button>
	      </div>
	    </div>
	  </div>
	</div><!-- space-reserve-model(车位预约操作框)结束 -->
	
	<!-- model-user-checkWait(停车确认提示框) -->	
	<div id="model-user-checkWait" class="modal fade" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">您有一个停车确认</h4>
				</div>
				<div class="modal-body">
					<p><span id="model-user-checkWait-name"></span>您好。</p>
					<p>您正在使用的是
					<a style="color:#3385ff;text-decoration: underline;" id="model-user-checkWait-parkHref" href="#">
						<span id="model-user-checkWait-parkName"></span>
					</a>
					<code><span id="model-user-checkWait-spaceCode"></span></code>号车位。</p>
					<p>当您的车辆确认停靠完毕时请点击<code>车停好了</code>。</p>
					<p>请在申请使用后<code><span id="model-user-checkWait-time"></span>分钟</code>内确认停车，在确认等待期间随时可以双击您的所在的车位进行确认。</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">别催~给我点时间</button>
					<button id="parkConfirmSubmit" type="button" class="btn btn-primary">我已经停好了</button>
				</div>
			</div>
		</div>
	</div><!-- model-user-checkWait(停车确认提示框)结束 -->	
	
	<!-- model-user-parkInfo(用户车位信息显示模态框) -->
	<div id="model-user-parkInfo" class="modal fade" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title">您的正在使用的车位</h4>
				</div>
				<div class="modal-body">
					<div class="table-responsive">
					  <table class="table table-bordered ">
			    		<tbody>
					  		<tr>
						    	<th>车辆:</th>
						    	<td id="model-user-parkInfo-car"></td>
						  	</tr>
						  	<tr>
						    	<th>停车时间:</th>
						    	<td id="model-user-parkInfo-parkTime"></td>
						  	</tr>
						  	<tr>
						    	<th>截止目前已停靠:</th>
						    	<td id="model-user-parkInfo-timeCount"></td>
						  	</tr>
						  	<tr>
						    	<th>收费标准:</th>
						    	<td id="model-user-parkInfo-price"></td>
						  	</tr>
						  	<tr>
						    	<th>预计缴费:</th>
						    	<td id="model-user-parkInfo-priceCount">30.0元</td>
						  	</tr>
						  	<tr>
						    	<th>计划离开时间:</th>
						    	<td id="model-user-parkInfo-leave">无</td>
						  	</tr>
					    </tbody>
					  </table>
					</div><!-- table结束 -->
					<h4 id="parkInfo-codeInput-title" style="height:15px;font-size: 15px">
			        	请输入您的6位操作码
			        	<span id="leave-codeInput-alert" class="hidden" style="color: #e74c3c">(请输入数字)</span>
		        	</h4>
			        <div class="input-group  use-pwd-space">
					  <input id="leave-codeInput-0" style="height: 100%" class="use-pwd-input" type="password" onkeyup="InputTools.codeInput(0,'leave')" onfocus="InputTools.clean(this)"/>
					  <input id="leave-codeInput-1" class="use-pwd-input" type="password" onkeyup="InputTools.codeInput(1,'leave')" onfocus="InputTools.clean(this)"/>
					  <input id="leave-codeInput-2" class="use-pwd-input" type="password" onkeyup="InputTools.codeInput(2,'leave')" onfocus="InputTools.clean(this)"/>
					  <input id="leave-codeInput-3" class="use-pwd-input" type="password" onkeyup="InputTools.codeInput(3,'leave')" onfocus="InputTools.clean(this)"/>
					  <input id="leave-codeInput-4" class="use-pwd-input" type="password" onkeyup="InputTools.codeInput(4,'leave')" onfocus="InputTools.clean(this)"/>
					  <input id="leave-codeInput-5" style="border-right-width: 3px;" class="use-pwd-input" type="password" onkeyup="InputTools.codeInput(5,'leave')" onfocus="InputTools.clean(this)"/>
					</div>
					<input type="hidden" id="model-user-parkInfo-hidden-controllerId"/>
				</div><!-- model-content结束 -->
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="model-user-parkInfo-leaveSubmit" type="button" class="btn btn-primary">我要离开</button>
				</div>
			</div>
		</div>
	</div><!-- model-user-parkInfo(用户车位信息显示模态框)结束 -->
	
	
	
	<!-- ALERT-MODEL-SM -->
	<div id="alert-model"  class="modal fade bs-example-modal-sm alertModal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
	  <div class="modal-dialog modal-sm" role="document">
	    <div class="modal-content">
	        <h4 style="text-align: center;margin-bottom: 25px;margin-top: 25px" id="alert-model-content">你好啊同学!</h4>
	      <!-- <h3 id="alert-model-content">你好啊同学!</h3> -->
	    </div>
	  </div>
	</div>
	
	<!-- ALERT-MODEL-MID -->
	<div id="alert-model-mid"  class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	    	<h4 style="margin-left:20px; margin-bottom: 25px;margin-top: 25px" class="modal-title" id="alert-model-content-mid">你好啊同学!</h4>
		   <!-- <div class="modal-header">
		        
	      	</div>
	       <h3 id="alert-model-content">你好啊同学!</h3> -->
	    </div>
	  </div>
	</div>
	
	
    <!-- JavaScript -->
     <script src="https://cdn.staticfile.org/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://static.antido.cn/js/jquery-migrate-1.2.1.min.js"></script>
    <script src="https://cdn.staticfile.org/jqueryui/1.10.4/jquery-ui.min.js"></script>
	<script src="http://static.antido.cn/js/jquery.corner.js"></script>
	<script src="https://cdn.staticfile.org/jquery-cookie/1.1/jquery.cookie.min.js"></script>
	<script src="https://cdn.staticfile.org/jquery-mousewheel/3.1.0/jquery.mousewheel.min.js"></script>
   	<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <script src="https://cdn.staticfile.org/modernizr/2010.07.06dev/modernizr.min.js"></script>  <!-- Modernizr /-->
	<script src="https://cdn.staticfile.org/owl-carousel/1.32/owl.carousel.js"></script>
	<%-- <script src="${pageContext.request.contextPath }/js/uiMorphingButton_inflow.js"></script> --%>
	<script src="http://static.antido.cn/js/script.js"></script>
	<!-- StikyMenu -->
	<!-- <script src="http://static.antido.cn/js/stickUp.min.js"></script> -->
	<script src="http://static.antido.cn/js/wow.min.js"></script>
	<script src="http://static.antido.cn/js/classie.js"></script>
	<!-- Magnific Popup core JS file -->
	<script src="https://cdn.staticfile.org/magnific-popup.js/1.1.0/jquery.magnific-popup.min.js"></script> 
	<script src="https://cdn.staticfile.org/dragscroll/0.0.8/dragscroll.min.js"></script>
	
	<script src="http://static.antido.cn/js/antido.format.js"></script>
	<script src="http://static.antido.cn/js/antido.map-park.js"></script>
	<script src="http://static.antido.cn/js/antido.mod.js"></script>
	<script src="http://static.antido.cn/js/antido.color.js"></script>
	<script src="http://static.antido.cn/js/antido.search-complete.js"></script>
	<script src="http://static.antido.cn/js/antido.user.js"></script>
	<script src="http://static.antido.cn/js/antido.input.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/getscript?v=2.0&ak=yI43GzysGKGcADFGUo6etBG6kMbmbO7I"></script>
	
	<script type="text/javascript">
		//关键字检索
		searchComplete();
		/*页面加载完毕*/
		$().ready(function() {
			UserUtils.getUserByCookie();
			//初始化模拟图
			ModUtils.init("${pageContext.request.contextPath}",getQueryString("parkId"));
		}); //页面加载完毕结束
		
		function getQueryString(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
			var r = window.location.search.substr(1).match(reg);
			if (r != null)
				//返回第三个参数(参数的值)
				return r[2];
			return null;
		}
	
		/*面板切换单击事件-展示模拟图*/
		$("#button-showMod").click(function() {
			$("#panel-mod").removeClass("hidden");
			$("#map-container").addClass("hidden");
			$("#panel-comment").addClass("hidden");
		});
		
		/*面板切换单击事件-展示地图*/
		$("#button-showMap").click(function() {
			$("#panel-mod").addClass("hidden");
			$("#map-container").removeClass("hidden");
			$("#panel-comment").addClass("hidden");
			//初始化地图
			initMap(0.618);
			
			var x = $("#map-xPoint").val() / 1000000;
			var y = $("#map-yPoint").val() / 1000000;
			console.log(x);
			console.log(y);
			var point = new BMap.Point(x, y); //将标注点转化成地图上的点
	        map.centerAndZoom(point,13)
	        var marker = new BMap.Marker(point); //将点转化成标注点
	        map.addOverlay(marker);  //将标注点添加到地图上
		});
		
		/*面板切换单击事件-展示评论区*/
		$("#button-showComment").click(function() {
			$("#panel-mod").addClass("hidden");
			$("#map-container").addClass("hidden");
			$("#panel-comment").removeClass("hidden");
		});
	
		/*
		 * 车位使用按钮单击事件
		 */
		$("#model-btn-use").click(function() {
			if(UserUtils.user != null && UserUtils.user.space != null) {
				$("#item-freeCheck-model").modal("hide");
				$("#alert-model-content").text("您已拥有正在使用的车位...");
				$("#alert-model").modal();
				return;
			}
			InputTools.cleanOpCode();
			$('#item-freeCheck-model').modal('hide')
			$('#space-use-model').modal();
		});
		
		
		/*
		 * 车位预约按钮单击事件
		 */
		$("#model-btn-reserve").click(function() {
			if(UserUtils.user != null && UserUtils.user.space != null) {
				$("#item-freeCheck-model").modal("hide");
				$("#alert-model-content").text("您已拥有正在使用的车位...");
				$("#alert-model").modal();
				return;
			}
			InputTools.cleanOpCode();
			$('#item-freeCheck-model').modal('hide')
			$('#space-reserve-model').modal();
		});
		
		
		/*
		 * 车位离开按钮单击事件
		 */
		$("#model-btn-leave").click(function() {
			$.ajax({
	  			url:"http://park.antido.cn/leaveSpace",
	  			data:{
	  				"nodeId":$("#model-nodeId").val(),
	  				"controllerId":$("#model-controllerId").val(),
	  				"parkId":$("#parkId").val(),
	  				"spaceId":$("#model-spaceId").val(),
	  				"userId":$("#token-userId").val(),
	  				"token":$.cookie("PNW_SESSION")
	  			},
	  			dataType:"json",
	  			type:"POST",
	  			success:function(data){
	  				console.log(data);
	  			}
			});
		});
		
		/*
		 * 获取用户cookie,如果cookie已失效跳转到登录页面
		*/
		function checkUser() {
			var cookie = $.cookie("PNW_SESSION");
			if(cookie != null) return ;
			//跳转至登录页面
			var url = window.location.href;
			window.location.href = "http://sso.antido.cn/login?redirect=" +url;
			
		}
		
		//车位使用提交
		$("#useSpaceSubmit").click(function(){
			//先验证登录
			var cookie = $.cookie("PNW_SESSION");
			if(cookie == null || cookie == "") {
				UserUtils.checkUser();
				return;
			}
			
			//获取预计离开时间
			var time = InputTools.getTimeInput("use");
			if (time == -1) {
				return;
			}
			
			//获取操作码
			var opCode = InputTools.getCodeInput("use");
			if(opCode == -1) {
				$("#use-codeInput-title").css({"color":"#e74c3c"});
				return;
			}
			
			$("#space-use-model").modal("hide");
			$("#alert-model-content").text("正在操作，请稍后...");
			$("#alert-model").modal();
			
			//向后台发送停车请求
			$.ajax({
	  			url:"http://park.antido.cn/useSpace",
	  			data:{
	  				"nodeId":$("#model-nodeId").val(),
	  				"controllerId":$("#model-controllerId").val(),
	  				"parkId":$("#parkId").val(),
	  				"spaceId":$("#model-spaceId").val(),
	  				"userId":$("#token-userId").val(),
	  				"token":$.cookie("PNW_SESSION"),
	  				"opCode":opCode,
	  				"leavingTime":time
	  			},
	  			dataType:"json",
	  			type:"POST",
	  			success:function(data){
	  				console.log(data);
	  				if(data.code == 0) { //操作成功
	  					$("#alert-model-content").text("操作成功!");
	  					//刷新当前页面
	  					setTimeout("window.location.reload()",1500);
	  				} else if(data.code == 1) {//操作异常
	  					//if(data.msg == "余额不足") alert("余额不足");
	  					$("#alert-model-content").text(data.msg);
	  				} else if(data.code == 2) { //操作错误
	  					$("#alert-model-content").text("操作失败!");
	  				} else { //code == 3 操作超时
	  					$("#alert-model-content").text("操作超时!");
	  				}
	  			}
			});
		});//车位使用提交结束
		
		
		$("#reserveSpaceSubmit").click(function(){
			//先验证登录
			var cookie = $.cookie("PNW_SESSION");
			if(cookie == null || cookie == "") {
				UserUtils.checkUser();
				return;
			}
			
			//获取操作码
			var opCode = InputTools.getCodeInput("reserve");
			if(opCode == -1) {
				$("#reserve-codeInput-title").css({"color":"#e74c3c"});
				return;
			}
			
			$("#space-reserve-model").modal("hide");
			$("#alert-model-content").text("正在操作，请稍后...");
			$("#alert-model").modal();
			
			//向后台发送停车请求
			$.ajax({
	  			url:"http://park.antido.cn/reserveSpace",
	  			data:{
	  				"nodeId":$("#model-nodeId").val(),
	  				"controllerId":$("#model-controllerId").val(),
	  				"parkId":$("#parkId").val(),
	  				"spaceId":$("#model-spaceId").val(),
	  				"userId":$("#token-userId").val(),
	  				"token":$.cookie("PNW_SESSION"),
	  				"opCode":opCode
	  			},
	  			dataType:"json",
	  			type:"POST",
	  			success:function(data){
	  				console.log(data);
	  				if(data.code == 0) { //操作成功
	  					$("#alert-model-content").text("操作成功!");
	  					//刷新当前页面
	  					setTimeout("window.location.reload()",1500);
	  				} else if(data.code == 1) {//操作异常
	  					$("#alert-model-content").text(data.msg);
	  				} else if(data.code == 2) { //操作错误
	  					$("#alert-model-content").text("操作失败!");
	  				} else { //code == 3 操作超时
	  					$("#alert-model-content").text("操作超时!");
	  				}
	  			}
			});
		});
		
		//预约确认提交
		$("#reserveConfirmSubmit").click(function(){
			var token = UserUtils.getToken();
			var opCode = InputTools.getCodeInput("reserveConfirm");
			//获取预计离开时间
			var time = InputTools.getTimeInput("reserve");
			if (time == -1) {
				return;
			}
			
			$("#space-reserveConfirm-model").modal("hide");
			$("#alert-model-content").text("正在操作，请稍后...");
			$("#alert-model").modal();
			//异步提交提车请求
			$.ajax({
	  			url:"http://park.antido.cn/reserveConfirm",
	  			data:{
	  					"token":token,
	  					"opCode":opCode,
	  					"leavingTime":time
	  				},
	  			dataType:"json",
	  			type:"POST",
	  			success:function(data){
	  				console.log(data);
	  				if(data.code == 0) { //操作成功
	  					$("#alert-model-content").text("操作成功!");
	  					//刷新当前页面
	  					setTimeout("window.location.reload()",1500);
	  				} else if(data.code == 1) {//操作异常
	  					$("#alert-model-content").text(data.msg);
	  				} else if(data.code == 2) { //操作错误
	  					$("#alert-model-content").text("操作失败!");
	  				} else { //code == 3 操作超时
	  					$("#alert-model-content").text("操作超时!");
	  				}
	  			}
			});
		});
		
		//停车确认提交
		$("#parkConfirmSubmit").click(function() {
			var token = UserUtils.getToken();
			
			$("#model-user-checkWait").modal('hide');
			$("#alert-model-content").text("正在操作，请稍后...");
			$("#alert-model").modal();
			//异步提交提车请求
			$.ajax({
	  			url:"http://park.antido.cn/parkConfirm",
	  			data:{"token":token},
	  			dataType:"json",
	  			type:"GET",
	  			success:function(data){
	  				if(data.code == 0) { //操作成功
	  					//显示停车确认成功并刷新页面
	  					$("#alert-model-content").html("停车确认成功！");
	  					//显示提示1.5s后刷新页面
	  					setTimeout("window.location.reload()",1500);
	  				} else if(data.code == 1) {//操作异常
	  					//显示停车确认操作异常停车确认提交但车位未检测到车辆
	  					$("#alert-model").modal('hide');
	  					$("#alert-model-content-mid").html("<p style='color:#d43f3a'>暂未检测到车位停靠。</p><p>请确认车位正确,车辆停好之后再次确认。<p/><p>如有疑问及时请联系管理员！<p/>");
	  					$("#alert-model-mid").modal();
	  				} else if(data.code == 2) { //操作错误
	  					//停车确数据操作失败
	  					$("#alert-model-content").html("数据异常");
	  					$("#alert-model").modal();
	  				} else { //code == 3 操作超时
	  					
	  				}
	  			}
			});
		});//停车确认提交结束
		
		$("#model-user-parkInfo-leaveSubmit").click(function() {
			var opCode = InputTools.getCodeInput("leave");
			$("#model-user-parkInfo").modal('hide');
			$("#alert-model-content").text("正在操作，请稍后...");
			$("#alert-model").modal();
			$.ajax({
				url:ModUtils.param.contextPath+"/leaveSpace",
				data:{
					"nodeId":UserUtils.user.space.node.id,
					"controllerId":$("#model-user-parkInfo-hidden-controllerId").val(),
					"parkId":UserUtils.user.space.park.id,
					"spaceId":UserUtils.user.space.idStr,
					"userId":UserUtils.user.id,
					"token":UserUtils.getToken(),
					"opCode":opCode
				},
				dataType:"json",
				type:"POST",
				success:function(data){
					if(data.code == 0) { //操作成功
	  					$("#alert-model-content").html("操作成功!<br>驶离车位后将自动扣费..");
	  					//刷新当前页面
	  					setTimeout("window.location.reload()",1500);
	  				} else if(data.code == 1) {//操作异常
	  					$("#alert-model-content").text(data.msg);
	  				} else if(data.code == 2) { //操作错误
	  					$("#alert-model-content").text("操作失败!");
	  				} else { //code == 3 操作超时
	  					$("#alert-model-content").text("操作超时!");
	  				}
				}
			});
		});
		
		//预约取消提交
		$("#reserveCancelSubmit").click(function() {
			var token = UserUtils.getToken();
			var opCode = InputTools.getCodeInput("reserveConfirm");
			
			$("#space-reserveConfirm-model").modal("hide");
			$("#alert-model-content").text("正在操作，请稍后...");
			$("#alert-model").modal();
			//异步提交提车请求
			$.ajax({
	  			url:"http://park.antido.cn/reserveCancel",
	  			data:{
	  					"token":token,
	  					"opCode":opCode
	  				},
	  			dataType:"json",
	  			type:"POST",
	  			success:function(data){
	  				console.log(data);
	  				if(data.code == 0) { //操作成功
	  					$("#alert-model-content").text("操作成功!");
	  					//刷新当前页面
	  					setTimeout("window.location.reload()",1500);
	  				} else if(data.code == 1) {//操作异常
	  					$("#alert-model-content").text(data.msg);
	  				} else if(data.code == 2) { //操作错误
	  					$("#alert-model-content").text("操作失败!");
	  				} else { //code == 3 操作超时
	  					$("#alert-model-content").text("操作超时!");
	  				}
	  			}
			});
		});
		
	</script>
	
</body>
</html>
