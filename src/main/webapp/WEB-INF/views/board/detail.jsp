<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<c:if test = "${sessionScope.principal.id == boardEntity.user.id}">
		<a href="/board/${boardEntity.id}/updateForm" class="btn btn-warning">수정</a>
		<button class="btn btn-danger" onclick="deleteById(${boardEntity.id})">삭제</button>
	</c:if>
<script>
		
			async function deleteById(id){
				
				let response = await fetch("http://localhost:8080/board/"+id, {
					method: "delete"
				}); 
				
				let parseResponse = await response.json();
				console.log(parseResponse);
				
				if(parseResponse.code == 1){
					alert("삭제 성공");
					location.href="/";	
				} else {
					alert(parseResponse.msg);
					location.href="/";
				}
			}
		</script>
	<br /><br />
	<div>
		글 번호 : ${boardEntity.id} </span> 작성자 : <span><i>${boardEntity.user.username}</i></span>
	</div>
	<br />
	<div>
		<h3>${boardEntity.title}</h3>
	</div>
	<hr />
	<div>
		<div>${boardEntity.content}</div>
	</div>
	<hr />
	
 <!-- 댓글 쓰는 부분  -->
	<div class="card">
		
		<form action="/board/${boardEntity.id}/comment"  method="post">
			<div class="card-body">
				<textarea name="content" class="form-control" rows="1"></textarea>
			</div>
			<div class="card-footer">
				<button type="submit" id="btn-reply-save" class="btn btn-primary">등록</button>
			</div>
		</form>
	</div>
	<br />
	 <!-- 댓글 끝 -->
	
	
<%@ include file="../layout/footer.jsp"%>