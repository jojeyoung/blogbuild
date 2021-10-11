<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>

<div class="container">
	<form onsubmit="update(event, ${boardEntity.id})" >
	  <div class="form-group">
	    <input id="title" type="text"  value="${boardEntity.title}" class="form-control" placeholder="Enter title"  >
	  </div>
	  <div class="form-group">
	  	<textarea id="content" class="form-control" rows="5"  >
	  		${boardEntity.content}
	  	</textarea>
	  </div>
	  <button type="submit" class="btn btn-primary">수정하기</button>
	</form>
</div>
  <script>
       async function update(event, id){ 
    	
    	   event.preventDefault();
    
    	   let boardUpdateDto = {
    			   title: document.querySelector("#title").value,
    			   content: document.querySelector("#content").value,
    	   };
       
       		console.log(boardUpdateDto);
       		console.log(JSON.stringify(boardUpdateDto));
       	
     
       		let response = await fetch("http://localhost:8080/board/"+id, {
       			method: "put",
       			body: JSON.stringify(boardUpdateDto),
       			headers: {
       				"Content-Type": "application/json; charset=utf-8"
       			}
       		});
       		
       		let parseResponse = await response.json(); 
       		
       	
       		console.log(parseResponse);
       		
       		if(parseResponse.code == 1){
       			alert("업데이트 성공");
       			location.href = "/board/"+id
       		}else{
       			alert("업데이트 실패");
       			alert("업데이트 실패 : "+parseResponse.msg);
       		}
       }
  
        $('#content').summernote({
             height: 350
        });
  </script>
<%@ include file="../layout/footer.jsp" %>
