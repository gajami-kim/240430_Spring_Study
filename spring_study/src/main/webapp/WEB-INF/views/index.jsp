<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="layout/header.jsp"></jsp:include>

<div class="container-sm">
	<h1>
		My First Spring Project 
	</h1>
	<br>
	<c:if test="${ses.id ne null }">
		<p>
			${ses.id }님이 로그인 하셨습니다.
			<span class="badge text-bg-info">${ses.last_login }</span>
		</p>
	</c:if>
	<br>
</div>


<script type="text/javascript">
	const msg_login = `<c:out value="${msg_login}" />`;
	if(msg_login==="1") {
		alert("로그인 실패");
	}
	
	const msg_logout=`<c:out value="${msg_logout}" />`;
	if(msg_logout==="1") {
		alert("로그아웃 되었습니다.")
	}
	
	const msg_join = `<c:out value="${msg_join}" />`;
	if(msg_join==="1") {
		alert("같은 아이디가 존재합니다.")
	}
	
	const msg_delete = `<c:out value="${msg_delete}" />`;
	if(msg_delete==="1") {
		alert("회원 탈퇴 완료")
	}
	
	const msg_modify = `<c:out value="${msg_modify}" />`;
	if(msg_modify==="1") {
		alert("회원정보 수정 완료")
	}
</script>

<jsp:include page="layout/footer.jsp"></jsp:include>

