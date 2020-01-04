<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		    	<h5>添加失败</h5>
		    </div>
	    </div>
	<section>
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
</body>
</html>