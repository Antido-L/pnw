<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<jsp:include page="${pageContext.request.contextPath }/WEB-INF/jsp/common/head.jsp"></jsp:include>
	<style type="text/css">  
	</style>
	
	
</head>

<body>
	<section>
		<!-- 引入左侧面板 -->
		<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/common/leftpanel.jsp"></jsp:include>
		<!-- 中部区域 -->
		<div class="mainpanel">
			<!-- 引入头部面板 -->
			<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/common/headerbar.jsp"></jsp:include>
			<!-- 当前面板信息 -->
			<div class="pageheader">
		      <h2><i class="fa fa-home"></i> 主面板 <span>子标题</span></h2>
		      <div class="breadcrumb-wrapper">
		        <span class="label">当前位置:</span>
		        <ol class="breadcrumb">
		          <li><a href="index.html">Bracket</a></li>
		          <li class="active">主面板</li>
		        </ol>
		      </div>
		    </div>
		    <!-- 中部内容区域容器 -->
		    <div class="contentpanel">
			<div class="panel panel-default">
				<!-- panel-heading -->
				<div class="panel-heading">
					<h4 class="panel-title">Google Maps</h4>
					<!--<p><a href="http://hpneo.github.io/gmaps/" target="_blank">gmaps.js</a> allows you to use the potential of Google Maps in a simple way.</p>-->
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-md-9">
							<h5 class="subtitle">Basic Map</h5>
							<p>Some basic example of using google maps with latitude and
								longitude defined.</p>
								<div id="map-container" class="antido-baiduMap">
									 <div id="baiduMap"></div>
									 <div class="antido-mapSearch">
										<div class="input-group mb15">
								     	<input id="mapSearch" type="text" class="form-control" placeholder="位置搜索...">
								     	<span  style="cursor: pointer;" class="input-group-addon">
								     		<span class="glyphicon  glyphicon-search"></span>
							     		 </span>
								     	</div>
								     </div>
								</div>
						</div>
						<!-- col-md-6 -->
						<div class="col-md-3">
							<h5 class="subtitle">地图信息</h5>
							<p>Some example of using google maps with markers enabled</p>
							<!-- <div id="gmap-marker" style="height: 300px"></div> -->
							<button type="button" class="btn btn-danger" onclick="makeParkMarker()">将停车场标记移动到地图中心</button>
						</div>
						<!-- col-md-6 -->
					</div><!-- row结束 -->
				</div><!-- panel-body结束 -->
			</div><!-- panel结束 -->
		</div>
	    </div>
	   
	</section>
	<%-- <jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/common/jsimport.jsp"></jsp:include> --%>
	<script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-ui-1.10.3.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/modernizr.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.sparkline.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/toggles.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.cookies.js"></script>
	<script src="${pageContext.request.contextPath}/js/retina.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/custom.js"></script>
	
	<%-- <script src="${pageContext.request.contextPath}/js/flot/jquery.flot.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/flot/jquery.flot.resize.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/flot/jquery.flot.spline.min.js"></script> --%>
	<%-- <script src="${pageContext.request.contextPath}/js/morris.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/raphael-2.1.0.min.js"></script> --%>
	<%-- <script src="${pageContext.request.contextPath}/js/dashboard.js"></script> --%>
	
	<script src="${pageContext.request.contextPath}/js/antido.search.js"></script>
	<script src="${pageContext.request.contextPath}/js/antido.map.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/getscript?v=2.0&ak=yI43GzysGKGcADFGUo6etBG6kMbmbO7I"></script>
	
	<script>
		$().ready(function() {
			
			/* $.ajax({
				url:"http://api.map.baidu.com/place/v2/suggestion",
				data:{
					"query":"工大",
					"region":"沈阳",
					"city_limit":false, 
					"output":"json",
					//"scope":"2",
					"ak":"yI43GzysGKGcADFGUo6etBG6kMbmbO7I",
					},
				dataType:"jsonp",
				type:"GET",
				success:function(data) {
					console.log(data);
				}
			}); */
			
		});
		
		
		
	</script>
	<script>
		
		/* //初始化实例
		var map = new BMap.Map(".map-container");
		//初始化中心坐标点
		var point = new BMap.Point(116.404, 39.915);
		//初始化
		map.centerAndZoom(point, 15); */
		
		$().ready(function() {
			searchComplete("${pageContext.request.contextPath}");
			initMap(0.8);//创建和初始化地图
			/* $(window).resize(function() {
				$("#baiduMap").height($("#baiduMap").width());
			}); */
			
			/* 
			//正方形地图
			$("#baiduMap").height($("#baiduMap").width());
			//初始化实例
			var map = new BMap.Map("baiduMap");
			//初始化中心坐标点
			var point = new BMap.Point(116.404, 39.915);
			//初始化
			map.centerAndZoom(point, 15);
			//新增比例尺控件
			map.addControl(new BMap.ScaleControl());
			map.addControl(new BMap.MapTypeControl());
			//map.setCurrentCity("北京");
			//map.panTo(new BMap.Point(116.409, 39.918)); //移动中心点
			//开启鼠标滚动缩放
			map.enableScrollWheelZoom(true);
			
			baiduMapSearch(); 
			*/
			/* var marker = new BMap.Marker(point);        // 创建标注    
			map.addOverlay(marker); 
			
			marker.addEventListener("click", function(){    //标注单机事件
				
			});  
			
			marker.enableDragging();   //标注可拖拽 
			marker.addEventListener("dragend", function(e){    
			    alert("当前位置：" + e.point.lng + ", " + e.point.lat);    
			})   
			
			var polyline = new BMap.Polyline([
			        new BMap.Point(116.399, 39.910),
			        new BMap.Point(116.405, 39.920)
	       		],
	        	{strokeColor:"blue", strokeWeight:6, strokeOpacity:0.5}
	        );
		   map.addOverlay(polyline);
			map.addEventListener("click", function(e){    
			    alert(e.point.lng + ", " + e.point.lat);    
			});
			
			     
			var local = new BMap.LocalSearch(map, {      
			    renderOptions:{map: map}      
			});      
			local.search("天安门"); */
		});
		
		//添加停车场位置选择标注
		function makeParkMarker() {
			//清除所有覆盖物
			map.clearOverlays();
			//获取当前中心点
			var point = map.getCenter();
			console.log("中心：" + point.lng + ", " + point.lat);
			var markerArr = [
			     {title:"停车场位置",content:"将停车场的位置设置到此处",point:point.lng+"|"+point.lat,isOpen:0,icon:{w:18,h:27,l:36,t:22,x:1,lb:10}}
			];
			//放置坐标
			var markerList = addMarker(markerArr);
			//为坐标添加拖动事件
			markerList[0].enableDragging();
			//监听标记的落点
			markerList[0].addEventListener("dragend", function(e){    
			    console.log("当前位置：" + e.point.lng + ", " + e.point.lat);    
			})
		}
		
		/* //地图响应窗口缩放
		$(window).resize(function() {
			$("#baiduMap").height($("#baiduMap").width());
		}); */
		
		
		
		
		/***********************************************/

   		

		/***********************************************/
		
	</script>
</body>
</html>