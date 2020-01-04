<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<jsp:include page="${pageContext.request.contextPath }/WEB-INF/jsp/common/head.jsp"></jsp:include>
	<link href="${pageContext.request.contextPath}/css/antido/mod.css" rel="stylesheet">
</head>
<body>
	<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/common/loading.jsp"></jsp:include>
	<section>
		<!-- 引入左侧面板 -->
		<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/common/leftpanel.jsp"></jsp:include>
		<!-- 中部区域 -->
		<div class="mainpanel">
			<!-- 引入头部面板 -->
			<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/common/headerbar.jsp"></jsp:include>
			<!-- 当前面板信息 -->
			<div class="pageheader">
		      <h2><i class="fa fa-home"></i> 停车场 <span>停车场模拟图</span></h2>
		      <div class="breadcrumb-wrapper">
		        <span class="label">当前位置:</span>
		        <ol class="breadcrumb">
		          <li><a href="index.html">Bracket</a></li>
		          <li class="active">停车场</li>
		          <li class="active">停车场模拟图</li>
		        </ol>
		      </div>
		    </div>
		    <!-- 中部内容区域 -->
		    <div class="contentpanel">
	          <div class="panel panel-default">
		        <div class="panel-heading">
		          <div class="panel-btns">
		            <a href="" class="panel-close">&times;</a>
		            <a href="" class="minimize">&minus;</a>
		          </div>
		          <h4 class="panel-title">停车场车位模拟图</h4>
		          <p><code>双击车位槽</code>将会展示车位信息，管理员可以可以在此查询或更改车位工作状态，所有强制操作都会影响用户使用，务必谨慎操作。如需改变车位布局，需将该停车场下线，并保证所用车位都未被使用。</p>
		          <p>点击<code>停车位管理说明书</code>获取详细说明</p>
		        </div>
			</div><!-- panel结束 -->
            	 <div class="row" style="margin-top: 10px">
	            	<div class="col-sm-9">
	            	 <!-- 结构图panel -->
			         <div class="panel panel-default">
			         	<div class="panel-heading">
			              <div class="panel-btns">
			                <a href="" class="panel-close">&times;</a>
			                <a href="" class="minimize">&minus;</a>
			              </div><!-- panel-btns -->
			              <h3 class="panel-title">${park.name }--<a style="color:#428bca" href="${pageContext.request.contextPath }/space/list?park_id=${park.id }">切换至车位信息表</a></h3>
			            </div>
			            <div class="panel-body">
				          <!-- <h3 style="margin-top: 5px; text-align: center;">沈阳工业大学信息院停车场</h3> -->
			              <!--  -->
						  <div class="mod-space dragscroll"></div>		                  
		                </div><!-- <!-- 结构图 结束 -->
			         </div> <!-- 结构图panel-body结束 -->
		            </div><!-- col-sm-9结束 -->
		            <div class="col-sm-3">
		            	<div class="panel panel-default">
			            	<div class="panel-heading">
				              <div class="panel-btns">
				                <a href="" class="panel-close">&times;</a>
				                <a href="" class="minimize">&minus;</a>
				              </div><!-- panel-btns -->
				              <h3 class="panel-title">车场信息</h3>
				            </div>
				            <div class="panel-body">
				                  <!-- <p class="mb15">铁西区沈辽西路111号，沈阳工业大学中央小区。东门进入右转信息院楼前</p> -->
				                 <p class="mb15">控制器地址：${park.serviceIpStr }</p>
				                 <input type="hidden" id = "parentNodeId" value="${park.node.id }"/>
				                 <input type="hidden" id = "parkId" value="${park.id }"/>
				                 
								<div class="col-sm-12" style="padding-left: 0;padding-bottom: 10px">
				                  	<div class="col-sm-4 " style="padding-left: 0">
				                  		<img style="width: 50px;height: 80%" src="${pageContext.request.contextPath }/images/car-unable-x.png">
				                  	</div>
				                  	<h5 class=" col-sm-8">不可使用</h5>
				                  </div>
				                 
				                  
				                  <div class="col-sm-12" style="padding-left: 0 ;padding-bottom: 10px">
				                  	<div class="col-sm-4 " style="padding-left: 0">
				                  		<img style="width: 50px;height: 80%" src="${pageContext.request.contextPath }/images/car-empty-x.png">
				                  	</div>
				                  	<h5 class=" col-sm-8">空闲车位</h5>
				                  </div>
				                  
				                  <div class="col-sm-12" style="padding-left: 0 ;padding-bottom: 10px">
				                  	<div class="col-sm-4 " style="padding-left: 0">
				                  		<img style="width: 50px;height: 80%" src="${pageContext.request.contextPath }/images/car-error-x2.png">
				                  	</div>
				                  	<h5 class=" col-sm-8">工作异常</h5>
				                  </div>
				                  
				                  <div class="col-sm-12" style="padding-left: 0 ;padding-bottom: 10px">
				                  	<div class="col-sm-4 " style="padding-left: 0">
				                  		<img style="width: 50px;height: 80%" src="${pageContext.request.contextPath }/images/car-success-x2.png">
				                  	</div>
				                  	<h5 class=" col-sm-8">使用中</h5>
				                  </div>
				                  
				                  <div class="col-sm-12" style="padding-left: 0 ;padding-bottom: 10px">
				                  	<div class="col-sm-4 " style="padding-left: 0">
				                  		<img style="width: 50px;height: 80%" src="${pageContext.request.contextPath }/images/car-warning-x.png">
				                  	</div>
				                  	<h5 class=" col-sm-8">预约中</h5>
				                  </div>
					
										
				                  <span class="sublabel">上线车位
				                  	(<span class="num-carItemCount"></span>
				                  	/<span class="num-designCount">${park.design_count }</span>)
			                  	  </span>
				                  <div class="progress progress-sm">
				                    <div id="totalProgress" style="width: 0%" aria-valuemax="100" aria-valuemin="0" aria-valuenow="40" role="progressbar" class="progress-bar progress-bar-primary"></div>
				                  </div><!-- progress -->
				
				                  <span class="sublabel">连接正常
				                  	(<span class="num-spaceCount"></span>
				                  	/<span class="num-carItemCount"></span>)
				                  </span>
				                  <div class="progress progress-sm">
				                    <div id="usingProgress" style="width: 0%" aria-valuemax="100" aria-valuemin="0" aria-valuenow="40" role="progressbar" class="progress-bar progress-bar-success"></div>
				                  </div><!-- progress -->
				
				                  <span class="sublabel">已被使用
				                  	(<span class="num-spaceWorkCount"></span>
				                  	/<span class="num-spaceCount"></span>)
				                  </span>
				                  <div class="progress progress-sm">
				                    <div id="onlineProgress" style="width: 0%" aria-valuemax="100" aria-valuemin="0" aria-valuenow="40" role="progressbar" class="progress-bar progress-bar-danger"></div>
				                  </div><!-- progress -->
				                  
				                  <!-- <span class="sublabel">未分配的车位</span> -->
					              <button style="margin: 15px auto;" class="col-sm-12 btn btn-primary btn-sm" id="submitBtn">刷新状态</button>
					              
				                  <button style="margin: 0 auto;" class="col-sm-12 btn btn-danger btn-sm" id="submitBtn">显示地图位置</button>
				                 
				            </div>
				          </div><!-- 模型图信息panel结束 -->
		            </div><!-- col-sm-3结束 -->
			       </div><!-- row -->
			    
   		</div><!-- 中部内容区域结束 --> 
	</section>
	
	<!-- ALERT -->
	<div class="modal fade bs-example-modal-sm" id="alertModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog modal-sm" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="alert-model-title">Modal title</h4>
	      </div>
	      <div class="modal-body" id="alert-model-body">
           	
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	
	<!-- 停车位信息模态框(双击item弹出) -->
	<div id="space-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 id="space-modal-title" class="modal-title" id="myModalLabel">Modal title</h4>
	      </div>
	      <div class="modal-body">
	      	<div>
	      		<h4>运行状态：<span id="model-running"></span></h4>
	      		<ul>
	      			<li>
	      				节点编号：<a id="model-nodeId-url" href="#"></a>
	      			</li>
	      			<li>
	      				车位锁：<span id="model-nodeIsLock"></span>
	      			</li>
	      			<li>
	      				接近开关：<span id="model-nodeIsClose"></span>
   					</li>
	      			<li>
	      				累计服役：<span id="model-countTime"></span>
	      			</li>
	      			<li>
	      				创建时间：<span id="model-created"></span>
      				</li>
	      			<li>
	      				上次修改时间：<span id="model-updated"></span>
      				</li>
	      			<li>
	      				备注：<span id="model-remark"></span>
      				</li>
	      			<li>
	      				<a id="model-fix-url" href="#">维修记录</a>
      				</li>
	      		</ul>
	      		<hr>
	      		<h4>使用状态：<span id="model-using"></span></h4>
	      		<ul id="model-user-info">
	      			<li>
	      				订单编号：<a id="model-orderId-url" href="#"></a>
      				</li>
	      			<li>
	      				当前用户：<span id="model-userName-url"></span>
	      			</li>
	      			<li>
	      				车牌号：<span id="model-carLicense"></span>
      				</li>
	      			<li>
	      				停靠车辆：<span id="model-carName"></span>
   					</li>
	      			<li>
	      				停靠时间：<span id="model-parked"></span>
      				</li>
	      			<li>
	      				本次停靠：<span id="model-parkedTime"></span>
      				</li>
	      			<li>
	      				预定离开时间：<span id="model-leavingStr"></span>
	      			</li>
	      			<li>
	      				当前结算：<span id="model-currentPrice"></span>
	      			</li>
	      		</ul>
	      	</div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary">查看统计数据</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      </div>
	    </div>
	  </div>
	</div> <!-- 停车位信息模态框 结束 -->
	
	<script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
	<!-- <script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"> -->
	<script src="${pageContext.request.contextPath}/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-ui-1.10.3.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/modernizr.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.sparkline.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/toggles.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/retina.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.cookies.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.maskedinput.min.js"></script>
	
	<script src="${pageContext.request.contextPath}/js/antido.js"></script>
	<script src="${pageContext.request.contextPath}/js/antido.mod.js"></script>
	<script src="${pageContext.request.contextPath}/js/antido.color.js"></script>
	<script src="${pageContext.request.contextPath}/js/custom.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.mousewheel.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.form.js"></script>
	<script src="${pageContext.request.contextPath}/js/messages_zh.min.js"></script>	
	<script src="${pageContext.request.contextPath}/js/dragscroll.js"></script>
	
	<script src="${pageContext.request.contextPath}/js/antido.search.js"></script>
	
	<script>
		$().ready(function() {
			searchComplete("${pageContext.request.contextPath}");
		});
	</script>
	
	<script>
		/*页面加载完毕*/		
		$().ready(function() {
		    var parkId = $("#parkId").val();
			// 加载模型数据
			$.ajax({
				url:"${pageContext.request.contextPath}/park/getParkMapByParkId",
	  			type:"POST",
	  			data:{"parkId":parkId},
	  			dataType:"json",
	  			success:function(data) { //data:当前停车场内mod数据
	  				if(data == null) {
	  					return;
	  				}
	  				//回显模型图参数
	  				//$("#modRow").val(data.row);
	  				//$("#modCol").val(data.col);
	  				//$("#modDirect").val(data.direction);
	  				
	  				$(".mod-space").removeClass("hidden");
					$(".mod-space").empty();
					if(data.direction == '0') {
						modMaker(100,60,data.row,data.col,10);
					} else{
						modMaker(60,100,data.row,data.col,10);
					}
					var carItemCount = 0;
					for(var i = 0 ; i < data.items.length ; i++) {
						if(data.items[i].state != 2) carItemCount++;
					}
					$(".num-carItemCount").text(carItemCount);
					
					itemMaker(data.items, data.direction);
	  			}
			});
			
			//加载车位情况
			$.ajax({
				url:"${pageContext.request.contextPath}/space/allSpaceByParkId",
	  			type:"POST",
	  			data:{"parkId":parkId},
	  			dataType:"JSON",
	  			cache:false,
	  			success:function(data) { //包含当前停车场内车位列表
	  				console.log(data);
	  				var onlineSize = 0;
	  				var isUsingSize = 0;
	  				for(var i = 0 ; i < data.length ; i++) {
	  					if (data[i].node != null && data[i].node.is_online) { //有节点,切节点在线无异常
  							onlineSize++;
	  						if(data[i].using_state == '1') { //车位正在被使用
	  							$("#item-"+data[i].x_axis+"-"+data[i].y_axis).find("div[class='mod-item-empty']").attr("class","mod-item-success");
	  							isUsingSize++;
	  						} else if(data[i].using_state == '2') { //车位被预约
	  							$("#item-"+data[i].x_axis+"-"+data[i].y_axis).find("div[class='mod-item-empty']").attr("class","mod-item-warning");
	  							isUsingSize++;
	  						}
	  					} else { //节点异常
  							$("#item-"+data[i].x_axis+"-"+data[i].y_axis).find("div[class = 'mod-item-empty']").attr("class","mod-item-danger");
	  					}
	  					//在每一个有车的的节点上添加spaceId
	  					//在编辑车位是需要spaceId来判断是新增操作还是更新操作
	  					$("#item-"+data[i].x_axis+"-"+data[i].y_axis).attr("spaceId",data[i].idStr);
	  				}
	  				$(".num-spaceCount").text(onlineSize);
	  				$(".num-spaceWorkCount").text(isUsingSize);
	  			}
			});
			//计算百分比
			//因为数据是异步加载，所以延时一下
			//如果calculate加了括号，在调用时就会被执行
			setTimeout(calculate,2000); //2s后
			
		}); //页面加载完毕结束
		
		
		/*计算progress的百分比*/
		function calculate() {
			var designCount = $(".num-designCount").get(0).innerText;
			var carItemCount = $(".num-carItemCount").get(0).innerText;
			var spaceCount = $(".num-spaceCount").get(0).innerText;
			var spaceWorkCount = $(".num-spaceWorkCount").get(0).innerText;
			
			//动态类型就是厉害
			$("#totalProgress").css({"width":parseInt(carItemCount / designCount * 100) + "%"})
			$("#usingProgress").css({"width":parseInt(spaceCount / carItemCount  * 100) + "%"})
			$("#onlineProgress").css({"width":parseInt(spaceWorkCount / spaceCount * 100) + "%"})
		}
		
		/*绘制模型项*/
		function itemMaker(items, direction) {
			for(var i = 0 ; i < items.length ; i++) {
				var $div = $("<div class='mod-item-empty'></div>");
				if(items[i].state == '1') {  //空槽
					if(direction == '0') {
						$div.append($("<img class='mod-item-img' src='${pageContext.request.contextPath}/images/car-empty-x.png'/>"));
					} else {
						$div.append($("<img class='mod-item-img' src='${pageContext.request.contextPath}/images/car-empty-y.png'/>"));
					}
					$("#item-"+items[i].xAxis+"-"+items[i].yAxis).append($div);
					$("#item-"+items[i].xAxis+"-"+items[i].yAxis).attr("itemtype","1");
					
				} else if (items[i].state == '2') { //静止使用
					if(direction == '0') {
						$div.append($("<img class='mod-item-img' src='${pageContext.request.contextPath}/images/car-unable-x.png'/>"));
					} else {
						$div.append($("<img class='mod-item-img' src='${pageContext.request.contextPath}/images/car-unable-y.png'/>"));
					}
					$("#item-"+items[i].xAxis+"-"+items[i].yAxis).append($div);
					$("#item-"+items[i].xAxis+"-"+items[i].yAxis).attr("itemtype","2");
				} 
			}
		};
		
		/*item双击事件*/
		function clickItem(x,y) {
			//空白双击无效
			if($("#item-"+x+"-"+y).children().length == 0) {
				return ;
			}
			var spaceId = $("#item-"+x+"-"+y).attr("spaceId");
			$("#space-modal-title").html("车位编号:"+spaceId);
			if(spaceId != undefined) {
				//异步请求model数据
		  		$.ajax({
		  			url:"${pageContext.request.contextPath}/space/modelJsonBySpaceId",
		  			data:{"spaceId":spaceId},
		  			dataType:"json",
		  			type:"POST",
		  			success:function(data){
		  				if(data == null) {
		  					return ;
		  				}
		  				if(data.space.running_state == 0) {
		  					$("#model-running").css({color:"red"}).text("废弃");
		  				} else if(data.space.running_state == 1) {
		  					$("#model-running").css({color:"green"}).text("正常使用");
		  				} else if(data.space.running_state == 2) {
		  					$("#model-running").css({color:"blue"}).text("维护中");
		  				} //running_state 结束
		  				
		  				if(data.space.node == null) {
		  					$("#model-nodeId-url").attr("href","#");
		  					$("#model-nodeId-url").text("暂无节点");
		  					$("#model-nodeIsLock").text("暂无节点");
		  					$("#model-nodeIsClose").text("暂无节点");
		  				} else {
		  					$("#model-nodeId-url").attr("href","${pageContext.request.contextPath}/node/"+data.space.node.id);
		  					$("#model-nodeId-url").text(data.space.node.id);
		  					if(data.space.node.is_lock == 0) {
			  					$("#model-nodeIsLock").text("未锁");
		  					} else {
		  						$("#model-nodeIsLock").text("已锁");
		  					}
		  					
		  					if(data.space.node.is_lock == 0) {
			  					$("#model-nodeIsClose").text("无车");
		  					} else {
		  						$("#model-nodeIsClose").text("有车");
		  					}
		  				} // node 结束
		  				
	  					if(data.space.using_state == 0) {
	  						$("#model-using").css({color:"green"}).text("空闲");
	  					} else if(data.space.using_state == 1) {
	  						$("#model-using").css({color:"blue"}).text("正在使用");
	  					} else if(data.space.using_state == 2) {
	  						$("#model-using").css({color:"red"}).text("预约中");
	  					}
	  					
	  					if(data.space.user != null && data.space.order != null) {
	  						$("#model-user-info").removeClass("hidden");
	  						
	  						$("#model-orderId-url").attr("href","${pageContext.request.contextPath}/order/"+data.space.order.id);
	  						$("#model-orderId-url").text(data.space.order.id);
	  						$("#model-userName-url").attr("href","${pageContext.request.contextPath}/user/"+data.space.user.id);
	  						$("#model-userName-url").text(data.space.user.nice_name);
	  						
	  						$("#model-carLicense").text(data.space.user.car.license);
	  						$("#model-carName").text(data.space.user.car.name);
	  						$("#model-parked").text(data.parkedStr);
	  						$("#model-parkedTime").text(data.parkedTimeStr);
	  						$("#model-leavingStr").text(data.leavingStr);
	  						$("#model-currentPrice").text(data.currentPrice);
	  					} else {
	  						$("#model-user-info").addClass("hidden");
	  					} //user&order 结束
	  					
		  				$("#model-created").text(data.createdStr);
	  					$("#model-updated").text(data.updatedStr);
	  					$("#model-remark").text(data.space.remark);
	  					//$("#model-fix-url").append();
		  			}
		  		});
		  		//弹出
	  			$('#space-modal').modal();
			}
			
		}; //item双击事件结束
	</script>
</body>
</html>