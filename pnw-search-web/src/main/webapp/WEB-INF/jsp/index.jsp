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

    <title>PNW-查找</title>
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
	
	<%-- <link href="${pageContext.request.contextPath }/css/antido/map.css" rel="stylesheet"> --%>
	<link href="http://static.antido.cn/css/antido/map.css" rel="stylesheet">
	<link href="http://static.antido.cn/css/antido/mod.css" rel="stylesheet">
	<link href="http://static.antido.cn/css/antido/search.css" rel="stylesheet">
	
	<!-- Magnific Popup core CSS file -->
	<%-- <link rel="stylesheet" href="${pageContext.request.contextPath }css/magnific-popup.css"> --%> 
	<link href="http://static.antido.cn/css/magnific-popup.css" rel="stylesheet"> 
</head>
<body id="home">
	<!-- loader -->
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
				<a class="navbar-brand" href="#home">PNW</a>
			</div>
			
			<div class="collapse navbar-collapse navbar-right navbar-ex1-collapse">
				<ul class="nav navbar-nav">
					<li class="menuItem"><a href="http://localhost:8083">周围的停车场</a></li>
					<li id="header-login" class="menuItem"><a href="http://localhost:8089/login">登录</a></li>
					<li id="header-space" class="menuItem hidden"><a href="http://localhost:8089/login">未停靠</a></li>
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
			            <li><a href="javaScript:void(0)"  onclick="UserUtils.logout()">退出登录</a></li>
			          </ul>
			        </li>
				</ul>
			</div>
			<!-- 快速检索 -->
		    <form id="antido-search-form" class="navbar-form navbar-left">
			    <div style="width: 100%" class="form-group">
			      <input id="searchInput" style="width: 100%" type="text" class="form-control" placeholder="停车场关键字..." autocomplete="off"
			      	onkeydown="if(event.keyCode==13){return false;}">
			    </div>
			</form>
		</div>
	</nav> 
	
	<!-- 匹配条件 -->
	<div id="" class="queryPanel" style="margin-top: 20px">
		<div class="container">
			<ul class="list-group">
			  <li class="list-group-item">
			  	<div class="row">
				  	<span class="col-sm-2">停车场类型:</span>
				  	<div class="col-sm-10">
				  	  <span id="parkInfo-type-0" class="query-free" onclick="parkTypeQuery(0)">不限</span>
					  <span id="parkInfo-type-1" class="query-free" onclick="parkTypeQuery(1)">室外</span>
					  <span id="parkInfo-type-2" class="query-free" onclick="parkTypeQuery(2)">室内</span>
					  <span id="parkInfo-type-3" class="query-free" onclick="parkTypeQuery(3)">立体停车场</span>
					</div>
				</div>
			  </li>
			  <li class="list-group-item">
			  	<div class="row">
				  	<span class="col-sm-2">车位类型:</span>
				  	<div class="col-sm-10">
					  <span id="parkInfo-space-0" class="query-free" onclick="spaceTypeQuery(0)">不限</span>
					  <span id="parkInfo-space-1" class="query-free" onclick="spaceTypeQuery(1)">我是小型车</span>
					  <span id="parkInfo-space-2" class="query-free" onclick="spaceTypeQuery(2)">我是中大型（卡车、货车）</span>
					</div>
				</div>
			  </li>
			  <li class="list-group-item">
			  	<div class="row">
				  	<span class="col-sm-2">停车场服务方式:</span>
				  	<div class="col-sm-10">
					  <span id="parkInfo-service-0" class="query-free" onclick="serviceTypeQuery(0)">不限</span>
					  <span id="parkInfo-service-1" class="query-free" onclick="serviceTypeQuery(1)">自助停车（可预约）</span>
					  <span id="parkInfo-service-2" class="query-free" onclick="serviceTypeQuery(2)">入场计时收费（不可预约）</span>
					</div>
				</div>
			  </li>
			  <li class="list-group-item">
			  	<div class="row">
				  	<span class="col-sm-2">价格:</span>
				  	<div class="col-sm-10">
					  <span id="parkInfo-charge-0" class="query-free" onclick="chargeTypeQuery(0)">不限</span>
					  <span id="parkInfo-charge-1" class="query-free" onclick="chargeTypeQuery(1)">免费</span>
					  <span id="parkInfo-charge-2" class="query-free" onclick="chargeTypeQuery(2)">不免费</span>
					  <input id="lowPrice" class="priceLimit" style="margin-left: 30px" type="text" number:true/>
					  -
					  <input id="highPrice" class="priceLimit" type="text" 	number:true/>	
					  <span>（元/小时）</span>
					  <span class="query-checked" onclick="priceLimitQuery()">&nbsp&nbsp&nbsp确定&nbsp&nbsp&nbsp </span>
					  <span id="priceLimitAlert" class="hidden" style="color: #a94442">只能输入数字</span>
					</div>
				</div>
			  </li>
			</ul>
		</div>
	</div>
	
	<!-- 数据显示面板 -->
		<div class="container">
			<ul class="list-group">
			  <li style="margin-bottom: 0px" class="list-group-item">地图上会默认显示在您附近的停车场，同时也可以使用地图中的搜索功能显示目标地点周围的停车场。</li>
			  <li class="list-group-item">
			  	<div class="row">
			  		<!-- 地图 -->
				    <div id="map-container" class="antido-baiduMap col-sm-5">
						 <div id="baiduMap" class="col-sm-12"></div>
						 <div class="antido-mapSearch">
							<div class="input-group mb15">
					     	<input id="mapSearch" type="text" class="form-control" placeholder="位置搜索...">
					     	<span  style="cursor: pointer;" class="input-group-addon">
					     		<span class="glyphicon  glyphicon-search"></span>
				     		 </span>
					     	</div>
					     </div>
					</div>
					
					<!-- 停车场信息列表 -->
					<div class="col-sm-7">
						<ul class="list-group parkList">
							<li style="padding-top:0px;padding-bottom: 0px; border-radius:0px;border: 0px " class="list-group-item list-group-item-info">
							    <div id="parkList-defaultOrder" class="parkList-queryOrder-checked" onclick="defaultQueryOrder()">
							    	<span>综合排序</span>
							    </div>
							    <div id="parkList-priceOrder" class="parkList-queryOrder" onclick="priceQueryOrder()">
							    	价格最优<i class="fa fa-angle-down"></i>
						    	</div>
							    <div id="parkList-remainOrder" class="parkList-queryOrder" onclick="remainQueryOrder()">
							    	最多空位<i class="fa fa-angle-down"></i>
						    	</div>
							    <div id="parkList-distanceOrder" class="parkList-queryOrder" onclick="distanceQueryOrder()">
							    	离我最近<i class="fa fa-angle-down"></i>
						    	</div>
							</li>
							<c:if test="${pageBean.totalPages == 0 }">
								<li class="list-group-item list-group-item-default">
								    <h4 class="parkInfo-name">
								    	未找该地区到符合条件的停车场
								    </h4>
								    <p class="parkInfo-desc">系统测试阶段，请尝试查找沈阳范围内的停车场。</p>
								</li>
							
							</c:if>
						
							<c:forEach items="${pageBean.dataList }" var="park" varStatus="status">
								<c:if test="${park.remainCount > park.workingCount / 4 }">
									<li class="list-group-item list-group-item-success" onmouseenter="focusMap('${status.index + 1}')"
										onclick="window.open('http://localhost:8086/?parkId=${park.id }')">
								</c:if>
								<c:if test="${park.remainCount <= park.workingCount / 4 }">
									<li class="list-group-item list-group-item-danger" onmouseenter="focusMap('${status.index + 1}')"
										onclick="window.open('http://localhost:8086/?parkId=${park.id }')">
								</c:if>
								    <h4 class="parkInfo-name">${status.index + 1 }.${park.name }
								    	<span class="parkInfo-size">(剩余${park.remainCount }个车位，共${park.workingCount }个)</span>
								    	<span class="parkInfo-type">
									    	<c:if test="${park.type == 0 }">室外</c:if>
									    	<c:if test="${park.type == 1 }">室内</c:if>
									    	<c:if test="${park.type == 2 }">立体</c:if>
									    	/自助停车/
									    	<c:if test="${park.price == '0.00' }">免费</c:if>
									    	<c:if test="${park.price != '0.00' }">${park.price }元/小时</c:if>
								    	</span>
								    </h4>
								    <p class="parkInfo-desc">${park.desc }</p>
								</li>
							</c:forEach>
							
							<!-- 停车场信息表分页 -->
							<ul style="padding-left: 0px">
								<c:if test="${pageBean.totalPages <= 1}">
							    <li class="pager-next pager-disabled"><a href="JavaScript:void(0)">下一页 <i class="fa fa-chevron-circle-right"></i></a></li>
								<li class="pager-total"><span style="color: #337ab7">${pageBean.currentPage }/${pageBean.totalPages }</span></li>
							    <li class="pager-previous pager-disabled"><a href="JavaScript:void(0)"><i class="fa fa-chevron-circle-left"></i>上一页</a></li>
							    </c:if>
								<c:if test="${pageBean.totalPages > 1}">
									<c:if test="${pageBean.currentPage == 1}">
								    <li class="pager-next"><a href="JavaScript:void(0)" onclick="changeURL('currentPage','${pageBean.currentPage + 1 }')">下一页 <i class="fa fa-chevron-circle-right"></i></a></li>
									<li class="pager-total"><span style="color: #337ab7">${pageBean.currentPage }/${pageBean.totalPages }</span></li>
								    <li class="pager-previous pager-disabled"><a href="JavaScript:void(0)"><i class="fa fa-chevron-circle-left"></i>上一页</a></li>
								    </c:if>
								    
								    <c:if test="${pageBean.currentPage != 1 }">
									    <c:if test="${pageBean.currentPage != pageBean.totalPages }">
									    	<li class="pager-next"><a href="JavaScript:void(0)" onclick="changeURL('currentPage','${pageBean.currentPage + 1 }')" >下一页 <i class="fa fa-chevron-circle-right"></i></a></li>
											<li class="pager-total"><span style="color: #337ab7">${pageBean.currentPage }/${pageBean.totalPages }</span></li>
										    <li class="pager-previous"><a href="JavaScript:void(0)" onclick="changeURL('currentPage','${pageBean.currentPage - 1 }')" ><i class="fa fa-chevron-circle-left"></i>上一页</a></li>
									    </c:if>
									    <c:if test="${pageBean.currentPage == pageBean.totalPages }">
									    	<li class="pager-next pager-disabled"><a href="JavaScript:void(0)">下一页 <i class="fa fa-chevron-circle-right"></i></a></li>
											<li class="pager-total"><span style="color: #337ab7">${pageBean.currentPage }/${pageBean.totalPages }</span></li>
										    <li class="pager-previous"><a  href="JavaScript:void(0)" onclick="changeURL('currentPage','${pageBean.currentPage - 1 }')"><i class="fa fa-chevron-circle-left"></i>上一页</a></li>
									    </c:if>
								    </c:if>
							    </c:if>
							</ul>
						</ul>		
					</div><!-- 停车场信息表结束 -->
			  	</div>
			  </li>
		 </ul>
	</div> <!-- 数据显示面板结束 -->
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

    <!-- JavaScript -->
    <script src="https://cdn.staticfile.org/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://static.antido.cn/js/jquery-migrate-1.2.1.min.js"></script>
    <script src="https://cdn.staticfile.org/jqueryui/1.10.4/jquery-ui.min.js"></script>
	<script src="http://static.antido.cn/js/jquery.corner.js"></script>
	<script src="https://cdn.staticfile.org/jquery-cookie/1.1/jquery.cookie.min.js"></script>
	
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
	
	<script src="http://static.antido.cn/js/antido/antido.map-search.js"></script>
	<script src="http://static.antido.cn/js/antido/antido.search-complete.js"></script>
	<!-- <script src="http://static.antido.cn/js/antido/antido.user.js"></script> -->
	<script src="${pageContext.request.contextPath}/js/antido.user.js"></script>
	<script src="http://static.antido.cn/js/antido/antido.search.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/getscript?v=2.0&ak=yI43GzysGKGcADFGUo6etBG6kMbmbO7I"></script>
	
	<script type="text/javascript">
		//初始化地图
		initMap(1.7);
		jQuery(function($) {
			$(document).ready(function() {
				//$('.navbar-default').stickUp();
				//根据URL中的参数回显过滤条件
				new FilterEcho().init();
				//checkUser();
				//获取用户cookie
			    UserUtils.getUserByCookie();
			  	//自动补全
				searchComplete();
				new WOW().init();
			});
		});
		
	</script>
	<c:forEach items="${pageBean.dataList }" var="park" varStatus="status">
		<script>
			//同步加载完毕后,获取request域中的park数据在地图中标记	
			$(document).ready(function() {
				mapUtil.showParkInMap("${park.east }","${park.north }","${park.name}","${status.index + 1}","${park.desc}","${park.id}");
			});
		</script>
	</c:forEach>
</body>
</html>
