<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="../layout/header.jsp" />

<div class="container-sm">
<h1>Member Join Page</h1>
	<form action="/user/register" method="post">
		<div class="mb-3">
		  <label for="e" class="form-label">Email</label>
		  <input type="email" class="form-control" name="email" id="e" placeholder="Email">
		</div>
		<div class="mb-3">
		  <label for="p" class="form-label">Password</label>
		  <input type="password" class="form-control" name="pwd" id="p" placeholder="Password">
		</div>
		<div class="mb-3">
		  <label for="n" class="form-label">Nick Name</label>
		  <input type="text" class="form-control" name="nickName" id="n" placeholder="NickName">
		</div>
		<button type="submit" class="btn btn-info">회원가입</button>
	</form>
</div>

<jsp:include page="../layout/footer.jsp" />