<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../layout/header.jsp" />

<div class="container-sm">
<h1>Member Login Page</h1>
	<form action="/user/login" method="post">
		<div class="mb-3">
		  <label for="i" class="form-label">Email</label>
		  <input type="text" class="form-control" name="email" id="i" placeholder="Email">
		</div>
		<div class="mb-3">
		  <label for="p" class="form-label">Password</label>
		  <input type="password" class="form-control" name="pwd" id="p" placeholder="Password">
		</div>
		<!-- 로그인 실패시 -->
		<!-- not empty : !='' : 비어있지않다면 -->
		<c:if test="${not empty param.errMsg }">
			<div class="mb-3 text-danger">
			<c:choose>
				<c:when test="${param.errMsg eq 'Bad credentials' }">
					<c:set value="이메일 혹은 비밀번호가 일치하지 않습니다." var="errText"></c:set>
				</c:when>
				<c:otherwise>
					<c:set value="관리자에게 문의해주세요" var="errText"></c:set>
				</c:otherwise>
			</c:choose>
			${errText }
			</div>
		</c:if>
		
		<button type="submit" class="btn btn-primary">로그인</button>
	</form>
</div>

<jsp:include page="../layout/footer.jsp" />