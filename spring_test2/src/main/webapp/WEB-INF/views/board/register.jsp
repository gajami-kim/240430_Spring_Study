<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
    
<jsp:include page="../layout/header.jsp" />

<div class="container-sm">
<h1>Board Register Page</h1> <br>
	<form action="/board/insert" method="post" enctype="multipart/form-data">
	<sec:authentication property="principal.uvo.nickName" var="authNick" />
		<div class="mb-3">
		  <label for="t" class="form-label">title</label>
		  <input type="text" class="form-control" name="title" id="t" placeholder="Title">
		</div>
		<div class="mb-3">
		  <label for="w" class="form-label">writer</label>
		  <input type="text" class="form-control" value="${authNick }" name="writer" id="w" placeholder="Writer" readonly="readonly">
		</div>
		<div class="mb-3">
		  <label for="c" class="form-label">content</label>
		  <textarea class="form-control" name="content" id="c" aria-label="With textarea"></textarea>
		</div>

		<!-- 파일 입력 라인 추가 -->
		<div class="mb-3">
		  <label for="file" class="form-label">files</label>
		  <input type="file" class="form-control" name="files" id="file" multiple="multiple" style="display: none"> <br>
		  <button type="button" class="btn btn-secondary" id="trigger">fileUpload</button>
		</div>
		
		<!-- 파일 목록 표시라인 -->
		<div class="mb-3" id="fileZone"></div>
				
		<button type="submit" class="btn btn-primary" id="regBtn">등록</button>
		
	</form>
</div>
<script type="text/javascript" src="/re/js/boardRegister.js"></script>

<jsp:include page="../layout/footer.jsp" />