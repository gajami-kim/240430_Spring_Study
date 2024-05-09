<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<jsp:include page="../layout/header.jsp" />

<div class="container-sm">
<h1>User Detail/Modify Page</h1> <br>
<sec:authentication property="principal.uvo.Email" var="authEmail" />
<sec:authentication property="principal.uvo.nickName" var="authNick" />
<sec:authentication property="principal.uvo.authList" var="auths" />
<sec:authentication property="principal.uvo.regDate" var="authreg" />
<sec:authentication property="principal.uvo.lastLogin" var="authlast" />
	<form action="/user/modify" method="post">
		<div class="mb-3">
		  <label for="e" class="form-label">Email</label>
		  <input type="email" class="form-control" name="email" id="e" value="${authEmail }" readonly="readonly">
		</div>
		<div class="mb-3">
		  <label for="p" class="form-label">Password</label>
		  <input type="password" class="form-control" name="pwd" id="p" placeholder="Password">
		</div>
		<div class="mb-3">
		  <label for="n" class="form-label">Nick Name</label>
		  <input type="text" class="form-control" name="nickName" id="n" value="${authNick }">
		</div>
		<div class="mb-3">
		  <label for="n" class="form-label">Auth</label>
		  <c:forEach items="${auths }" var="auths">		  
		  <input type="text" class="form-control" name="auth" id="n" value="${auths.auth }" readonly="readonly">
		  </c:forEach>
		</div>
		<div class="mb-3">
		  <label for="n" class="form-label">Reg Date</label>
		  <input type="text" class="form-control" name="regdate" id="n" value="${authreg }" readonly="readonly">
		</div>
		<div class="mb-3">
		  <label for="n" class="form-label">Last Login</label>
		  <input type="text" class="form-control" name="lastLogin" id="n" value="${authlast }" readonly="readonly">
		</div>
		<button type="submit" class="btn btn-warning">수정</button> <br><br>
	</form>
	<a href="/user/delete?email=${authEmail }"><button type="button" class="btn btn-danger">삭제</button></a>
</div>

<jsp:include page="../layout/footer.jsp" />