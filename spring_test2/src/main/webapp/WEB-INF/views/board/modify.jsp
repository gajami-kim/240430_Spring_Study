<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../layout/header.jsp" />

<div class="container-sm">
<h1>Board Modify Page</h1> <br>
	<%-- <c:set value="${bdto.bvo }" var="bvo" /> --%>
	<form action="/board/modify" method="post">
		<div class="mb-3">
		  <label for="n" class="form-label">bno</label>
		  <input type="text" class="form-control" name="bno" id="n" value="${bvo.bno }" placeholder="Bno.." readonly="readonly">
		</div>
		<div class="mb-3">
		  <label for="t" class="form-label">title</label>
		  <input type="text" class="form-control" name="title" id="t" value="${bvo.title }" placeholder="Title..">
		</div>
		<div class="mb-3">
		  <label for="w" class="form-label">writer</label>
		  <input type="text" class="form-control" name="writer" id="w" value="${bvo.writer }" readonly="readonly">
		</div>
		<div class="mb-3">
		  <label for="r" class="form-label">reg date</label>
		  <input type="text" class="form-control" name="reg_date" id="r" aria-label="With textarea" value="${bvo.regDate }" readonly="readonly"></input>
		</div>
		<div class="mb-3">
		  <label for="c" class="form-label">Content</label>
		  <textarea class="form-control" name="content" id="c" aria-label="With textarea" >${bvo.content }</textarea>
		</div>
		<%-- <c:set  value="${bdto.flist }" var="flist"/>
		<div class="mb-3">
			<ul class="list-group list-group-flush">
				<!-- 파일 개수만큼 li를 반복해서 파일표시, 타입이 1인 경우에만 표시 -->
				<!-- li -> div -> img -->
				<!--    -> div -> 파일이름, 작성일, span 파일사이즈 -->
				<c:forEach items="${flist }" var="fvo">				
		  			<li class="list-group-item">
		  				<c:choose>
		  					<c:when test="${fvo.file_type > 0 }">
		  						<div>
		  							<img alt="" src="/upload/${fvo.save_dir }/${fvo.uuid }_${fvo.file_name }">
		  						</div>
		  					</c:when>
		  					<c:otherwise>
		  						<div>
		  							<!-- 파일타입이 0인 경우 아이콘 모양 하나 가져와서 넣기 -->
		  						</div>
		  					</c:otherwise>
		  				</c:choose>
	  					<div>
	  						<!-- 파일이름, 작성일, 사이즈 -->
	  						<div>${fvo.file_name }</div>
	  						${fvo.reg_date }
	  						<span class="badge text-bg-light">${fvo.file_size }Byte</span>
	  						<button type="button" data-uuid="${fvo.uuid }" data-bno=${bvo.bno } class="btn btn-outline-danger btn-sm file-x fileDelBtnC" id="fileDelBtn">X</button>
	  					</div>
		  			</li>
				</c:forEach>
		  	</ul>
		  	
		  	<!-- 파일 추가 라인 -->
		  	<div class="mb-3">
			<label for="file" class="form-label">files</label>
			<input type="file" class="form-control" name="files" id="file" multiple="multiple" style="display: none"> <br>
			<button type="button" class="btn btn-secondary" id="trigger">fileUpload</button>
			</div>
			<div class="mb-3" id="fileZone"></div> 
		</div>--%>
		<button type="submit" class="btn btn-primary" id="regBtn">수정</button>
	</form>
	<br>
	<a href="/board/list"><button type="button" class="btn btn-light">list</button></a>
</div>
<!-- <script type="text/javascript" src="/resources/js/boardModify.js"></script>
<script type="text/javascript" src="/resources/js/boardRegister.js"></script> -->
<jsp:include page="../layout/footer.jsp" />