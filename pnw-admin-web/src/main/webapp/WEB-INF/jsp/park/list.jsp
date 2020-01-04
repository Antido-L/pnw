<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<style type="text/css">
		.model-list > li{
			padding-top: 3px;
			padding-bottom: 3px
		}
	</style>
	<jsp:include page="${pageContext.request.contextPath }/WEB-INF/jsp/common/head.jsp"></jsp:include>
	  	<link rel="stylesheet" href="${pageContext.request.contextPath }/css/bootstrap-timepicker.min.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath }/css/jquery.tagsinput.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath }/css/colorpicker.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath }/css/dropzone.css" />
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
		      <h2><i class="fa fa-pied-piper"></i> 停车场 <span>停车场管理</span></h2>
		      <div class="breadcrumb-wrapper">
		        <span class="label">当前位置:</span>
		        <ol class="breadcrumb">
		          <li><a href="index.html">PNW-ADMIN</a></li>
		          <li class="active">停车场管理</li>
		        </ol>
		      </div>
		    </div>
		    <!-- 中部内容区域 -->
		    <div class="contentpanel">
		    	<!-- 数据面板 -->
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
						<th style="padding: 0" colspan="9"><h4 id="table_title">停车场信息表</h4></th>
					</tr>
		              <tr>
		                <th>名称</th>
		                <th>停车场类型</th>
		                <th>状态</th>
		                <th>设计车位</th>
		                <th>正常工作</th>
		                <th>被使用</th>
		                <th>费率（元/小时）</th>
		                <th>负责人</th>
		                <th>车位列表</th>
		                <th>操作</th>
		              </tr>
		            </thead>
		            <tbody>
		              <c:forEach items="${pageBean.dataList }" var="park" varStatus="status">
		              <tr ondblclick="showModel('${park.id}')">
		                <td>${park.name }</td>
		                <!-- 停车场类型:0-室外,1-室内,2-立体 -->
		                <c:if test="${park.park_type ==0 }">
		                	<td>室外停车场</td>
		                </c:if>
		                <c:if test="${park.park_type ==1 }">
		                	<td>室内车场</td>
		                </c:if>
		                <c:if test="${park.park_type ==2 }">
		                	<td>立体停车场</td>
		                </c:if>
		                <c:if test="${park.state == 0}">
		                	<td>已废弃</td>
		                </c:if>
		                <c:if test="${park.state == 1}">
		                	<td>正常使用</td>
		                </c:if>
		                <c:if test="${park.state == 2}">
		                	<td>正在维护</td>
		                </c:if>
		                <c:if test="${park.state == 3}">
		                	<td>开发中</td>
		                </c:if>
		                <td>${park.design_count}</td>
		                <td>${park.working_count}</td>
		                <td>${park.using_count}</td>
		                <td>${park.price/100}</td>
		                <td>${park.parkAdmin.name}</td>
		                <td>
		                	<a href="${pageContext.request.contextPath }/space/list?park_id=${park.id}" target="_blank">前往</a>
		                </td>
		                <td class="table-action">
		                  <a href="javaScript:void(0)" onclick="infoEdit('${park.id}')"><i class="fa fa-pencil"></i></a>
		                  <!-- <a href="javaScript:void(0)" class="delete-row"><i class="fa fa-cog"></i></a> -->
		                  <a href="javaScript:void(0)" onclick="setting('${park.id}')"><i class="fa fa-cog"></i></a>
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
	    	</div> <!--中部内容结束-->
		  	
		    
			<!-- 停车场信息模态框(单击列表任意数据后弹出) -->
			<div id="park-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 id="park-modal-title" class="modal-title">Modal title</h4>
			      </div>
			      <div class="modal-body">
			      	<div>
			      		<h4>运行状态：<span id="park-model-state"></span></h4>
			      		<dl class="dl-horizontal">
			      		<ul class="model-list">
			      			<li>
			      				停车场类型：<span id="park-model-type"></span>
			      			</li>
			      			<li>
			      				费率：<span id="park-model-price"></span>
			      			</li>
			      			<li>
			      				使用状况：<span id="park-model-using"></span>
		   					</li>
			      			<li>
			      				停车场负责人：<a id="park-model-admin" href="#"></a>
			      			</li>
			      			<li>
			      				位置：<span id="park-model-position"></span>
		      				</li>
		      				<li>
			      				协调器编号：<a id="park-model-node" href="#"></a>
		      				</li>
		      				<li>
			      				控制器地址：<a id="park-model-service" href="#"></a>
		      				</li>
		      				<li>
			      				上线时间：<span id="park-model-created"></span>
		      				</li><li>
			      				上次修改时间：<span id="park-model-updated"></span>
		      				</li>
			      			<li>
			      				停车场描述：<span id="park-model-desc"></span>
		      				</li>
		      				<li>
			      				<a id="park-model-more" href="#">更多描述</a>
		      				</li>
			      			<li>
			      				<a id="park-model-fix" href="#">维修记录</a>
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
		</div>  <!-- 中部区域结束 -->	
    	<!-- </div> -->
	</section>
	
	<!-- 停车场设置模态框(单击设置按钮后弹出) -->
	<div id="parkSetting-modal" class="modal fade " tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 id="parkSetting-modal" class="modal-title">停车场设置</h4>
	      </div>
	      <div class="modal-body">
	      	<h5 style="color: red"><span id="">&nbsp&nbsp&nbsp&nbsp请谨慎改变停车场状态，除“使用中”以外其他选项均会下线该停车场。</span></h4>
	      	<h5 style="color: red"><span id="">&nbsp&nbsp&nbsp&nbsp请保证停车场内没有正在被使用的车位，下线后该停车场将不会被用户搜索到。</span></h4>
	     	<div class="form-group">
	     		<input type="hidden" id="settingParkId"/>
				<label style="text-align: right;" class="col-sm-3 control-label">停车位状态 <span class="asterisk">*</span></label>
				<div class="col-sm-6">
					<div class="rdio rdio-success">
	                  	<input type="radio" name="settingType" value="1" id="radioParkSetting0" checked="checked"/>
	                    <label for="radioParkSetting0">使用中</label>
	              	</div>
	              	<div class="rdio rdio-danger">
	                  	<input type="radio" name="settingType" value="3" id="radioParkSetting1"/>
	                    <label for="radioParkSetting1">开发中</label>
	              	</div>
	              	<div class="rdio rdio-danger">
	                  	<input type="radio" name="settingType" value="2" id="radioParkSetting2"/>
	                    <label for="radioParkSetting2">维护</label>
	              	</div>
              	</div>
           	</div>
	      </div>
	      <div class="modal-footer">
	      	<button type="button" class="btn btn-danger" onclick="settingSubmit()">确认更改</button>
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
	
	<script src="${pageContext.request.contextPath}/js/antido.js"></script>
	<script src="${pageContext.request.contextPath}/js/antido.search.js"></script>
	<script src="${pageContext.request.contextPath}/js/custom.js"></script>
	
	<script src="${pageContext.request.contextPath}/js/antido.search.js"></script>
	

	
	<script type="text/javascript">
		/*页面加载完毕*/
		$(function(){
			//加载下拉选数据
			loadSelect("${pageContext.request.contextPath}");
			//加载检索功能
			searchComplete("${pageContext.request.contextPath}");
		});
		
		/*列表数据点击事件*/
		function showModel(parkId) {
			//向后台要一个封装好的数据传输对象
			$.ajax({
	  			url:"${pageContext.request.contextPath}/park/modelJsonByParkId",
	  			data:{"parkId":parkId},
	  			dataType:"json",
	  			type:"POST",
	  			success:function(data){
	  				//显示数据
	  				$("#park-model-state").text(data.parkState);
	  				if(data.park.park_type == 0) {
	  					$("#park-model-type").text("室外停车场");
	  				} else if(data.park.park_type == 1) {
	  					$("#park-model-type").text("室内停车场");
	  				} else if(data.park.park_type == 2) {
	  					$("#park-model-type").text("立体停车场");
	  				}
	  				
	  				$("#park-model-price").text(data.parkPrice);
	  				$("#park-model-using").text(data.parkUsing);
	  				$("#park-model-price").text(data.parkPrice);
	  				$("#park-model-admin").attr("href","#");
	  				$("#park-model-admin").text(data.park.parkAdmin.name);
	  				$("#park-model-position").text(data.parkPosition);
	  				
	  				if(data.park.node != null) {
	  					$("#park-model-node").attr("href","#");
		  				$("#park-model-node").text(data.park.node.id);
	  				} else {
	  					$("#park-model-node").attr("href","#");
		  				$("#park-model-node").text("暂无节点");
	  				}
	  				
	  				if(data.serviceIp != null) {
	  					$("#park-model-service").attr("href","#");
	  					$("#park-model-service").text(data.serviceIp);
	  				} else {
	  					$("#park-model-service").attr("href","#");
	  					$("#park-model-service").text("暂无地址");
	  				} 
	  				
	  				$("#park-model-created").text(data.createdStr);
	  				$("#park-model-updated").text(data.updatedStr);
	  				$("#park-model-desc").text(data.park.position_desc);
	  				
	  			}
	  		});
			$("#park-modal-title").html("车位编号:"+parkId);
	  		//弹出模态框
  			$('#park-modal').modal();
	  	}
		
		/*停车场基本信息热编辑*/
		function infoEdit(parkId) {
			alert(parkId);
		}
		
		/*停车场设置*/
		function setting(parkId) {
			$('#settingParkId').val(parkId);
			$('#parkSetting-modal').modal();
		}
		
		/*停车场设置提交*/
		function settingSubmit() {
			var state = $("input[name='settingType']:checked").val();
			var parkId = $('#settingParkId').val();
			if(state == '1') return ;
			//提交后台
			$.ajax({
				url:"${pageContext.request.contextPath}/park/changeParkState",
	  			data:{"parkId":parkId,"state":state},
	  			dataType:"json",
	  			type:"POST",
	  			success:function(data){
					$('#parkSetting-modal').modal('hide'); 
	  				$("#alert-model-title").text("设置成功");
					$('#alertModel').modal({
						  keyboard:true
					})
					//1.5秒后自动刷新页面
					setTimeout(function(){location.reload()},1500);
	  			},
	  			error:function(data) {
	  				console.log(data);
	  				$('#parkSetting-modal').modal('hide'); 
	  				$("#alert-model-title").text("设置失败,请联系开发人员");
					$('#alertModel').modal({
						  keyboard:true
					})
					//1.5秒后自动刷新页面
					setTimeout(function(){location.reload()},3000);
	  			}
				
			});
			
		}
		
		
	</script>
</body>         
</html>