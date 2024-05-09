<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="../layout/header.jsp" />

<div class="container-sm">
<h1>Member Join Page</h1> <br>
	<form action="/user/register" method="post">
		<div class="mb-3">
		  <label for="e" class="form-label">Email</label>
		  <button type="button" id="cheBtn" class="btn btn-info" 
		  		style="--bs-btn-padding-y: .25rem; --bs-btn-padding-x: .5rem; --bs-btn-font-size: .75rem;">중복확인</button>
		  <input type="email" class="form-control" name="email" id="email" placeholder="Email">
		</div>
		<div class="mb-3">
		  <label for="p" class="form-label">Password</label>
		  <input type="password" class="form-control" name="pwd" id="p" placeholder="Password">
		</div>
		<div class="mb-3">
		  <label for="n" class="form-label">Nick Name</label>
		  <button type="button" id="cheNickBtn" class="btn btn-info" 
		  		style="--bs-btn-padding-y: .25rem; --bs-btn-padding-x: .5rem; --bs-btn-font-size: .75rem;">닉네임 중복확인</button>
		  <input type="text" class="form-control" name="nickName" id="nickName" placeholder="NickName">
		</div>
		<button type="submit" id="joinBtn" class="btn btn-primary">회원가입</button>
	</form>
</div>


<script type="text/javascript" src="/re/js/userCheck.js"></script>

<jsp:include page="../layout/footer.jsp" />