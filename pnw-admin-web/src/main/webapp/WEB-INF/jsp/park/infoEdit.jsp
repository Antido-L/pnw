<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		      <h2><i class="fa fa-home"></i> 停车场 <span>编辑停车场信息</span></h2>
		      <div class="breadcrumb-wrapper">
		        <span class="label">当前位置:</span>
		        <ol class="breadcrumb">
		          <li><a href="index.html">Bracket</a></li>
		          <li class="active">停车场</li>
		          <li class="active">编辑停车场信息</li>
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
		          <h4 class="panel-title">编辑停车场基本信息表</h4>
		          <p>编辑开发中的停车场基本信息表，目前该停车场内除<code>编号</code>之外所有基本信息都可以被修改。如要修改编号信息请联系开发人员或拥有更高权限的管理者。当改变<code>停车场控制器地址</code>时可能会改变停车内已绑定的车位。请慎重改变<code>停车场状态</code>选项，在非开发模式下的停车场无法被详细编辑，当选择<code>正常使用</code>后该停车场将会被所有用户查询到。
		          <p>点击<code>新增停车场流程</code>获取详细说明</p>
		        </div>
		        <div class="panel-body " style="padding-bottom: 0px">
		         <form id="basicForm" action="${pageContext.request.contextPath}/park/updatePark" class="form-horizontal form-bordered" method="POST">
	               <div class="form-group">
                		<label class="col-sm-3 control-label">创建时间 </label>
	                	<div class="col-sm-6">
		                  <input id="created" type="text" class="form-control" value="${infoEcho.createdStr }" readonly="readonly"/>
	                	</div>
                	</div>
                	
                	<div class="form-group">
                		<label class="col-sm-3 control-label">上次编辑时间 </label>
	                	<div class="col-sm-6">
		                  <input id="updated" type="text" class="form-control" value="${infoEcho.updatedStr }" readonly="readonly"/>
	                	</div>
                	</div>
	               
	                <div class="form-group">
                  		<label class="col-sm-3 control-label">所在省/直辖市<span class="asterisk">*</span></label>
	                	<div class="col-sm-6">
			                <select id="provinceSelect" class="form-control" name="provinceId" required onchange="loadCitySelect('${pageContext.request.contextPath}')">
			                  <option value="">---省/直辖市---</option>
			                  <c:forEach items="${infoEcho.provinceEcho }" var="province">
			                  	<c:if test="${province.id != infoEcho.selectedEcho.provinceSelected}">
			                  		<option value="${province.id }">${province.name }</option>
			                  	</c:if>
			                  	<c:if test="${province.id == infoEcho.selectedEcho.provinceSelected}">
			                  		<option value="${province.id }" selected="selected">${province.name }</option>
			                  	</c:if>
			                  </c:forEach>
			                </select>
	             		</div>
	                </div>
	                
	                <div class="form-group">
                  		<label class="col-sm-3 control-label">所在市/市辖区 <span class="asterisk">*</span></label>
	                	<div class="col-sm-6">
			                <select id="citySelect" class="form-control" name="cityId" required onchange="loadDistrictSelect('${pageContext.request.contextPath}')">
			                  <option value="">---市/市辖区---</option>
			                  <c:forEach items="${infoEcho.cityEcho }" var="city">
			                  	<c:if test="${city.id != infoEcho.selectedEcho.citySelected}">
			                  		<option value="${city.id }">${city.name }</option>
			                  	</c:if>
			                  	<c:if test="${city.id == infoEcho.selectedEcho.citySelected}">
			                  		<option value="${city.id }" selected="selected">${city.name }</option>
			                  	</c:if>
			                  </c:forEach>
			                </select>
	             		</div>
	                </div>
	                
	                
	                <div class="form-group">
                  		<label class="col-sm-3 control-label">所在区/县 <span class="asterisk">*</span></label>
	                	<div class="col-sm-6">
			                <select id="districtSelect" class="form-control" name="districtId" required onchange="loadStreetSelect('${pageContext.request.contextPath}')">
			                  <option value="">---区/县---</option>
			                  <c:forEach items="${infoEcho.districtEcho }" var="district">
			                  	<c:if test="${district.id != infoEcho.selectedEcho.districtSelected}">
			                  		<option value="${district.id }">${district.name }</option>
			                  	</c:if>
			                  	<c:if test="${district.id == infoEcho.selectedEcho.districtSelected}">
			                  		<option value="${district.id }" selected="selected">${district.name }</option>
			                  	</c:if>
			                  </c:forEach>
			                </select>
	             		</div>
	                </div>
	                
	                 <div class="form-group">
                  		<label class="col-sm-3 control-label">所在街道/片区 <span class="asterisk">*</span></label>
	                	<div class="col-sm-6">
			                <select id="streetSelect" class="form-control" name="streetId" required>
			                  <option value="">---街道/片区---</option>
			                  <c:forEach items="${infoEcho.streetEcho }" var="street">
			                  	<c:if test="${street.id != infoEcho.selectedEcho.streetSelected}">
			                  		<option value="${street.id }">${street.name }</option>
			                  	</c:if>
			                  	<c:if test="${street.id == infoEcho.selectedEcho.streetSelected}">
			                  		<option value="${street.id }" selected="selected">${street.name }</option>
			                  	</c:if>
			                  </c:forEach>
			                </select>
	             		</div>
	                </div>
	                
	                <div class="form-group">
	                  <label class="col-sm-3 control-label">停车名称 <span class="asterisk">*</span></label>
	                  <div class="col-sm-6">
	                    <input type="text" name="name" id="parkName" class="form-control" value="${infoEcho.parkName }" placeholder="请输入请车场名称" required maxlength="30"/>
	                  </div>
	                  <input type="hidden" id="parkId" name="parkId" value="${infoEcho.parkId }"/>
	                </div>
	                
	                <div class="form-group">
	                	<label class="col-sm-3 control-label">停车场类型<span class="asterisk">*</span></label>
	                 	<div class="col-sm-6" id="radio-parkType">
	                 		<c:forEach items="${infoEcho.parkTypeDict }" var="type">
	                 			<c:if test="${type.item_code == infoEcho.parkType}">
	                 				<div class="rdio rdio-success">
			                        	<input type="radio" name="parkType" value="${type.item_code }" id="radio-parkType-${type.item_code }" checked="checked"/>
			                        	<label for="radio-parkType-${type.item_code }">${type.item_name }</label>
	                      			</div>
	                 			</c:if>
	                 			<c:if test="${type.item_code != infoEcho.parkType}">
		                 			<div class="rdio rdio-success">
				                        <input type="radio" name="parkType" value="${type.item_code }" id="radio-parkType-${type.item_code }"/>
				                        <label for="radio-parkType-${type.item_code }">${type.item_name }</label>
		                      		</div>
	                      		</c:if>
	                 		</c:forEach>
	                    </div>
		            </div>
		            
		            <div class="form-group">
	                  <label class="col-sm-3 control-label">设计车位数 <span class="asterisk">*</span></label>
	                  <div class="col-sm-6">
	                    <input type="text" name="designCount" value="${infoEcho.designCount }" class="form-control" placeholder="请输入设计车位个数 " required number="true"/>
	                  </div>
	                </div>
	                
	                <div class="form-group">
                		<label class="col-sm-3 control-label">已绑定车位数 </label>
	                	<div class="col-sm-6">
		                  <input id="" type="text" name="" value="${infoEcho.workingCount }" class="form-control" readonly="readonly"/>
	                	</div>
                	</div>
                	
                	<div class="form-group">
	                	<label class="col-sm-3 control-label">场内车位模型 <span class="asterisk">*</span></label>
	                	<div class="col-sm-2">
		                	<select id="modDirect" class="form-control" name="modDirect" required>
				                  <option value="">---模型方向---</option>
				                  <option value="0">---横向---</option>
				                  <option value="1">---纵向---</option>
			                </select>
		                </div>
		                <div class="col-sm-1">
		                  <input id="modRow" type="text" name="modRow" class="form-control" placeholder="行个数" required number="true" maxlength="10"/>
	                	</div>
		                <div class="col-sm-1">
		                  <input id="modCol" type="text" name="modCol" class="form-control" placeholder="列个数" required number="true" maxlength="10"/>
		                </div>
	                	<button type="button" class="col-sm-2 btn btn-success" onclick="makeMod()">生成模型 </button>
	                	<input id="modInfo" type="hidden" name="modInfo"/>
		           </div>
		           
		           <!-- 模拟图容器 -->
		           <div class="form-group">
		           		<div class="col-sm-2"></div>
		           		<div class="col-sm-8 mod-space dragscroll hidden"></div>
		           		<div class="col-sm-2">
		           		</div>
					</div>
		            
					<c:if test="${infoEcho.isFree }">
		            <div class="form-group">
						<label class="col-sm-3 control-label">停车场收费 <span class="asterisk">*</span></label>
						<div class="col-sm-6">
							<div class="rdio rdio-success">
		                        <input type="radio" name="isFree" value="1" id="radioIsFree" checked="checked"/>
		                        <label for="radioIsFree">免费</label>
	                      	</div>
	                      	<div class="rdio rdio-danger">
		                        <input type="radio" name="isFree" value="0" id="radioNotFree" />
		                        <label for="radioNotFree">不免费</label>
	                      	</div>
                      	</div>
                	</div>
                	
                	<div class="form-group">
                		<label class="col-sm-3 control-label">不计费时间(分钟) <span class="asterisk">*</span></label>
	                	<div class="col-sm-6">
		                  <input id="freeTime" type="text" name="freeTime" placeholder="请输入不计费时间" class="form-control" disabled="" required number="true"/>
	                	</div>
                	</div>
                	
                	<div class="form-group">
	                  <label class="col-sm-3 control-label">停车价格(元/小时) <span class="asterisk">*</span></label>
	                  <div class="col-sm-6">
	                    <input id="price" type="text" name="price" class="form-control" placeholder="请输入停车价格" disabled="" required number="true"/>
	                  </div>
	                </div>
	                </c:if>
	                
	                <c:if test="${!infoEcho.isFree }">
		            <div class="form-group">
						<label class="col-sm-3 control-label">停车场收费 <span class="asterisk">*</span></label>
						<div class="col-sm-6">
							<div class="rdio rdio-success">
		                        <input type="radio" name="isFree" value="1" id="radioIsFree"/>
		                        <label for="radioIsFree">免费</label>
	                      	</div>
	                      	<div class="rdio rdio-danger">
		                        <input type="radio" name="isFree" value="0" id="radioNotFree" checked="checked"/>
		                        <label for="radioNotFree">不免费</label>
	                      	</div>
                      	</div>
                	</div>
                	
                	<div class="form-group">
                		<label class="col-sm-3 control-label">不计费时间(分钟) <span class="asterisk">*</span></label>
	                	<div class="col-sm-6">
		                  <input id="freeTime" type="text" name="freeTime" value="${infoEcho.freeTime }" placeholder="请输入不计费时间" class="form-control" required number="true"/>
	                	</div>
                	</div>
                	
                	<div class="form-group">
	                  <label class="col-sm-3 control-label">停车价格(元/小时) <span class="asterisk">*</span></label>
	                  <div class="col-sm-6">
	                    <input id="price" type="text" name="price" value="${infoEcho.price }" class="form-control" placeholder="请输入停车价格" required number="true"/>
	                  </div>
	                </div>
	                </c:if>
	                
	                <div class="form-group">
		              <label class="col-sm-3 control-label">停车场位置描述信息  <span class="asterisk">*</span></label>
		              <div class="col-sm-6">
		                <textarea id="textDesc" class="popovers form-control" rows="3" name="parkPositionDesc" 
		                placeholder="请输入停车场位置描述信息" data-toggle="popover" data-placement="top" data-original-title="直观描述停车场位置信息（不要超过120字）" 
		                data-content="例如：铁西区沈辽西路111号，沈阳工业大学中央小区。东门进入右转信息院楼前..." data-trigger="click" required maxlength="120">${infoEcho.positionDesc }</textarea>
		              </div>
		            </div>
		            
		            <div class="form-group">
	                  <label class="col-sm-3 control-label">经纬度(精确到小数点后6位) <span class="asterisk">*</span></label>
	                  <div class="col-sm-2">
	                    <input id="east" type="text" name="east" value="${infoEcho.east }" class="form-control" placeholder="东经" required readonly="true" number="true" maxlength="10"/>
	                  </div>
	                  <div class="col-sm-2">
	                    <input id="north" type="text" name="north" value="${infoEcho.north }" class="form-control" placeholder="北纬" required readonly="true" number="true" maxlength="10"/>
	                  </div>
	                  <button type="button" class="col-sm-2 btn btn-danger" onclick="makeParkMarker()">放置标记在地图中心 </button>
	                </div>
	                
	                 <!-- 地图 -->
	                 <div class="form-group">
	                  <div class="col-sm-2"></div>
	                  <div class="col-sm-8">
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
						<div class="col-sm-2"></div>
	                 </div>
		            
		            <div class="form-group">
						<label class="col-sm-3 control-label">选择负责人 <span class="asterisk">*</span></label>
						<div class="col-sm-6">
							<div class="rdio rdio-success">
		                        <input type="radio" name="radioAdmin" value="0" id="radioCurrentAdmin" checked="checked"/>
		                        <label for="radioCurrentAdmin">选择当前负责人:<a id="defaultParkAdminName" href="#">${infoEcho.adminName }</a></label>
	                      	</div>
	                      	<!-- <div class="rdio rdio-danger">
		                        <input type="radio" name="radioAdmin" value="1" id="radioNoAdmin" />
		                        <label for="radioNoAdmin">暂不选择（上线前必须拥有负责人）</label>
	                      	</div> -->
	                      	<div class="rdio rdio-warning">
		                        <input type="radio" name="radioAdmin" value="2" id="radioOtherAdmin" />
		                        <label for="radioOtherAdmin">选择区域其他负责人</label>
	                      	</div>
	                      	<select id="adminSelect" class="form-control hidden" name="selectParkAdmin">
			                  <option value="">---本市其他负责人---</option>
			                </select>
                      	</div>
                      	<input type="hidden" id="defaultParkAdmin" name="defaultParkAdmin" value="${infoEcho.adminId }"/>
                	</div>
                	
                	<div class="form-group">
						<label class="col-sm-3 control-label">停车场状态 <span class="asterisk">*</span></label>
						<div class="col-sm-6">
							<c:forEach items="${infoEcho.stateDict }" var="state">
								<c:if test="${state.item_code != infoEcho.state}">
								<div class="rdio rdio-danger">
			                        <input type="radio" name="state" value="${state.item_code }" id="radio-state-${state.item_code }"/>
			                        <label for="radio-state-${state.item_code }">${state.item_name }</label>
		                      	</div>
		                      	</c:if>
		                      	<c:if test="${state.item_code == infoEcho.state}">
								<div class="rdio rdio-danger">
			                        <input type="radio" name="state" value="${state.item_code }" id="radio-state-${state.item_code }" checked="checked"/>
			                        <label for="radio-state-${state.item_code }">${state.item_name }</label>
		                      	</div>
		                      	</c:if>
	                      	</c:forEach>
                      	</div>
                	</div>
                	
                	<div class="form-group">
	                  <label class="col-sm-3 control-label">停车场控制器地址</label>
	                  <div class="col-sm-5" style="padding-left: 10px">
		                <input type="text" name="serviceIp" value="${infoEcho.serviceIp }" placeholder="请输入控制器IP地址" id="serviceIp" class="form-control" required isIpAddress="true"/>
	                  </div>
					  	<div class="col-sm-1 btn-demo">
						 <!--  <button id="connTestButton" type="button" class="btn btn-danger ladda-button" data-style="slide-down">
					    	<span class="ladda-label">测 试</span>
				    	  </button> -->
				    	  
				    	  <button id="connTestButton" type="button" class="btn btn-danger ladda-button" data-style="slide-down">
				    		<span class="ladda-label">测 试</span>
			    		</button>
					  </div>
	                </div>
	                
		          </form>
		        </div><!-- panel-body结束 -->
		        <div class="panel-footer">
				 <div class="row">
					<div class="col-sm-2"></div>
                    <button class="col-sm-3 btn btn-success" id="cancelBtn">取消</button>
                    <div class="col-sm-2"></div>
                    <button class="col-sm-3 btn btn-primary" id="submitBtn">提交</button>
		 		</div>
			   </div><!-- panel-footer -->
			</div><!-- panel结束 -->
   		</div><!-- 中部内容区域结束 --> 
	</section>
	
	<!-- item双击弹出框 -->
	<div class="modal fade bs-example-modal-sm" id="itemModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog modal-sm" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="item-model-title">Modal title</h4>
	      </div>
	      <div class="modal-body" id="item-model-body">
	      	<input type="hidden" id="checkedItemX"/>
	      	<input type="hidden" id="checkedItemY"/>
	      	<div class="rdio rdio-primary">
              <input type="radio" name="addCarSlot" value="0" id="addEmpty"/>
              <label for="addEmpty">设置为空白</label>
           	</div>
	        <div class="rdio rdio-success">
              <input type="radio" name="addCarSlot" value="1" id="addCarSlot"/>
              <label for="addCarSlot">设置车位槽</label>
           	</div>
           	<div class="rdio rdio-danger">
              <input type="radio" name="addCarSlot" value="2" id="addCarUnable" />
              <label for="addCarUnable">设置不可用</label>
           	</div>
           	
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="button" class="btn btn-primary" id="model-submit" data-dismiss="modal">确认更改</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	
	<!-- alert -->
	<div class="modal fade bs-example-modal-sm" id="alertModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog modal-sm" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h3 class="modal-title" >提示信息</h3>
	      </div>
	      <div class="modal-body" id="item-model-body">
           	<h4 class="modal-title" id="alert-model-content">请输入正确的模型信息</h4>
	      </div>
	    </div>
	  </div>
	</div>
	
	<script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
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
	<script src="${pageContext.request.contextPath}/js/jquery.mousewheel.js"></script>
	<script src="${pageContext.request.contextPath}/js/dragscroll.js"></script>
	<script src="${pageContext.request.contextPath}/js/custom.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/messages_zh.min.js"></script>	
	
	<!-- 按钮载入动画 -->
	<script src="${pageContext.request.contextPath}/js/spin.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/ladda.min.js"></script>
	
	<script src="${pageContext.request.contextPath}/js/antido.search.js"></script>
	<script src="${pageContext.request.contextPath}/js/antido.map.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/getscript?v=2.0&ak=yI43GzysGKGcADFGUo6etBG6kMbmbO7I"></script>
	
	<script>
		$().ready(function() {
			searchComplete("${pageContext.request.contextPath}");
		});
	</script>
	<script>
		//1.新增停车场基本信息
		//2.绑定停车场新增车位信息
			//新增车位基本信息
			//编辑车位位置信息
			//配置节点
			//校验上线
		//3.补全停车场信息,校验上线
		//**************************************************//

		/*页面加载完毕*/
		$().ready(function() {
			//新增IP地址校验规则
		    jQuery.validator.addMethod("isIpAddress", function(value, element) {   
		        //var ipRex = /^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$/;
		        var ipRex = /^((25[0-5]|2[0-4]\d|[01]?\d\d?)($|(?!\.$)\.)){4}$/;
		        return this.optional(element) || (ipRex.test(value));
		    }, "请填写正确的ip地址格式");
		    
			//初始化表单校验
		    var validator = $("#basicForm").validate({
		    	submitHandler:function(form){
		    		//输入modInfo
		    		var row = $("#modRow").val();
		    		var col = $("#modCol").val();
		    		var info = "";
		    		for(var i = 0 ; i < row ; i++) {
		    			for(var j = 0 ; j < col ; j++) {
		    				var type = $("#item-"+i+"-"+j).attr("itemType");
		    				if(type == undefined) {
		    					info = info + "0,"; // 默认补0
		    				} else {
		    					info = info + type + ",";
		    				}
			    		}
		    		}
		    		$("#modInfo").val(info);
		            form.submit();
	        	}	
    		});
			
			//回显当前区域管理员下拉选数据
		    var streetId = $("#streetSelect").find("option:selected").val();
			$.ajax({
	  			url:"${pageContext.request.contextPath}/parkAdmin/adminJsonByStreet",
	  			type:"POST",
	  			data:{"streetId":streetId},
	  			dataType:"json",
	  			success:function(data) {
	  				//数据中包含当前区域默认管理员对象和当前区域内所有管理员列表
	  				$("#defaultParkAdminName").text(data.defaultAdmin.name);
	  				$("#defaultParkAdmin").val(data.defaultAdmin.id);
	  				//加载当前街道管理员下拉选数据
	  				var $select = $("#adminSelect")
	  				$select.empty();
	  				$select.append("<option value=''>---当前区域负责人---</option>");
	  				for(var i = 0 ; i < data.adminList.length ; i++) {
						var $option = "<option value='"+data.adminList[i].id+"'>"+data.adminList[i].name+"---"+data.adminList[i].phone+"</option>"; 					
	  					$select.append($option);
	  				}
	  			}
	  			
	  		});
			
			//回显模拟图数据
			var parkId = $("#parkId").val();
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
					
					itemMaker(data.items, data.direction);
	  			}
			});
			
			//创建和初始化地图
			initMap(0.6);
			markerEcho();
			
		}); //页面加载完毕 结束
		
		//在地图上回显位置
		function markerEcho() {
			var lng = $("#east").val();
			var lat = $("#north").val();
			
			console.log(lng+"，"+lat);
			//将地图的中心点设置在停车场
			map.panTo(new BMap.Point(lng,lat));
			
			var markerArr = [
			     {title:"停车场位置",content:"将停车场的位置设置到此处",point:lng+"|"+lat,isOpen:0,icon:{w:18,h:27,l:36,t:22,x:1,lb:10}}
			];
			var markerList = addMarker(markerArr);
			$("#east").val(markerList[0].point.lng)
			$("#north").val(markerList[0].point.lat)
			
			//map.panTo(new BMap.Point(lng,lat)); 先放置标注，再移动地图会出现错位，原因未知
			//为坐标添加拖动事件
			markerList[0].enableDragging();
			//监听标记的落点
			markerList[0].addEventListener("dragend", function(e){   
				$("#east").val(markerList[0].point.lng)
				$("#north").val(markerList[0].point.lat)
			    //console.log("当前位置：" + e.point.lng + ", " + e.point.lat);    
			})
		}
		
		//添加停车场位置选择标注
		function makeParkMarker() {
			//清除所有覆盖物
			map.clearOverlays();
			//获取中心点
			var point = map.getCenter();
			
			var markerArr = [
			     {title:"停车场位置",content:"将停车场的位置设置到此处",point:point.lng+"|"+point.lat,isOpen:0,icon:{w:18,h:27,l:36,t:22,x:1,lb:10}}
			];
			//放置坐标
			var markerList = addMarker(markerArr);
			//为坐标添加拖动事件
			markerList[0].enableDragging();
			//监听标记的落点
			markerList[0].addEventListener("dragend", function(e){    
				$("#east").val(markerList[0].point.lng)
				$("#north").val(markerList[0].point.lat)
			})
		}
		
		/*初始化模型*/
		function makeMod() {
			var row = $("#modRow").val();
			var col = $("#modCol").val();
			var direction = $("#modDirect").val();
			//空数据提醒
			if(row == "" || col == "" || direction == "") {
				$('#alertModel').modal({
					  keyboard: false
				})
				return;
			}
			$(".mod-space").removeClass("hidden");
			$(".mod-space").empty();
			if(direction == "0") {
				modMaker(100,60,row,col,10);
			} else{
				modMaker(60,100,row,col,10);
			}
			
			var parkId = $("#parkId").val();
			//改变尺寸后异步加载历史数据
			$.ajax({
				url:"${pageContext.request.contextPath}/park/getParkMapByParkId",
	  			type:"POST",
	  			data:{"parkId":parkId},
	  			dataType:"json",
	  			success:function(data) {
	  				if(data == null) {
	  					return;
	  				}
					itemMaker(data.items, data.direction);
	  			}
			});
		};
		
		/*车槽输入按钮*/
		$("#model-submit").click(function() {
			var state = $("input[name='addCarSlot']:checked").val();
			var direct = $("#modDirect").val();
			//非空校验
			if(state == undefined || direct == "") {
				$('#alertModel').modal({
					  keyboard: false
				})
				return;
			}
			var x = $("#checkedItemX").val();
			var y = $("#checkedItemY").val();
			
			//清空所选item
			$("#item-"+x+"-"+y).removeAttr("itemType");
			$("#item-"+x+"-"+y).empty();
			
			var $img = null;
			var $div = $("<div class='mod-item-empty'></div>");
			//根据选择改变对应item的样式
			if(state == '0') { //清空
				return ;
			} else if(state == '1') { //设置为空车位槽
				if($("#modDirect").val() == "0") {
					$img = $("<img class='mod-item-img' src='${pageContext.request.contextPath}/images/car-empty-x.png'/>")
				} else {
					$img = $("<img class='mod-item-img' src='${pageContext.request.contextPath}/images/car-empty-y.png'/>")
				}
				$("#item-"+x+"-"+y).attr("itemType","1");
			} else if(state == '2') { //设置为禁止使用
				
				if($("#modDirect").val() == "0") {
					$img = $("<img class='mod-item-img' src='${pageContext.request.contextPath}/images/car-unable-x.png'/>")
				} else {
					$img = $("<img class='mod-item-img' src='${pageContext.request.contextPath}/images/car-unable-y.png'/>")
				}
				$("#item-"+x+"-"+y).attr("itemType","2");
			}
			$div.append($img);
			$("#item-"+x+"-"+y).append($div);
			
		});
		
		/*表单提交*/
		$("#submitBtn").click(function(){
			$("#basicForm").submit();
		});
		
		/*返回按钮*/
		$("#cancelBtn").click(function(){
			window.location.href="${pageContext.request.contextPath}/park/developing";
		});
		
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
			$("#checkedItemX").val(x);
			$("#checkedItemY").val(y);
			$("#item-model-title").text("第"+x+"行，"+"第"+y+"列");
			$('#itemModel').modal({
			  keyboard: false
			})
			
		};
		
		/*根据是否免费转换价格和免费时间输入框的可选状态*/
		$("input:radio[name='isFree']").change(function (){ 
			var notFree = $("#radioNotFree").attr("checked");
			var isFree = $("#radioIsFree").attr("checked");
			
			if(notFree == "checked") {
				$("#freeTime").removeAttr("disabled");
				//在切换可选状态时绑定表单校验
				$("#freeTime").attr("required","true");
				$("#freeTime").attr("number","true");
				
				$("#price").removeAttr("disabled");
				$("#price").attr("required","true");
				$("#price").attr("number","true");
			}
			if(isFree == "checked") {
				$("#freeTime").val("");
				$("#freeTime").attr("disabled","");
				$("#freeTime").removeAttr("required");
				
				$("#price").val("");
				$("#price").attr("disabled","");
				$("#price").removeAttr("required");
			}
		});
		
		/*根据管理员radio的选择情况调整下拉选的显示状态*/
		$("input:radio[name='radioAdmin']").change(function (){ 
			var other = $("#radioOtherAdmin").attr("checked");
			if(other == "checked") {
				$("#adminSelect").removeClass("hidden");
				$("#adminSelect").attr("required","true");
			} else {
				//$("#adminSelect").empty();
				$("#adminSelect").addClass("hidden");
				$("#adminSelect").removeAttr("required");
			}
		});
		
		/*街道一级选择,选择完毕后加载当前街道管理员*/
		$("#streetSelect").change(function() {
			//一个区域有一个负责人
			//停车场也有一个负责人
			//停车场负责人可能是区域负责人 也可能是其他负责人
			//负责人的基本单位是停车场
			var streetId = $("#streetSelect").find("option:selected").val();
			$.ajax({
	  			url:"${pageContext.request.contextPath}/parkAdmin/adminJsonByStreet",
	  			type:"POST",
	  			data:{"streetId":streetId},
	  			dataType:"json",
	  			success:function(data) {
	  				//数据中包含当前区域默认管理员对象和当前区域内所有管理员列表
	  				$("#defaultParkAdminName").text(data.defaultAdmin.name);
	  				$("#defaultParkAdmin").val(data.defaultAdmin.id);
	  				//加载当前街道管理员下拉选数据
	  				var $select = $("#adminSelect")
	  				$select.empty();
	  				$select.append("<option value=''>---当前区域负责人---</option>");
	  				for(var i = 0 ; i < data.adminList.length ; i++) {
						var $option = "<option value='"+data.adminList[i].id+"'>"+data.adminList[i].name+"---"+data.adminList[i].phone+"</option>"; 					
	  					$select.append($option);
	  				}
	  			}
	  			
	  		});
		});
		
		/*
		 控制器地址连接测试单击事件		
		*/
		$("#connTestButton").click(function() {
			var loading = Ladda.create(document.querySelector('#connTestButton'));
			loading.start();
			var address = $("#serviceIp").val();
			$.ajax({
	  			url:"${pageContext.request.contextPath}/conn/connTest",
	  			type:"POST",
	  			data:{"address":address},
	  			dataType:"json",
	  			success:function(data) {
	  				loading.stop();
	  				console.log(data);
	  				$("#alert-model-content").text("连接正常");
	  			},
	  			error:function() {
	  				$("#alert-model-content").text("连接异常");
	  				loading.stop();
	  			}
	  			
	  		});
			
			$('#alertModel').modal({
				  keyboard: true
			})
		});
		
		
	</script>
</body>
</html>