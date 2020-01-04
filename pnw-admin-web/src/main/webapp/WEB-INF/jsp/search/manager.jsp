<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<jsp:include page="${pageContext.request.contextPath }/WEB-INF/jsp/common/head.jsp"></jsp:include>
	<link href="${pageContext.request.contextPath}/css/ladda-themeless.min.css" rel="stylesheet">
	
	
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
		    <!-- 中部内容区域 -->
		   <div class="contentpanel">
		    	<button id="importButton" type="button" class="btn btn-danger ladda-button" data-style="slide-down">
		    		<span class="ladda-label">导入所有停车场数据</span>
	    		</button>
		    	<button id="loading" type="button" class="btn btn-danger ladda-button" data-style="slide-down"><span class="ladda-label">slide-down</span></button>
		    	<a id="loading2" href="#" class="btn btn-primary ladda-button" data-style="expand-left">
				  <span class="ladda-label">expand-left</span>
				</a>
				
				<button id="testB" type="button" class="btn btn-danger ladda-button" data-style="slide-down">
		    		<span class="ladda-label">测 试</span>
	    		</button>  
		   </div> 
	    </div>
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
	<script src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery-ui-1.10.3.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap-typeahead.js"></script>
	<script src="${pageContext.request.contextPath}/js/modernizr.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.sparkline.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/toggles.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/retina.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.cookies.js"></script>
	<%-- <script src="${pageContext.request.contextPath}/js/ladda.jquery.min.js"></script> --%>
	<script src="${pageContext.request.contextPath}/js/spin.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/ladda.min.js"></script>
	
	
	<script src="${pageContext.request.contextPath}/js/antido.js"></script>
	<script src="${pageContext.request.contextPath}/js/antido.search.js"></script>
	<script src="${pageContext.request.contextPath}/js/custom.js"></script>
	
	<script>
	$().ready(function() {
		searchComplete("${pageContext.request.contextPath}");
	});
	
	$("#testB").click(function() {
		var loading = Ladda.create(document.querySelector('#testB'));
		loading.start();
		
	});
	
	$("#importButton").click(function() {
		var loading = Ladda.create(document.querySelector('#importButton'));
		loading.start();			
		$.ajax({
			url : "${pageContext.request.contextPath}/search/importAll",
			dataType : "text",
			type : "POST",			
			success : function(data) {
				loading.stop();
				if (data == "success") {
					$("#alert-model-title").text("数据导入成功");
					$("#alert-model-body").empty();
					var $msg = $("<p>SUCCESS</p>");
					$("#alert-model-body").append($msg);
					$('#alertModel').modal({
						keyboard : false
					})
				} else {
					$("#alert-model-title").text(	"数据导入失败");
					$("#alert-model-body").empty();
					var $msg = $("<p>请联系管理者或开发人员</p>");
					$("#alert-model-body").append($msg);
					$('#alertModel').modal({
						keyboard : false
					})
				}
				//console.log(data);
			},
			error:function() {
				loading.stop();
				$("#alert-model-title").text(	"数据导入失败");
				$("#alert-model-body").empty();
				var $msg = $("<p>请联系管理者或开发人员</p>");
				$("#alert-model-body").append($msg);
				$('#alertModel').modal({
					keyboard : false
				})
			}
		});					
	});								
	</script>
	
</body>
</html>