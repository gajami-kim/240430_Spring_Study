<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../layout/header.jsp" />

<div class="container-sm">
<h1>Board Detail Page</h1> <br>
	<!-- bdto안에 bvo를 꺼냄 -->
	<c:set value="${bdto.bvo }" var="bvo" />
	<div class="mb-3">
	  <label for="n" class="form-label">bno</label>
	  <input type="text" class="form-control" name="bno" id="n" value="${bvo.bno }" readonly="readonly">
	</div>
	<div class="mb-3">
	  <label for="t" class="form-label">title</label>
	  <input type="text" class="form-control" name="title" id="t" value="${bvo.title }" readonly="readonly">
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
	  <textarea class="form-control" name="content" id="c" aria-label="With textarea" readonly="readonly">${bvo.content }</textarea>
	</div>
	<!-- file 출력 -->
	<c:set value="${bdto.flist }" var="flist" />
	<div class="mb-3">
		<ul class="list-group list-group-flush">
			<!-- 파일 개수만큼 li를 반복해서 파일표시, 타입이 1인 경우에만 표시 -->
			<!-- li -> div -> img -->
			<!--    -> div -> 파일이름, 작성일, span 파일사이즈 -->
			<c:forEach items="${flist }" var="fvo">				
	  			<li class="list-group-item">
	  				<c:choose>
	  					<c:when test="${fvo.fileType > 0 }">
	  						<div>
	  							<img alt="" src="/up/${fvo.saveDir }/${fvo.uuid }_${fvo.fileName }">
	  						</div>
	  					</c:when>
	  					<c:otherwise>
	  						<div>
	  						<!-- 일반 파일 다운로드 -->
	  							<!-- download : 다운로드를 할 수 있게 함 download="다운로드시 나타낼 이름" 괄호 값이 없으면 원본파일 이름으로 다운로드됨 -->
	  							<a href="/up/${fvo.saveDir }/${fvo.uuid }_${fvo.fileName }" download="${fvo.fileName }">
	  							<!-- 파일타입이 0인 경우 아이콘 모양 하나 가져와서 넣기 -->
	  							<svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-file-earmark-arrow-down" viewBox="0 0 16 16">
								  <path d="M8.5 6.5a.5.5 0 0 0-1 0v3.793L6.354 9.146a.5.5 0 1 0-.708.708l2 2a.5.5 0 0 0 .708 0l2-2a.5.5 0 0 0-.708-.708L8.5 10.293z"/>
								  <path d="M14 14V4.5L9.5 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2M9.5 3A1.5 1.5 0 0 0 11 4.5h2V14a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h5.5z"/>
								</svg></a>
	  						</div>
	  					</c:otherwise>
	  				</c:choose>
  					<div>
  						<!-- 파일이름, 작성일, 사이즈 -->
  						<div>${fvo.fileName }</div>
  						${fvo.regDate }
  						<span class="badge text-bg-light">${fvo.fileSize }Byte</span>
  					</div>
	  			</li>
			</c:forEach>
	  	</ul>
	</div>
	<hr>
	
	<!-- Comment Line -->
	<!-- 댓글 등록 라인 -->
	<div class="input-group mb-3">
	  <span class="input-group-text" id="cmtWriter">Tester</span>
	  <input type="text" id="cmtText" class="form-control" placeholder="Add Comment.." aria-label="Username" aria-describedby="basic-addon1">
	  <button type="button" id="cmtAddBtn" class="btn btn-secondary">댓글등록</button>
	</div>
	
	<!-- 댓글 출력 라인 -->
	<ul class="list-group" id="cmtListArea">
	  <li class="list-group-item">
	  	<div class="input-group mb-3">
	  		<div class="fw-bold">Writer</div>
	  		content
	  	</div>
	  	<span class="badge rounded-pill text-bg-primary">regDate</span>
	  </li>
	</ul>
	
	<!-- 댓글 더보기 -->
	<div>
		<button type="button" id="moreBtn" data-page="1" class="btn btn-outline-primary btn-sm" style="visibility:hidden">MORE +</button>
	</div>
	
	<!-- 모달창 라인 -->
	<div class="modal" id="myModal" tabindex="-1">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">Writer</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <input type="text" class="form-control" id="cmtTextMod">
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
	        <button type="button" class="btn btn-primary" id="cmtModBtn">수정</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<br><hr>
	<a href="/board/modify?bno=${bvo.bno }"><button type="button" class="btn btn-primary">수정</button></a>
	<a href="/board/remove?bno=${bvo.bno }"><button type="button" class="btn btn-warning">삭제</button></a>
	<br><br>
	<a href="/board/list"><button type="button" class="btn btn-secondary">list</button></a>
	<br><br>
</div>

<script type="text/javascript">
	const msg_bd_modify = `<c:out value="${msg_bd_modify}" />`;
	console.log(msg_bd_modify);
	if(msg_bd_modify=="1") {
		alert("수정완료");
	}
</script>

<script type="text/javascript">
	const bnoVal = `<c:out value="${bvo.bno}" />`;
	console.log(bnoVal);
	/* const id = `<c:out value="${ses.id}" />`; */
</script>

<script type="text/javascript" src="/re/js/boardDetailComment.js"></script>
<!-- 새로 입력받는 댓글이 없더라도 기존값을 뿌리기 위해 한번 더 호출 -->
<script type="text/javascript">spreadCommentList(bnoVal);</script>

<jsp:include page="../layout/footer.jsp" />