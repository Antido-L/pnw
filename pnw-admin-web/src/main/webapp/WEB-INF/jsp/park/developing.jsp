<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<jsp:include page="${pageContext.request.contextPath }/WEB-INF/jsp/common/head.jsp"></jsp:include>
	<style type="text/css">
		.model-list > li{
			padding-top: 3px;
			padding-bottom: 3px
		}
	</style>
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
		      <h2><i class="fa fa-home"></i> 停车场 <span>开发中的停车场</span></h2>
		      <div class="breadcrumb-wrapper">
		        <span class="label">当前位置:</span>
		        <ol class="breadcrumb">
		          <li><a href="index.html">Bracket</a></li>
		          <li class="active">停车场</li>
		          <li class="active">开发中的停车场</li>
		        </ol>
		      </div>
		    </div>
		    <!-- 中部内容区域 -->
		    <div class="contentpanel">
		    	<div class="panel panel-success" >
		            <div class="panel-heading" style="padding: 10px">
		              <div class="panel-btns" >
		                <a href="" class="panel-close">&times;</a>
		                <a href="" class="minimize">&minus;</a>
		              </div><!-- panel-btns -->
		              <h4 class="panel-title">区域选择器</h4>
		            </div>
		            <div class="panel-body">
		              <div class="col-sm-2">
		                <select id="provinceSelect" class="form-control" onchange="loadCitySelect('${pageContext.request.contextPath}')">
		                  <option value="">---省/直辖市---</option>
		                </select>
		              </div>
		             <div class="col-sm-3">
		                <select id="citySelect" class="form-control" onchange="loadDistrictSelect('${pageContext.request.contextPath}')">
		                  <option value="">---市/市辖区---</option>
		                </select>
		              </div>
		              <div class="col-sm-3">
		                <select id="districtSelect" class="form-control" onchange="loadStreetSelect('${pageContext.request.contextPath}')">
		                  <option value="">---区/县---</option>
		                </select>
		              </div>
		              <div class="col-sm-3">
		                 <select id="streetSelect" class="form-control">
		                  <option value="">---街道/片区---</option>
		                </select>
		              </div>
		              <div class="col-sm-1">
		              	<button id="findPark" class="btn btn-success" type="submit" onclick="submitRegionChoose()">前往查看</button>
		              </div>
	            	</div> <!-- panel-body结束 -->
		      	  <!--信息表-->
			      <div class="table-responsive ">
			          <table class="table table-bordered table-hover mb30">
			            <thead>
				            <tr>
								<th style="padding: 0" colspan="8"><h4 id="table_title">正在开发中的停车场</h4></th>
							</tr>
				            <tr>
					            <th>名称</th>
					            <th>停车场类型</th>
					            <th>设计车位</th>
					            <th>已绑定车位</th>
					            <th>负责人</th>
					            <th colspan="4">操作</th>
				            </tr>
			            </thead>
			            <tbody>
			            	<c:forEach items="${pageBean.dataList }" var="park">
			            	<tr>
			            		<td>${park.name }</td>
					            <c:if test="${park.park_type == 0 }">
					            	<td>室外</td>
					            </c:if>
					            <c:if test="${park.park_type == 1 }">
					            	<td>室内</td>
					            </c:if>
					            <c:if test="${park.park_type == 2 }">
					            	<td>立体</td>
					            </c:if>
					            <td>${park.design_count }</td>
					            <c:if test="${park.working_count == null }">
					            	<td>0</td>
					            </c:if>
					            <c:if test="${park.working_count != null }">
					            	 <td>${park.working_count }</td>
					            </c:if>
					            <td>${park.parkAdmin.name }</td>
				            	<td>
				            		<a href="${pageContext.request.contextPath }/park/infoEdit?parkId=${park.id}">编辑停车场信息</a>
		            			</td>
					            <td>
					            	<a href="${pageContext.request.contextPath }/park/spaceEdit?parkId=${park.id}">编辑车位信息</a>
			            		</td>
					            <td>
					            	<a href="#"><span class="fa fa-link"></span>测试连接</a>
				            	</td>
				            	<td class="table-action">
				                  <a href="javaScript:void(0)" onclick="parkPublish('${park.id}')"><i class="fa fa-check"></i></a>
				                  <a href="javaScript:void(0)" onclick="parkDelete('${park.id}')" class="delete-row"><i class="fa fa-trash-o"></i></a>
				                </td>
				            </tr>	
			            	</c:forEach>
			            </tbody>
			          </table>
			      </div><!-- table-responsive结束 -->
			      <div class="panel-footer" style="padding: 10px">
				    <!--引入分页-->
	         		<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/common/page.jsp"></jsp:include>
				  </div>
       		  </div><!-- panel结束 -->
		    </div><!-- 中部内容结束 -->
	    </div>
	<section>
	
	<!-- 停车场信息模态框(单击列表任意数据后弹出) -->
	<div id="park-confirm" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 id="park-confirm-title" class="modal-title" id="myModalLabel">Modal title</h4>
	      </div>
	      <div class="modal-body">
	      	<div>
	      		<h5 style="color: red"><span id="park-confirm-alert"></span></h4>
	      		<ul class="model-list">
	      			<li>
	      				停车场名称：<span id="park-confirm-name"></span>
      				</li>
	      			<li>
	      				停车场描述：<span id="park-confirm-desc"></span>
      				</li>
	      			<li>
	      				停车场类型：<span id="park-confirm-type"></span>
	      			</li>
	      			<li>
	      				费率：<span id="park-confirm-price"></span>
	      			</li>
	      			<li>
	      				使用状况：<span id="park-confirm-using"></span>
   					</li>
	      			<li>
	      				停车场负责人：<a id="park-confirm-admin" href="#"></a>
	      			</li>
      				<li>
	      				协调器编号：<a id="park-confirm-node" href="#"></a>
      				</li>
      				<li>
	      				控制器地址：<a id="park-confirm-service" href="#"></a>
      				</li>
      				<li>
	      				可使用车位数：（<span class="park-confirm-spaceCount"></span>/<span class="park-confirm-designCount"></span>）
      				</li>
      				<!-- <li>
	      				已部署车位数：（<span class="park-confirm-spaceCount"></span>/<span class="park-confirm-carItemCount"></span>）
      				</li> -->
      				<li>
	      				正常连接车位数：（<span class="park-confirm-spaceWorkCount"></span>/<span class="park-confirm-spaceCount"></span>）
      				</li>
      				<li>
	      				上次修改时间：<span id="park-confirm-updated"></span>
      				</li>
      				<input id="park-confirm-id" type="hidden">
	      		</ul>
	      	</div>
	      </div>
	      <div class="modal-footer">
	      	<button id="publishConfirm" type="button" class="hidden btn btn-danger">确认发布</button>
	        <button id="deleteConfirm" type="button" class="hidden btn btn-danger">确定删除</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	      </div>
	    </div>
	  </div>
	</div> <!-- 停车位信息模态框 结束 -->
	
	<!-- ALERT -->
	<div class="modal fade bs-example-modal-sm" id="alertModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog modal-sm" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="alert-model-title">Modal title</h4>
	      </div>
	      <!-- <div class="modal-body" id="alert-model-body">
           	
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	      </div> -->
	    </div>
	  </div>
	</div><!-- ALERT结束 -->
	
	
	
	<script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-ui-1.10.3.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/modernizr.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.sparkline.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/toggles.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/retina.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.cookies.js"></script>
	
	<script src="${pageContext.request.contextPath}/js/custom.js"></script>
	
	<script src="${pageContext.request.contextPath}/js/antido.js"></script>
	<script src="${pageContext.request.contextPath}/js/antido.search.js"></script>
	
	<script>
		/*页面加载完毕*/
		$(function(){
			// 加载下拉选数据
			loadSelect("${pageContext.request.contextPath}");
			searchComplete("${pageContext.request.contextPath}");
		});
		
		/*发布正在开发中的停车场按键单击事件*/
		function parkPublish(parkId) {//弹出确认信息
			$("#deleteConfirm").addClass("hidden");
			$("#publishConfirm").removeClass("hidden");
			$("#park-confirm-id").val(parkId);
			//查询车位信息
			$.ajax({
				url:"${pageContext.request.contextPath}/park/modelJsonByParkId",
	  			type:"POST",
	  			data:{"parkId":parkId},
	  			dataType:"JSON",
	  			success:function(data) {
	  				//更改模态框数据
	  				$("#park-confirm-title").html("确认发布");
	  				$("#park-confirm-alert").text("请仔细核对停车场信息，确认发布后该停车场会立即进入用户的搜索范围并对外提供服务");
	  				$("#park-confirm-name").text(data.park.name);
	  				
	  				if(data.park.park_type == 0) {
	  					$("#park-confirm-type").text("室外停车场");
	  				} else if(data.park.park_type == 1) {
	  					$("#park-confirm-type").text("室内停车场");
	  				} else if(data.park.park_type == 2) {
	  					$("#park-confirm-type").text("立体停车场");
	  				}
	  				
	  				$("#park-confirm-price").text(data.parkPrice);
	  				$("#park-confirm-using").text(data.parkUsing);
	  				$("#park-confirm-price").text(data.parkPrice);
	  				
	  				$("#park-confirm-admin").attr("href","#");
	  				$("#park-confirm-admin").text(data.park.parkAdmin.name);
	  				
	  				if(data.park.node != null) {
	  					$("#park-confirm-node").attr("href","#");
		  				$("#park-confirm-node").text(data.park.node.id);
	  				} else {
	  					$("#park-confirm-node").attr("href","#");
		  				$("#park-confirm-node").text("暂无节点");
	  				}
	  				
	  				if(data.serviceIp != null) {
	  					$("#park-confirm-service").attr("href","#");
	  					$("#park-confirm-service").text(data.serviceIp);
	  				} else {
	  					$("#park-confirm-service").attr("href","#");
	  					$("#park-confirm-service").text("暂无地址");
	  				} 
	  				
	  				$(".park-confirm-designCount").text(data.park.design_count)
	  				
	  				$("#park-confirm-updated").text(data.updatedStr);
	  				$("#park-confirm-desc").text(data.park.position_desc);
	  			}
			});
	  			
			//查询车位部署情况
			$.ajax({
				url:"${pageContext.request.contextPath}/space/allSpaceByParkId",
	  			type:"POST",
	  			data:{"parkId":parkId},
	  			dataType:"JSON",
	  			success:function(data) { //包含当前停车场内车位列表
	  				
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
	  				$(".park-confirm-spaceCount").text(data.length);
	  				$(".park-confirm-spaceWorkCount").text(onlineSize);
	  			}
			});
			//弹出模态框
			$('#park-confirm').modal({
				keyboard:true
			});
		}
		
		/*删除正在开发中的停车场按键单击事件*/
		function parkDelete(parkId) {
			$("#deleteConfirm").removeClass("hidden");
			$("#publishConfirm").addClass("hidden");
			$("#park-confirm-id").val(parkId);
			//查询车位信息
			$.ajax({
				url:"${pageContext.request.contextPath}/park/modelJsonByParkId",
	  			type:"POST",
	  			data:{"parkId":parkId},
	  			dataType:"JSON",
	  			success:function(data) {
	  				//更改模态框数据
	  				$("#park-confirm-title").html("删除停车场");
	  				$("#park-confirm-alert").text("删除该开发中的停车场所有信息（包括车位），已被绑定的节点将会重置为自由状态");
	  				$("#park-confirm-name").text(data.park.name);
	  				
	  				if(data.park.park_type == 0) {
	  					$("#park-confirm-type").text("室外停车场");
	  				} else if(data.park.park_type == 1) {
	  					$("#park-confirm-type").text("室内停车场");
	  				} else if(data.park.park_type == 2) {
	  					$("#park-confirm-type").text("立体停车场");
	  				}
	  				
	  				$("#park-confirm-price").text(data.parkPrice);
	  				$("#park-confirm-using").text(data.parkUsing);
	  				$("#park-confirm-price").text(data.parkPrice);
	  				
	  				$("#park-confirm-admin").attr("href","#");
	  				$("#park-confirm-admin").text(data.park.parkAdmin.name);
	  				
	  				if(data.park.node != null) {
	  					$("#park-confirm-node").attr("href","#");
		  				$("#park-confirm-node").text(data.park.node.id);
	  				} else {
	  					$("#park-confirm-node").attr("href","#");
		  				$("#park-confirm-node").text("暂无节点");
	  				}
	  				
	  				if(data.serviceIp != null) {
	  					$("#park-confirm-service").attr("href","#");
	  					$("#park-confirm-service").text(data.serviceIp);
	  				} else {
	  					$("#park-confirm-service").attr("href","#");
	  					$("#park-confirm-service").text("暂无地址");
	  				} 
	  				
	  				$(".park-confirm-designCount").text(data.park.design_count)
	  				
	  				$("#park-confirm-updated").text(data.updatedStr);
	  				$("#park-confirm-desc").text(data.park.position_desc);
	  			}
			});
	  			
			//查询车位部署情况
			$.ajax({
				url:"${pageContext.request.contextPath}/space/allSpaceByParkId",
	  			type:"POST",
	  			data:{"parkId":parkId},
	  			dataType:"JSON",
	  			success:function(data) { //包含当前停车场内车位列表
	  				
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
	  				$(".park-confirm-spaceCount").text(data.length);
	  				$(".park-confirm-spaceWorkCount").text(onlineSize);
	  			}
			});
			//弹出模态框
			$('#park-confirm').modal({
				keyboard:true
			});
		}
		
		/*
		 * 确认发布按钮单击事件
		 */
		$("#publishConfirm").click(function() {
			$.ajax({
				url:"${pageContext.request.contextPath}/park/parkPublish",
	  			type:"POST",
	  			data:{"parkId":$("#park-confirm-id").val()},
	  			dataType:"JSON",
	  			success:function(data) {
	  				console.log(data);
					$('#park-confirm').modal('hide'); 
	  				
	  				$("#alert-model-title").text("发布成功");
					$("#alert-model-body").empty();
					$('#alertModel').modal({
						  keyboard:true
					})
					
					//3秒后自动刷新页面
					setTimeout(function(){location.reload()},1500);
	  			},
	  			error:function(e) {
	  				console.log(e);
					$('#park-confirm').modal('hide'); 
	  				
	  				$("#alert-model-title").text("发布失败,请联系管理员");
					$("#alert-model-body").empty();
					$('#alertModel').modal({
						  keyboard:true
					})
					
					//3秒后自动刷新页面
					setTimeout(function(){location.reload()},3000);
	  			}
			});
		});
		
		/*
		 *确认删除按钮单击事件
		 */
		$("#deleteConfirm").click(function() {
			//根据id删除park
			$.ajax({
				url:"${pageContext.request.contextPath}/park/deletePark",
	  			type:"POST",
	  			data:{"parkId":$("#park-confirm-id").val()},
	  			dataType:"JSON",
	  			success:function(data) {
	  				$('#park-confirm').modal('hide'); 
	  				
	  				$("#alert-model-title").text("删除成功");
					$("#alert-model-body").empty();
					$('#alertModel').modal({
						  keyboard:true
					})
					
					//3秒后自动刷新页面
					setTimeout(function(){location.reload()},1500);
	  			},
	  			error:function(e) {
	  				console.log(e);
					$('#park-confirm').modal('hide'); 
	  				
	  				$("#alert-model-title").text("删除失败,请联系管理员");
					$("#alert-model-body").empty();
					$('#alertModel').modal({
						  keyboard:true
					})
					
					//3秒后自动刷新页面
					setTimeout(function(){location.reload()},3000);
	  			}
			});
		});
	</script>
		
</body>
</html>