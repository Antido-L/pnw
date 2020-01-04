<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<jsp:include page="${pageContext.request.contextPath }/WEB-INF/jsp/common/head.jsp"></jsp:include>
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
		      <h2><i class="fa fa-car"></i> 停车位 <span>停车位列表</span></h2>
		      <div class="breadcrumb-wrapper">
		        <span class="label">当前位置:</span>
		        <ol class="breadcrumb">
		          <li><a href="index.html">PNW-ADMIN</a></li>
		          <li class="active">停车位</li>
		          <li class="active">停车位列表</li>
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
	                <select id="provinceSelect" class="form-control">
	                  <option value="">---省/直辖市---</option>
	                </select>
	              </div>
	             <div class="col-sm-2">
	                <select id="citySelect" class="form-control">
	                  <option value="">---市/市辖区---</option>
	                </select>
	              </div>
	              <div class="col-sm-2">
	                <select id="districtSelect" class="form-control">
	                  <option value="">---区/县---</option>
	                </select>
	              </div>
	              <div class="col-sm-2">
	                 <select id="streetSelect" class="form-control">
	                  <option value="">---街道/片区---</option>
	                </select>
	              </div>
	              <div class="col-sm-3">
	                <select id="parkSelect" class="form-control">
	                  <option value="">---停车场---</option>
	                </select>
	              </div>
	              <div class="col-sm-1">
	              	<button id="findPark" class="btn btn-success" type="submit">前往查看</button>
	              </div>
            	</div> <!-- panel-body结束 -->
		      <!--停车位信息表-->
		      <div class="table-responsive ">
		          <table class="table table-bordered table-hover table-condensed mb30">
		            <thead>
		            <tr>
		            	<th style="padding: 0" colspan="8"><h4 id="table_title">停车场车位信息表</h4></th>
		            </tr>
		              <tr>
		                <th>车位编码</th>
		                <!-- 车位类型 -->
						<th id="space_type" onclick="shiftSpaceType(this)" style="cursor:pointer;">
		                	<div class="inShow" space_type_num="-1">
		                		车位类型<span class="fa fa-plus-square"></span>
		                	</div>
		                	<div class="hidden" space_type_num="0">
		                		任意车型<span class="fa fa-check-square"></span>
		                	</div>
		                	<div class="hidden" space_type_num="1">
		                		仅小型车<span class="fa fa-check-square"></span>
		                	</div>
		                	<div class="hidden" space_type_num="2">
		                		仅小型中型车<span class="fa fa-check-square"></span>
		                	</div>
		                	<div class="hidden" space_type_num="3">
		                		仅中型大型车<span class="fa fa-check-square"></span>
		                	</div>
						</th>
						<!-- 运行状态 -->
						<th id="running_state" onclick="shiftRunningState(this)" style="cursor:pointer;">
							<div class="inShow" running_state_num="-1">
		                		运行状态<span class="fa fa-plus-square"></span>
		                	</div>
		                	<div class="hidden" running_state_num="0">
		                		已废弃<span class="fa fa-check-square"></span>
		                	</div>
		                	<div class="hidden" running_state_num="1">
		                		正常使用<span class="fa fa-check-square"></span>
		                	</div>
		                	<div class="hidden" running_state_num="2">
		                		维护中<span class="fa fa-check-square"></span>
		                	</div>
						</th>
						<!-- 使用状态 -->
						<th id="using_state" onclick="shiftUsingState(this)" style="cursor:pointer;">
							<div class="inShow" using_state_num="-1">
		                		使用状态<span class="fa fa-plus-square"></span>
		                	</div>
		                	<div class="hidden" using_state_num="0">
		                		空闲<span class="fa fa-check-square"></span>
		                	</div>
		                	<div class="hidden" using_state_num="1">
		                		正在使用<span class="fa fa-check-square"></span>
		                	</div>
		                	<div class="hidden" using_state_num="2">
		                		被预约<span class="fa fa-check-square"></span>
		                	</div>
						</th>
						<!-- 停靠时间 -->
		                <th id="parked_time" onclick="shiftParkedTime(this)" style="cursor:pointer;">
		                	<div class="inShow" parked_time_num="-1">
		                		停靠时间<span class="fa fa-plus-square"></span>
		                	</div>
		                	<div class="hidden" parked_time_num="0">
		                		停靠时间<span class="fa fa-chevron-down"></span>
		                	</div>
		                	<div class="hidden" parked_time_num="1">
		                		停靠时间<span class="fa fa-chevron-up"></span>
		                	</div>
	                	</th>
	                	<!-- 约定离开时间 -->
		                <th id="leaving_time" onclick="shiftLeavingTime(this)" style="cursor:pointer;">
		                	<div class="inShow" leaving_time_num="-1">
		                		约定离开时间<span class="fa fa-plus-square"></span>
		                	</div>
		                	<div class="hidden" leaving_time_num="0">
		                		约定离开时间<span class="fa fa-chevron-down"></span>
		                	</div>
		                	<div class="hidden" leaving_time_num="1">
		                		约定离开时间<span class="fa fa-chevron-up"></span>
		                	</div>
	                	</th>
		                <th>当前用户</th>
		                <th>操作</th>
		              </tr>
		            </thead>
		            <tbody>
		              <c:forEach items="${pageBean.dataList }" var="item" varStatus="status">
		              <tr onclick="showModel('${item.idStr}')">
		                <td>${item.code }</td>
		                <c:if test="${item.space_type == 0}">
		                	<td>任意车型</td>
		                </c:if>
		                <c:if test="${item.space_type == 1}">
		                	<td>仅小型车</td>
		                </c:if>
		                <c:if test="${item.space_type == 2}">
		                	<td>仅中型车与小型车</td>
		                </c:if>
		                <c:if test="${item.space_type == 3}">
		                	<td>仅大型车</td>
		                </c:if>
		               	 <!-- 运行状态:0-废弃,1-正常使用,2-维护中 -->
		               	<c:if test="${item.running_state == 0}">
		               		<td>已废弃</td>
		               	</c:if>
		               	<c:if test="${item.running_state == 1}">
		               		<td>正常使用</td>
		               	</c:if>
		               	<c:if test="${item.running_state == 2}">
		               		<td>维护中</td>
		               	</c:if>
		                <!-- 使用状态:0-空闲,1-正在使用,2-预约中 -->
		                <c:if test="${item.using_state == 0}">
		               		<td>空闲</td>
		               	</c:if>
		               	<c:if test="${item.using_state == 1}">
		               		<td>正在使用</td>
		               	</c:if>
		               	<c:if test="${item.using_state == 2}">
		               		<td>被预约</td>
		               	</c:if>
		               	<c:if test="${item.user_id != null}">
		               		<td><fmt:formatDate value="${item.parked_time}" pattern="yyyy年MM月dd日HH:mm" /></td>
			                <c:if test="${item.leaving_time != null}">
			                	<td><fmt:formatDate value="${item.leaving_time}" pattern="yyyy年MM月dd日HH:mm" /></td>
			                </c:if>
			                <c:if test="${item.leaving_time == null}">
			                	<td>--</td>
			                </c:if>
			                <td>${item.user_id}</td>
		               	</c:if>
		               	<c:if test="${item.user_id == null}">
		               		<!-- <td>--</td> -->
							<td>--</td>		               		
			                <td>--</td>
			                <td>--</td>
		               	</c:if>
		                <td class="table-action">
		                  <a href=""><i class="fa fa-pencil"></i></a>
		                  <a href="" class="delete-row"><i class="fa fa-trash-o"></i></a>
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
		</div>  <!-- 中部区域结束 -->
    	<!-- </div> -->
	</section>
	
	<!-- 停车位信息模态框(单击列表任意数据后弹出) -->
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
	<script src="${pageContext.request.contextPath}/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-ui-1.10.3.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/modernizr.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.sparkline.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/toggles.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/retina.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.cookies.js"></script>
	
	<script src="${pageContext.request.contextPath}/js/antido.js"></script>
	<script src="${pageContext.request.contextPath}/js/custom.js"></script>
	
	<script src="${pageContext.request.contextPath}/js/antido.search.js"></script>
	
	<script>
		$().ready(function() {
			searchComplete("${pageContext.request.contextPath}");
		});
	</script>
  	<script>
  		/*页面加载完毕*/
	  	$(document).ready(function (){
			//获取URL中的参数
	  		var space_type = getQueryString("space_type");
	  		var running_state = getQueryString("running_state");
	  		var using_state = getQueryString("using_state");
	  		var parked_time = getQueryString("parked_time");
	  		var leaving_time = getQueryString("leaving_time");
	  		
	  		//更新选项
	  		showState(space_type,"space_type");
	  		showState(running_state,"running_state");
	  		showState(using_state,"using_state");
	  		showState(parked_time,"parked_time");
	  		showState(leaving_time,"leaving_time");
	  		
	  		
	  		//关于加载数据
			//从省开始获取数据
			//如果上一级没有被选定 , 则不能越越级选择
			//当上一级数据加载完毕后自动加载下一级数据 当为最后一级停止
			//当用户选择区域数据时后触发事件自动加载下一级数据
			
			//关于回显数据
			//在车位页面的逻辑中,只有选择了指定的停车场才会获得列表数据(在停车场逻辑中 列表数据的来源是不确定的)
			//停车场id拥有唯一街道id,街道id拥有唯一区县id 一层层向上加载
			//在数据加载完毕后回显数据
			
			//在停车场页面中
			//后台需要根据不同区域范围获取区域内的所有车场数据
			//需要判断当前区域所在级别 ,向下查找直到查出所有所属街道id后获取车场信息,向上查找回显数据
			//需要对应的pojo
			
			
			//当页面没有选定停车场时 自动加载省一级下拉选数据
			loadSelect();
		});//$(document).ready结束
		
		
		/*根据当前URL中的停车场id 生成与之对应的选项*/
  		function loadSelect() {
			//获取URL中park_id参数
  			var park_id = getQueryString("park_id");
  			
  			if(park_id != null && park_id != "") { //当park_id存在时 回显所有数据
	  			//去除"&"
	  			while(park_id.indexOf("&") != -1) {
	  				park_id = park_id.replace("&","");
	  			}
	  			//正则匹配参数中的值
	  			var reg = new RegExp("=([^&]*)");
	  			var value = park_id.match(reg)[1];
	  			/* 根据当前park_id 加载停车场下拉选 */
	  			$.ajax({
		  			url:"${pageContext.request.contextPath}/region/backJsonByParkId",
		  			type:"POST",
		  			data:{"park_id":value},
		  			dataType:"json",
		  			//在一次ajax请求中获取所有需要回显的数据
		  			success:function(data) {  //数据中分别存有每个下拉选的list
		  				//回显parkSelect
		  				var $parkSelect = $("#parkSelect");
		  				for (var i = 0; i < data.parkEcho.length; i++) {
		  					//回显被选数据
		  					var $option = "<option value="+data.parkEcho[i].id+">"+data.parkEcho[i].name+"</option>";
							$parkSelect.append($option);
						}
		  				
		  				//回显streetSelect
		  				var $streetSelect = $("#streetSelect");
		  				for (var i = 0; i < data.streetEcho.length; i++) {
		  					//回显被选数据
		  					var $option = "<option value="+data.streetEcho[i].id+">"+data.streetEcho[i].name+"</option>";
							$streetSelect.append($option);
						}
		  				
		  				//回显districtSelect
		  				var $districtSelect = $("#districtSelect");
		  				for (var i = 0; i < data.districtEcho.length; i++) {
		  					//回显被选数据
		  					var $option = "<option value="+data.districtEcho[i].id+">"+data.districtEcho[i].name+"</option>";
							$districtSelect.append($option);
						}
		  				
		  				//回显citySelect
		  				var $citySelect = $("#citySelect");
		  				for (var i = 0; i < data.cityEcho.length; i++) {
		  					//回显被选数据
		  					var $option = "<option value="+data.cityEcho[i].id+">"+data.cityEcho[i].name+"</option>";
							$citySelect.append($option);
						}
		  				
		  				//回显provinceSelect
		  				var $provinceSelect = $("#provinceSelect");
		  				for (var i = 0; i < data.provinceEcho.length; i++) {
		  					//回显被选数据
		  					var $option = "<option value="+data.provinceEcho[i].id+">"+data.provinceEcho[i].name+"</option>";
							$provinceSelect.append($option);
						}
		  				
		  				//回显被选数据
		  				$parkSelect.find("option[value='"+data.selectedEcho.parkSelected+"']").attr("selected","selected");
		  				$streetSelect.find("option[value='"+data.selectedEcho.streetSelected+"']").attr("selected","selected");
		  				$districtSelect.find("option[value='"+data.selectedEcho.districtSelected+"']").attr("selected","selected");
		  				$citySelect.find("option[value='"+data.selectedEcho.citySelected+"']").attr("selected","selected");
		  				$provinceSelect.find("option[value='"+data.selectedEcho.provinceSelected+"']").attr("selected","selected");
		  				
		  				var parkName = $parkSelect.find("option[value='"+data.selectedEcho.parkSelected+"']").text();
		  				var modHref = "<a href='${pageContext.request.contextPath}/park/mod?"+park_id+"'>切换至模拟图</a>"
		  				console.log(park_id);
		  				$("#table_title").html(parkName+"车位信息表--"+modHref);
		  				//<span class='fa fa-exchange'></span>
		  			}
	  			});
	  			
	  		} else {  //当park_id不存在存在时加载省一级数据
	  			$.ajax({
		  			url:"${pageContext.request.contextPath}/region/provinceJson",
		  			type:"POST",
		  			dataType:"json",
		  			success:function(data) {
		  				var $select = $("#provinceSelect");
		  				for (var i = 0; i < data.length; i++) {
		  					//<option value="United States">United States</option>
							var $option = "<option value="+data[i].id+">"+data[i].name+"</option>";
							$select.append($option);
						}
		  			}
		  		});
	  		}
  		} // loadSelect结束
  		
  		
  		/*停车场选择提交按钮*/
  		$("#findPark").click(function() {
			var parkId = $("#parkSelect").val();
			//当未选择停车场时 弹出提示
			if(parkId == "" || parkId == undefined) {
				alert("请选择停车场");
			} else {  //当选择停车场后提交仅带有park_id参数的get请求
				var URL = window.location.href;
	  			var URLStr = URL.split("?")[0];
	  			window.location.href = URLStr+"?park_id="+parkId;
			}
  			//changeURL("park_id",1);
  		});
		
		/*省一级选择*/
		$("#provinceSelect").change(function() {
			//清空下级select
			$("#citySelect").empty();
			$("#citySelect").append("<option value=''>---市/市辖区---</option>");
			$("#districtSelect").empty();
			$("#districtSelect").append("<option value=''>---区/县---</option>");
			$("#streetSelect").empty();
			$("#streetSelect").append("<option value=''>---街道/片区---</option>");
			$("#parkSelect").empty();
			$("#parkSelect").append("<option value=''>---停车场---</option>");
			
			//加载下一级区域数据
			var selectId = $("#provinceSelect").find("option:selected").val();
			$.ajax({
	  			url:"${pageContext.request.contextPath}/region/getJsonByParentId",
	  			type:"POST",
	  			data:{"parent_id":selectId},
	  			dataType:"json",
	  			success:function(data) {
	  				var $select = $("#citySelect");
	  				for (var i = 0; i < data.length; i++) {
						var $option = "<option value="+data[i].id+">"+data[i].name+"</option>";
						$select.append($option);
					}
	  			}
	  		});
		});
		
		/*市/县一级选择*/
		$("#citySelect").change(function() {
			//清空下级select
			$("#districtSelect").empty();
			$("#districtSelect").append("<option value=''>---区/县---</option>");
			$("#streetSelect").empty();
			$("#streetSelect").append("<option value=''>---街道/片区---</option>");
			$("#parkSelect").empty();
			$("#parkSelect").append("<option value=''>---停车场---</option>");
			
			//加载区/县数据
			var selectId = $("#citySelect").find("option:selected").val();
			$.ajax({
	  			url:"${pageContext.request.contextPath}/region/getJsonByParentId",
	  			type:"POST",
	  			data:{"parent_id":selectId},
	  			dataType:"json",
	  			success:function(data) {
	  				var $select = $("#districtSelect");
	  				for (var i = 0; i < data.length; i++) {
	  					if(data[i].name == "市辖区") continue;
						var $option = "<option value="+data[i].id+">"+data[i].name+"</option>";
						$select.append($option);
					}
	  			}
	  		});
		});
		
		/*区/县一级选择*/
		$("#districtSelect").change(function() {
			//清空下级select
			$("#streetSelect").empty();
			$("#streetSelect").append("<option value=''>---街道/片区---</option>");
			$("#parkSelect").empty();
			$("#parkSelect").append("<option value=''>---停车场---</option>");
			
			//加载街道数据数据
			var selectId = $("#districtSelect").find("option:selected").val();
			$.ajax({
	  			url:"${pageContext.request.contextPath}/region/StreetJson",
	  			type:"POST",
	  			data:{"district_id":selectId},
	  			dataType:"json",
	  			success:function(data) {
	  				var $select = $("#streetSelect");
	  				for (var i = 0; i < data.length; i++) {
						var $option = "<option value="+data[i].id+">"+data[i].name+"</option>";
						$select.append($option);
					}
	  			}
	  		});
		});
		
		/*街道/片区一级选择*/
		$("#streetSelect").change(function() {
			//清空下级select
			$("#parkSelect").empty();
			$("#parkSelect").append("<option value=''>---停车场---</option>");
			
			//加载停车场数据
			var selectId = $("#streetSelect").find("option:selected").val();
			$.ajax({
	  			url:"${pageContext.request.contextPath}/park/parkJsonByStreetId",
	  			type:"POST",
	  			data:{"street_id":selectId},
	  			dataType:"json",
	  			success:function(data) {
	  				var $select = $("#parkSelect");
	  				for (var i = 0; i < data.length; i++) {
						var $option = "<option value="+data[i].id+">"+data[i].name+"</option>";
						$select.append($option);
					}
	  			}
	  		});
		});
  		
  		/*根据当前URL中的参数 生成与之对应的选项*/
  		function showState(state,filed) {
  			if(state != null) {
	  			//去除"&"
	  			while(state.indexOf("&") != -1) {
	  				state = state.replace("&","");
	  			}
	  			//正则匹配参数中的值
	  			var reg = new RegExp("=([^&]*)");
	  			var value = state.match(reg);
	  			
	  			/* 转换显示状态 */
				$("#"+filed).find("div[class='inShow']").removeClass("inShow").addClass("hidden")
				$("div["+filed+"_num="+value[1]+"]").removeClass("hidden").addClass("inShow");
	  		}
  		}

  		/*列表数据单击事件弹出对应的车位信息*/
	  	function showModel(spaceId) {
	  		$("#space-modal-title").html("车位编号:"+spaceId);
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
  		
	  	
		//转换车位类型
		function shiftSpaceType(ths) {
			//获取当前显示的类型
			var current_type = $(ths).find("div[class='inShow']").attr("space_type_num");
			//类型数+1
			if(current_type != 3) {
				current_type++;
			} else {
				current_type = -1; 
			}
			// 更换显示状态
			$(ths).find("div[class='inShow']").removeClass("inShow").addClass("hidden");
			//alert($(ths).find("div[space_type_num="+current_type+"]").html());
			$(ths).find("div[space_type_num="+current_type+"]").removeClass("hidden").addClass("inShow");
			changeURL("space_type",current_type);
		}
		
		
		//转换运行状态
		function shiftRunningState(ths) {
			//获取当前显示的类型
			var current_state = $(ths).find("div[class='inShow']").attr("running_state_num");
			//类型数+1
			if(current_state != 2) {
				current_state++;
			} else {
				current_state = -1; 
			}
			// 更换显示状态
			$(ths).find("div[class='inShow']").removeClass("inShow").addClass("hidden");
			//alert($(ths).find("div[space_type_num="+current_type+"]").html());
			$(ths).find("div[running_state_num="+current_state+"]").removeClass("hidden").addClass("inShow");
			changeURL("running_state",current_state);
		}
		
		//转换使用状态
		function shiftUsingState(ths) {
			//获取当前显示的类型
			var current_state = $(ths).find("div[class='inShow']").attr("using_state_num");
			//类型数+1
			if(current_state != 2) {
				current_state++;
			} else {
				current_state = -1; 
			}
			// 更换显示状态
			$(ths).find("div[class='inShow']").removeClass("inShow").addClass("hidden");
			//alert($(ths).find("div[space_type_num="+current_type+"]").html());
			$(ths).find("div[using_state_num="+current_state+"]").removeClass("hidden").addClass("inShow");
			changeURL("using_state",current_state);
		}
		
		//转换停时间
		function shiftParkedTime(ths) {
			//获取当前显示的类型
			var current_state = $(ths).find("div[class='inShow']").attr("parked_time_num");
			//类型数+1
			if(current_state != 1) {
				current_state++;
			} else {
				current_state = -1; 
			}
			// 更换显示状态
			$(ths).find("div[class='inShow']").removeClass("inShow").addClass("hidden");
			//alert($(ths).find("div[space_type_num="+current_type+"]").html());
			$(ths).find("div[parked_time_num="+current_state+"]").removeClass("hidden").addClass("inShow");
			changeURL("parked_time",current_state);
		}
		
		//转换约定离开的的时间
		function shiftLeavingTime(ths) {
			//获取当前显示的类型
			var current_state = $(ths).find("div[class='inShow']").attr("leaving_time_num");
			//类型数+1
			if(current_state != 1) {
				current_state++;
			} else {
				current_state = -1; 
			}
			// 更换显示状态
			$(ths).find("div[class='inShow']").removeClass("inShow").addClass("hidden");
			//alert($(ths).find("div[space_type_num="+current_type+"]").html());
			$(ths).find("div[leaving_time_num="+current_state+"]").removeClass("hidden").addClass("inShow");
			changeURL("leaving_time",current_state);
		}
	</script>
</body>
</html>