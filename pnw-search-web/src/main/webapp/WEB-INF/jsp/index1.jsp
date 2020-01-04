<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href="${pageContext.request.contextPath}/css/jquery-ui.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/antido/mod.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<h1>HELLO,WORLD!</h1>
	<input id="seachInput" type="text" class="form-control input-lg" name="keyword" autocomplete="off" placeholder="Search here..." />
	
	
	<div class="mod-space dragscroll"></div>
	<input type="hidden" id = "parkId">
	
	
	
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
	
	
	<!-- SPACE双击弹出 -->
	<div class="modal fade bs-example-modal-sm" id="spaceModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog modal-sm" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="space-model-title">Modal title</h4>
	      </div>
	      <div class="modal-body" id="">
           	<button id="onBtn" type="button" class="btn btn-danger">灯亮!</button>
           	<button id="offBtn" type="button" class="btn btn-success">灯灭!</button>
	      </div>
	      <input type="hidden" id="space-model-id">
	      <input type="hidden" id="space-model-nodeId">
	      <input type="hidden" id="space-model-parentId">
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-ui-1.10.3.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.mousewheel.js"></script>
	
	
	<script src="${pageContext.request.contextPath}/js/antido.color.js"></script>
	<script src="${pageContext.request.contextPath}/js/dragscroll.js"></script>
	<script src="${pageContext.request.contextPath}/js/antido.search.js"></script>
	<script src="${pageContext.request.contextPath}/js/antido.mod.js"></script>
	<script>
		$().ready(function() {
			searchComplete("${pageContext.request.contextPath}");
		});
		
		/*搜索框自动补全*/
		function searchComplete (contextPath) {
			//搜索框自动补全
			$( "#seachInput" ).autocomplete({
				minLength: 1, //最小搜索字符数
				delay:300,
				//数据源
				source: function(request,response) { 
				//request.term里是输入的数据
				//reponse是tm个回调函数是个方法  参数就是数据
				$.ajax({
					url:contextPath+"/search/keywordQuery",
					data:{"keyword":request.term},
					dataType:"json",
					type:"POST",
					success:function(data) {
						if(data.length > 0) {
			  				response(data);
						} 
					}
				});
			  },
			      
				//当光标移动到某个选项时
				focus: function( event, ui ) {
					var name = ui.item.name.replace("<span style='color:red'>","");
					name = name.replace("</span>","");
					$("#seachInput" ).val(name);
					return false;
				},
			      
			      //当一个选项被选中时
				select: function( event, ui ) {
					//$( "#seachInput" ).val( ui.item.name );
					
					//加载模拟图数据						
					loadMod(ui.item.id);
					return false;
				},
			      
			})

			.data( "ui-autocomplete" )._renderItem = function( ul, item ) {
			  	return $( "<li>" )
			    .append( "<a>"+item.name+"("+item.cityName+")"+"<br>"+"<span style='color:#999;font-size: 10px'>"+item.desc+"</span></a>" )
			    .appendTo( ul );
			};    
		}
		
		
		/*加载模型图*/		
		function loadMod (parkId) {
		    $("#parkId").val(parkId);
			// 加载模型数据
			$.ajax({
				url:"${pageContext.request.contextPath}/mod/getParkMapByParkId",
	  			type:"POST",
	  			data:{"parkId":parkId},
	  			dataType:"json",
	  			success:function(data) { //data:当前停车场内mod数据
	  				console.log(data);
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
				url:"${pageContext.request.contextPath}/mod/allSpaceByParkId",
	  			type:"POST",
	  			data:{"parkId":parkId},
	  			dataType:"json",
	  			cache:false,
	  			success:function(data) { //包含当前停车场内车位列表
	  				//console.log(data);
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
			
		} //loadMod结束
		
		
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
			var parkId = $("#parkId").val();
			//console.log(spaceId + " " + parkId);
			//return ;
			
			
			if(spaceId != undefined) {
				//异步请求model数据
		  		$.ajax({
		  			url:"${pageContext.request.contextPath}/space/modelJsonBySpaceId",
		  			data:{"spaceId":spaceId},
		  			dataType:"json",
		  			type:"POST",
		  			success:function(data){
		  				console.log(data);
		  				if(data == null) return ;
		  				
		  				if(data.space.running_state != 1) {
		  					$("#alert-model-title").css({color:"red"}).text("异常");
		  					$('#alertModel').modal();
		  					return ;
		  					
		  				} 
		  				$("#space-model-id").val(spaceId);
		  				$("#space-model-nodeId").val(data.space.node.id);
		  				$("#space-model-parentId").val(data.space.node.parent_id);
		  				$('#spaceModel').modal();
		  				return;
		  				/*********************************/ 
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
		
		
		
		$("#onBtn").click(function(){
			var parkId = $("#parkId").val();
			var spaceId = $("#space-model-id").val();
			var nodeId = $("#space-model-nodeId").val();
			var parent_id = $("#space-model-parentId").val();
			
			$.ajax({
	  			url:"${pageContext.request.contextPath}/space/lockOn",
	  			data:{"spaceId":spaceId,"nodeId":nodeId,"parent_id":parent_id},
	  			dataType:"json",
	  			type:"POST",
	  			success:function(data){
	  				console.log(data);
	  			}
			});
			
		});
		
		$("#offBtn").click(function(){
			var parkId = $("#parkId").val();
			var spaceId = $("#space-model-id").val();
			var nodeId = $("#space-model-nodeId").val();
			var parent_id = $("#space-model-parentId").val();
			$.ajax({
	  			url:"${pageContext.request.contextPath}/space/lockOff",
	  			data:{"spaceId":spaceId,"nodeId":nodeId,"parent_id":parent_id},
	  			dataType:"json",
	  			type:"POST",
	  			success:function(data){
	  				console.log(data);
	  			}
			});
		});
			
		
	</script>
</body>
</html>