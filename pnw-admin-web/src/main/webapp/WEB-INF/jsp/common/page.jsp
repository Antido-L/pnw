<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- 分页 -->
<!-- TODO:解决页码个数硬编码问题 -->
<nav aria-label="Page navigation" style="float:right">
  <ul class="pagination nomargin">
	<c:if test="${pageBean.currentPage > 1}">							  
	    <li>
	      <%-- <a href="${pageContext.request.contextPath }${pagePath }?page=${pageBean.currentPage-1}" aria-label="Previous">
	        <span aria-hidden="true">&laquo;</span>
	      </a> --%>
	      
	      <%-- <a href="javaScript:void(0)" onclick="changePage('${pageBean.currentPage-1}')" aria-label="Previous">
	        <span aria-hidden="true">&laquo;</span>
	      </a> --%>
	      
	      <a href="javaScript:void(0)" onclick="changeURL('page','${pageBean.currentPage-1}')" aria-label="Previous">
	        <span aria-hidden="true">&laquo;</span>
	      </a>
	      
	    </li>
    </c:if>
    <c:if test="${pageBean.currentPage == 1}">							  
	    <li class="disabled">
	      <span aria-label="Previous">
	        <span aria-hidden="true">&laquo;</span>
	      </span>
	    </li>
    </c:if>
    <c:if test="${pageBean.currentPage == null}">							  
	    <li class="disabled">
	      <span aria-label="Previous">
	        <span aria-hidden="true">&laquo;</span>
	      </span>
	    </li>
    </c:if>
    
    <!-- 当总页数不大于10时 -->
    <c:if test="${pageBean.totalPages <=10 }">
	    <c:forEach begin="1" end="${pageBean.totalPages }" step="1" var = "page">
		    <c:if test="${pageBean.currentPage == page}">
		   		<li class="active"><span>${page }</span></li>
		    </c:if>
		    <c:if test="${pageBean.currentPage != page}">
		   		<li><a href="javaScript:void(0)" onclick="changeURL('page','${page }')">${page }</a></li>
		    </c:if>
	    </c:forEach>
    </c:if>
     <!-- 当总页数大于10时 -->
     <c:if test="${pageBean.totalPages > 10 }">
     	<!-- 当前页<6时终止页不需要变化 默认为10 -->
     	<c:if test="${pageBean.currentPage < 6 }">
			<c:forEach begin="1" end="10" step="1" var="page">
			    <c:if test="${pageBean.currentPage == page}">
			   		<li class="active"><span>${page }</span></li>
			    </c:if>
			    <c:if test="${pageBean.currentPage != page}">
			   		<li><a href="javaScript:void(0)" onclick="changeURL('page','${page }')">${page }</a></li>
			    </c:if>
		    </c:forEach>						     	
     	</c:if>
     	<!-- 当前页>6时终止页小于总页数 保持页码总数不变 -->
     	<c:if test="${pageBean.currentPage >= 6 && pageBean.currentPage + 5 < pageBean.totalPages}">
			<c:forEach begin="${pageBean.currentPage - 5 }" end="${pageBean.currentPage + 5 }" step="1" var="page">
			    <c:if test="${pageBean.currentPage == page}">
			   		<li class="active"><span>${page }</span></li>
			    </c:if>
			    <c:if test="${pageBean.currentPage != page}">
			   		<li><a href="javaScript:void(0)" onclick="changeURL('page','${page }')">${page }</a></li>
			    </c:if>
		    </c:forEach>						     	
     	</c:if>
     	<!-- 当前页>6时终止页=总页数时 起始页=总页码数-默认显示页码数 -->
     	<c:if test="${pageBean.currentPage >= 6 && pageBean.currentPage + 5 >= pageBean.totalPages}">
			<c:forEach begin="${pageBean.totalPages - 10 }" end="${pageBean.totalPages }" step="1" var="page">
			    <c:if test="${pageBean.currentPage == page}">
			   		<li class="active"><span>${page }</span></li>
			    </c:if>
			    <c:if test="${pageBean.currentPage != page}">
			   		<li><a href="javaScript:void(0)" onclick="changeURL('page','${page }')">${page }</a></li>
			    </c:if>
		    </c:forEach>						     	
     	</c:if>
     </c:if> <!-- 当总页数大于10时 结束 -->
     
    <c:if test="${pageBean.currentPage < pageBean.totalPages}">							  
	    <li>
	      <a href="javaScript:void(0)" onclick="changeURL('page','${pageBean.currentPage+1 }')" aria-label="Next">
	        <span aria-hidden="true">&raquo;</span>
	      </a>
	    </li>
    </c:if>
    <c:if test="${pageBean.currentPage >= pageBean.totalPages}">							  
	    <li class="disabled">
	      <span aria-label="Next">
	        <span aria-hidden="true">&raquo;</span>
	      </span>
	    </li>
    </c:if>
  </ul>
</nav><!--分页 结束-->
<script>
	
</script>