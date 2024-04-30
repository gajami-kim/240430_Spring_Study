<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="../layout/header.jsp" />

<div class="container-sm">
<h1>Member Join Page</h1>
	<form action="/member/register" method="post">
		<div class="mb-3">
		  <label for="i" class="form-label">ID</label>
		  <input type="text" class="form-control" name="id" id="i" placeholder="ID">
		</div>
		<div class="mb-3">
		  <label for="p" class="form-label">Password</label>
		  <input type="password" class="form-control" name="pw" id="p" placeholder="Password">
		</div>
		<div class="mb-3">
		  <label for="n" class="form-label">Name</label>
		  <input type="text" class="form-control" name="name" id="n" placeholder="Name">
		</div>
		<div class="mb-3">
		  <label for="e" class="form-label">Email</label>
		  <input type="email" class="form-control" name="email" id="e" placeholder="Email">
		</div>
		<div class="mb-3">
		  <label for="h" class="form-label">Home</label>
		  <input type="text" class="form-control" name="home" id="h" placeholder="Home">
		</div>
		<div class="mb-3">
		  <label for="a" class="form-label">Age</label>
		  <input type="text" class="form-control" name="age" id="a" placeholder="Age">
		</div>
		<button type="submit" class="btn btn-secondary">회원가입</button>
	</form>
</div>

<jsp:include page="../layout/footer.jsp" />