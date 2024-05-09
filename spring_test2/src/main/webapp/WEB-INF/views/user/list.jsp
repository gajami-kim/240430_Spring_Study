<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<jsp:include page="../layout/header.jsp" />
<div class="container-sm">
<h1>User List Page</h1> <br>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
	<sec:authentication property="principal.uvo.authList" var="auths"/>
		<c:forEach items="${userList }" var="uvo">
		<div class="d-inline-flex p-2">
		<div class="card" style="width: 18rem;">
		  <img src="/re/img/춘식.png" class="card-img-top" alt="...">
		  <div class="card-body h-100">
		    <h5 class="card-title">${uvo.nickName }</h5>
		    <div class="card-text">${uvo.email }</div>
		    <div class="card-text">regDate : ${uvo.regDate }</div>
		    <div class="card-text">${uvo.lastLogin }</div>
		    <c:forEach items="${uvo.authList }" var="avo">	    
			  	<div class="card-text fw-bold text-danger">${avo.auth }</div>
		    </c:forEach>
		  </div>
		</div>
		</div>
		</c:forEach>
	</sec:authorize>
</div>
<jsp:include page="../layout/footer.jsp" />