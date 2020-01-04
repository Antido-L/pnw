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
		      <h2><i class="fa fa-home"></i> 停车场 <span>编辑车位信息</span></h2>
		      <div class="breadcrumb-wrapper">
		        <span class="label">当前位置:</span>
		        <ol class="breadcrumb">
		          <li><a href="index.html">Bracket</a></li>
		          <li class="active">停车场</li>
		          <li class="active">编辑车位</li>
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
		          <h4 class="panel-title">编辑车位信息</h4>
		          <p><code>双击车位槽</code>将会显示该车位槽的车位信息，可以更改每个车位的节点绑定状态，该操作只针对在本停车场控制器可以搜索到的节点。如需改变车位布局，请前往<code>编辑停车场信息。</code></p>
		          <p>点击<code>新增停车场流程</code>获取详细说明</p>
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
			              <h3 class="panel-title">${park.name }</h3>
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
				                  	<h5 class=" col-sm-8">可部署</h5>
				                  </div>
				                  
				                  <div class="col-sm-12" style="padding-left: 0 ;padding-bottom: 10px">
				                  	<div class="col-sm-4 " style="padding-left: 0">
				                  		<img style="width: 50px;height: 80%" src="${pageContext.request.contextPath }/images/car-error-x2.png">
				                  	</div>
				                  	<h5 class=" col-sm-8">连接异常</h5>
				                  </div>
				                  
				                  <div class="col-sm-12" style="padding-left: 0 ;padding-bottom: 10px">
				                  	<div class="col-sm-4 " style="padding-left: 0">
				                  		<img style="width: 50px;height: 80%" src="${pageContext.request.contextPath }/images/car-success-x2.png">
				                  	</div>
				                  	<h5 class=" col-sm-8">连接正常</h5>
				                  </div>
				                  
				                  <div class="col-sm-12" style="padding-left: 0 ;padding-bottom: 10px">
				                  	<div class="col-sm-4 " style="padding-left: 0">
				                  		<img style="width: 50px;height: 80%" src="${pageContext.request.contextPath }/images/car-warning-x.png">
				                  	</div>
				                  	<h5 class=" col-sm-8">暂无节点</h5>
				                  </div>
					
										
				                  <span class="sublabel">可使用
				                  	(<span class="num-carItemCount"></span>
				                  	/<span class="num-designCount">${park.design_count }</span>)
			                  	  </span>
				                  <div class="progress progress-sm">
				                    <div id="totalProgress" style="width: 0%" aria-valuemax="100" aria-valuemin="0" aria-valuenow="40" role="progressbar" class="progress-bar progress-bar-primary"></div>
				                  </div><!-- progress -->
				
				                  <span class="sublabel">已部署
				                  	(<span class="num-spaceCount"></span>
				                  	/<span class="num-carItemCount"></span>)
				                  </span>
				                  <div class="progress progress-sm">
				                    <div id="usingProgress" style="width: 0%" aria-valuemax="100" aria-valuemin="0" aria-valuenow="40" role="progressbar" class="progress-bar progress-bar-success"></div>
				                  </div><!-- progress -->
				
				                  <span class="sublabel">正常连接
				                  	(<span class="num-spaceWorkCount"></span>
				                  	/<span class="num-spaceCount"></span>)
				                  </span>
				                  <div class="progress progress-sm">
				                    <div id="onlineProgress" style="width: 0%" aria-valuemax="100" aria-valuemin="0" aria-valuenow="40" role="progressbar" class="progress-bar progress-bar-danger"></div>
				                  </div><!-- progress -->
				                  
				                  <!-- <span class="sublabel">未分配的车位</span> -->
					              <button style="margin: 15px auto;" class="col-sm-12 btn btn-primary btn-sm" id="submitBtn">刷新状态</button>
					              
				                  <button style="margin: 0 auto;" class="col-sm-12 btn btn-danger btn-sm" id="submitBtn">重置所有</button>
				                 
				            </div>
				          </div><!-- 模型图信息panel结束 -->
		            </div><!-- col-sm-3结束 -->
			       </div><!-- row -->
			    
   		</div><!-- 中部内容区域结束 --> 
	</section>
	
	<!-- item双击弹出框 -->
	<div class="modal fade" id="itemModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="item-model-title">Modal title</h4>
	      </div>
	      <div class="modal-body" id="item-model-body">
	      <form id="spaceForm">
	      	<input type="hidden" name="x_axis" id="checkedItemX" value=''/>
	      	<input type="hidden" name="y_axis" id="checkedItemY" value=''/>
	      	<input type="hidden" name="id" id="model-spaceId" value=''/>
	      	<input type="hidden" name="park.id" id="model-parkId" value=''/>
	      	
	      	<div class="form-group">
		   	  	 <label style="text-align: right;" class="col-sm-3 control-label">车位编码<span class="asterisk">*</span></label>
                  <div class="col-sm-7">
                    <input type="text" name="code" id="item-model-code" class="form-control" value="" placeholder="请输入车位编码" required maxlength="12"/>
                  </div>
		   	  </div>
	      	
	      	<div class="form-group">
				<label style="text-align: right;" class="col-sm-3 control-label">停车位类型 <span class="asterisk">*</span></label>
				<div class="col-sm-6">
					<div class="rdio rdio-success">
	                  	<input type="radio" name="space_type" value="0" id="radioSpaceType0"/>
	                    <label for="radioSpaceType0">任意车型</label>
	              	</div>
	              	<div class="rdio rdio-success">
	                  	<input type="radio" name="space_type" value="1" id="radioSpaceType1" checked="checked"/>
	                    <label for="radioSpaceType1">仅小型车</label>
	              	</div>
	              	<div class="rdio rdio-success">
	                  	<input type="radio" name="space_type" value="2" id="radioSpaceType2"/>
	                    <label for="radioSpaceType2">仅中型车与小型车</label>
	              	</div>
	              	<div class="rdio rdio-success">
	                  	<input type="radio" name="space_type" value="3" id="radioSpaceType3"/>
	                    <label for="radioSpaceType3">仅大型车</label>
	              	</div>
              	</div>
           	</div>
           	
		      <div class="form-group">
				<label style="text-align: right;" class="col-sm-3 control-label">配置无线通信节点 <span class="asterisk">*</span></label>
				<div class="col-sm-6">
					<div class="rdio rdio-warning">
		                  <input type="radio" name="nodeRadio" value="0" id="nodeRadioNo" checked="checked"/>
		                  <label for="nodeRadioNo">暂不绑定节点（会释放当前节点）</label>
	              	</div>
		                   	
	             	<div class="rdio rdio-danger">
	                  <input type="radio" name="nodeRadio" value="1" id="nodeRadioYes" />
	                  <label for="nodeRadioYes">选择当前停车场内的空闲节点</label>
	              	</div>
	         	</div>
		   	 </div>
		   	 
		   	  <div id="nodeSelectSpace" class="form-group hidden">
		   	  	<label style="text-align: right;" class="col-sm-3 control-label">设备列表</span></label>	
		   	  	<div class="col-sm-7">
		   	  		<select id="nodeSelect" class="form-control" name="node.id">
	                 <option id="nullOption" value="">---请选择需要绑定的设备---</option>
	                 <option value="bf9ae3557de84e62b8acf99e8457cdfc">zigbee（bf9ae3557de84e62b8acf99e8457cdfc）</option>
	               </select>
		   	  	</div>
		   	  	<div style="margin-top: 10px; padding-left: 0" class="col-sm-2">
		   	  		<a href="#">识别<span class="fa fa-link"></span></a>
		   	  	</div>
		   	  	<!-- <button type="button" class="btn btn-default col-sm-1">关闭</button> -->
		   	  </div>
		   	  
		   	  <div class="form-group">
		   	  	 <label style="text-align: right;" class="col-sm-3 control-label">车位备注</span></label>
                  <div class="col-sm-7">
                    <input type="text" name="remark" id="item-model-remark" class="form-control" value="" placeholder="车位备注" maxlength="25"/>
                  </div>
		   	  </div>
		   	 
	      </div>
	      
         </form>
	      <div class="modal-footer">
	      	<button type="button" style="float: left" class="btn btn-danger" id="item-model-delete">删除此车位</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="button" class="btn btn-primary" id="item-model-submit">确认更改</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	
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
			/* //ajaxForm 参数
			var options = {
			   target: '#output',          //把服务器返回的内容放入id为output的元素中    
			   //beforeSubmit: showRequest,  //提交前的回调函数
			   success: function(data,state){ //提交后的回调函数
				   updateMapItem(data);
			   },      
			   //url: url,                 //默认是form的action， 如果申明，则会覆盖
			   type: "POST",               //默认是form的method（get or post），如果申明，则会覆盖
			   dataType: "json",           //html(默认), xml, script, json...接受服务端返回的类型
			   //clearForm: true,          //成功提交后，清除所有表单元素的值
			   resetForm: true,          //成功提交后，重置所有表单元素的值
			   //timeout: 3000               //限制请求的时间，当请求大于3秒后，跳出请求
			}
			
			//初始化表单校验
			var validator = $("#spaceForm").validate({
		    	submitHandler:function(form){
		    		//alert("submit");
		    		$('#spaceForm').ajaxSubmit(options); //提交表单
		    		$('#itemModel').modal('hide');
	        	}	
    		}); */
			
			//var reg = new RegExp("(^|&)park_id=([^&]*)(&|$)");
		    var parkId = $("#parkId").val();
		    
		 	//$("#model-parkId").val(parkId); //在所有model中放置隐藏parkId
			// 加载模型数据
			$.ajax({
				url:"${pageContext.request.contextPath}/park/getParkMapByParkId",
	  			type:"POST",
	  			data:{"parkId":parkId},
	  			dataType:"json",
	  			success:function(data) {
	  				if(data == null) {
	  					return;
	  				}
	  				//回显模型图参数
	  				$("#modRow").val(data.row);
	  				$("#modCol").val(data.col);
	  				$("#modDirect").val(data.direction);
	  				
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
	  				for(var i = 0 ; i < data.length ; i++) {
	  					if (data[i].node != null) { //有节点
	  						if(data[i].node.is_online == '1') { //节点已经连接
	  							$("#item-"+data[i].x_axis+"-"+data[i].y_axis).find("div[class='mod-item-empty']").attr("class","mod-item-success");
	  							onlineSize++;
	  						} else { //节点未连接
	  							$("#item-"+data[i].x_axis+"-"+data[i].y_axis).find("div[class='mod-item-empty']").attr("class","mod-item-danger");
	  						}
	  					} else { //无节点
  							$("#item-"+data[i].x_axis+"-"+data[i].y_axis).find("div[class = 'mod-item-empty']").attr("class","mod-item-warning");
	  					}
	  					//在每一个有车的的节点上添加spaceId
	  					//在编辑车位是需要spaceId来判断是新增操作还是更新操作
	  					$("#item-"+data[i].x_axis+"-"+data[i].y_axis).attr("spaceId",data[i].idStr);
	  				}
	  				$(".num-spaceCount").text(data.length);
	  				$(".num-spaceWorkCount").text(onlineSize);
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
			//恢复初始状态
			$("#item-model-code").val("");
			
			var itemType = $("#item-"+x+"-"+y).attr("itemType");
			if(itemType == '1') { 
				$("#item-model-title").text("为此车位槽添加车位");
			} else if (itemType == '2') { //禁止使用车位槽被点击，弹出提示框
				$("#alert-model-title").text("禁止使用");
				$("#alert-model-body").empty();
				var $msg =  $("<p>如需改变此车位槽的使用状态 ，请在<code>开发中的停车场内</code>选择编辑停车场信息&hellip;</p>");
				$("#alert-model-body").append($msg);
				$('#alertModel').modal({
					  keyboard: false
				})
				return ;
			}
			
			$("#checkedItemX").val(x);
			$("#checkedItemY").val(y);
			
			var spaceId = $("#item-"+x+"-"+y).attr("spaceId");
			if(spaceId != undefined) {
				//把item中的spaceId放入表单里
				$("#model-spaceId").val(spaceId);
			}
			
			var $select = $("#nodeSelect");	
			$select.empty();
			var parentId = $("#parentNodeId").val(); //停车场协调器id
			//加载当前停车场内的空余节点
			$.ajax({
				url:"${pageContext.request.contextPath}/node/getNodeByParentId",
	  			type:"POST",
	  			data:{"parentId":parentId},
	  			dataType:"json",
	  			success:function(data) {
	  				//date里的节点是当前控制器内未绑定车位的自由节点
					var $select = $("#nodeSelect");	
					$select.append($("<option id='nullOption' value=''>---请选择需要绑定的设备---</option>"))
	  				for(var i = 0 ; i < data.length ; i++) {
	  					var $option = $("<option value='"+data[i].id+"'>"+data[i].node_desc+"（"+data[i].id+"）</option>");
	  					$select.append($option);
	  				}
	  			}
			});
			
			var spaceId = $("#item-"+x+"-"+y).attr("spaceId");
			if(spaceId != undefined) {
				//回显当前Space信息
				$.ajax({
					url:"${pageContext.request.contextPath}/space/getSpaceById",
		  			type:"POST",
		  			data:{"spaceId":spaceId},
		  			dataType:"json",
		  			success:function(data) {
		  				$("#item-model-title").text("更新车位槽信息");
		  				$("#item-model-code").val(data.code);
		  				$("input:radio[name='space']").find("input:radio[value='"+data.space_type+"']").attr("selected","selected");
		  				if(data.node == null) { //未绑定节点
		  					$("#nodeRadioYes").removeAttr("checked");
		  					$("#nodeRadioNo").attr("checked","checked");
		  					$("#nodeSelectSpace").addClass("hidden");
		  				} else { //已绑定节点
		  					$("#nodeRadioNo").removeAttr("checked");
		  					$("#nodeRadioYes").attr("checked","checked");
		  					$("#nodeSelectSpace").removeClass("hidden");
		  					
		  					$("#nodeSelect").find("option:selected").removeAttr("selected");
		  					$("#nodeSelect").append("<option value='"+data.node.id+"' selected='selected'>当前节点（"+data.node.id+"）</option>");
		  					
		  				}
		  				
		  				$("#item-model-remark").val(data.remark);
		  			}
				});
				
				//把item中的spaceId放入表单里
				$("#model-spaceId").val(spaceId);
				
			}
			
			//显示模态框
			$('#itemModel').modal({
			  keyboard: false
			})
		}; //item双击事件结束
		
		/*切换设备选择下拉选显示状态*/
		$("input:radio[name='nodeRadio']").change(function (){ 
			var nodeNo = $("#nodeRadioNo").attr("checked");
			var nodeYes = $("#nodeRadioYes").attr("checked");
			
			if(nodeNo == "checked") {
				//$("#nodeSelect").empty();
				//$("#nodeSelect").append($("<option value=''>---请选择需要绑定的设备---</option>"));
				$("#nodeSelect").find("option:selected").removeAttr("selected");
				
				$("#nodeSelect").find("#nullOption").attr("selected","selected");
				$("#nodeSelectSpace").addClass("hidden");
			}
			if(nodeYes == "checked") {
				$("#nodeSelectSpace").removeClass("hidden");
			}
		});
		
		/*车位添加*/
		$("#item-model-submit").click(function() {
			if($("#model-spaceId").val() == '') { //为新增操作
				$('#spaceForm').attr("action","${pageContext.request.contextPath}/space/add");
				$('#spaceForm').submit();
			} else { //为更新操作
				$('#spaceForm').attr("action","${pageContext.request.contextPath}/space/update");
				$('#spaceForm').submit();
			}
		});
		
		/*车位删除*/
		$("#item-model-delete").click(function() {
			var spaceId = $("#model-spaceId").val();
			if(spaceId == '') { //当前位置未来被绑定 ， 显示提示
				return ;
			} else { //删除
				//alert("delete");
 				$.ajax({
					url:"${pageContext.request.contextPath}/space/delete",
		  			type:"POST",
		  			data:{"spaceId":spaceId},
		  			dataType:"json",
		  			success:function(data) {
		  				if(data == '1') {
		  					$("div[spaceId = "+spaceId+"]").children().attr("class","mod-item-empty");
		  				}
		  				$('#itemModel').modal('hide');
		  				$("div[spaceId = "+spaceId+"]").removeAttr("spaceId");
		  			}
				});  
			}
		});
		
		/*更新被操作的item显示状态
		  data：被更新的节点数据
		*/
		function updateMapItem(data) {
			if(data == null) {
				return;
			}
			if (data.node != null && data.node.id != "") { //有节点
				if(data.node.is_online == '1') { //节点已经连接
					$("#item-"+data.x_axis+"-"+data.y_axis+"> div").removeClass();
					$("#item-"+data.x_axis+"-"+data.y_axis+"> div").addClass("mod-item-success");
				} else { //节点未连接
					$("#item-"+data.x_axis+"-"+data.y_axis+"> div").removeClass();
					$("#item-"+data.x_axis+"-"+data.y_axis+"> div").addClass("mod-item-danger");
				}
			} else { //无节点
				$("#item-"+data.x_axis+"-"+data.y_axis+"> div").removeClass();
				$("#item-"+data.x_axis+"-"+data.y_axis+"> div").addClass("mod-item-warning");
			}
		}
	</script>
</body>
</html>