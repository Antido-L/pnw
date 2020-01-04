<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%-- <jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/common/head.jsp"></jsp:include> --%>
	<jsp:include page="common/head.jsp"></jsp:include>
</head>
<body  class="signin">
	<!-- 引入载入画面 -->
	<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/common/loading.jsp"></jsp:include>
	<section>
	    <div class="signinpanel">
	        <div class="row">
	            <div class="col-md-2"></div>
	            <div class="col-md-8">
                    <h4 class="nomargin">PNW后台管理系统</h4>
                    <p class="mt5 mb20" id="info">Login to access your account.</p>
                    <input type="text" class="form-control uname" placeholder="Username" name="name"/>
                    <input type="password" class="form-control pword" placeholder="Password" name="password"/>
                    <button id="submit" class="btn btn-success btn-block" onclick="javaScript:void(0)">Login</button>

	            </div>
	            <div class="col-md-2"></div>
	        </div><!-- row -->
	        
	        <div class="signup-footer">
	            <div style="margin: 0 auto;text-align: center;" class="">
	            	&copy; 2014. All Rights Reserved. Bracket Bootstrap 3 Admin Template&nbsp;&nbsp;&nbsp;&nbsp;
	                Created By: <a href="#" target="_blank">Antido</a>
	            </div>
	        </div>
	        
	    </div><!-- signin -->
	</section>
	<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/common/jsimport.jsp"></jsp:include>
	<script>
		$("#submit").click(function(){
			console.log($("input[name='name']").val());
			$.ajax({
	  			url:"${pageContext.request.contextPath}/loginCheck",
	  			type:"POST",
	  			data:{
	  				name:$("input[name='name']").val(),
	  				password:$("input[name='password']").val()
	  			},
	  			dataType:"json",
	  			success:function(data) {
	  				if(data.code == 0) window.location.href="${pageContext.request.contextPath}/";
	  				else {
	  					$("#info").css({"color":"a94442"});
	  					$("#info").html("用户名或密码错误");
	  				}
	  			}
	  		});
		});
	</script>
</body>
</html>