<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<script type="text/javascript">
$(document).ready(function(){
	
	//listReply(); //댓글 목록 불러오기
	listReply2();
	
	//댓글 쓰기 버튼
	$("#btnReply").click(function(){
		var replytext=$("#replytext").val();
		var bno="${dto.bno}"
		var param="replytext="+replytext+"&bno="+bno;
		$.ajax({
			type: "post",
			url: "${path}/reply/insert.do",
			data: param,
			success: function(){
				alert("댓글이 등록 되었습니다.");
				listReply2();
			}
		});	
	});
	
	$("#btnList").click(function(){
		location.href="${path}/board/list.do?curPage=${curPage}&search_option=${search_option}&keyword=${keyword}";
	});
	
	
	$("#btnDelete").click(function(){
		if(confirm("삭제하시겠습니까?")){
			document.form1.action="${path}/board/delete.do"
			document.form1.submit();
		}
	});
	
	$("#btnUpdate").click(function(){
		//var writer=document.form1.writer.value;
		var content=document.form1.content.value;
		var title=document.form1.title.value;
        //var title=$("#title").val();
		/* if(writer==""){
        	alert("작성자를 입력하세요");
        	document.form.writer.focus();
        	return;
        } */
        if(content==""){
        	alert("내용을 입력하세요");
        	document.form.writer.focus();
        	return;
        }
        if(title==""){
        	alert("제목을 입력하세요");
        	document.form.writer.focus();
        	return;
        }
        //수정 주소
        document.form1.action="${path}/board/update.do"
        //폼에 입력한 데이터를 서버로 전송 함.
        document.form1.submit();
	});
});

function listReply(){
	$.ajax({
		type: "get",
		url: "${path}/reply/list.do?bno=${dto.bno}",
		success: function(result){
// responseText가 result에 저장됨
			console.log(result);
			$("#listReply").html(result);
		}
	});
}

function listReply2(){
	$.ajax({
		type: "get",
		contentType: "application/json",
		url: "${path}/reply/list_json.do?bno=${dto.bno}",
		success: function(result){
			console.log(result);
			var output="<table>";
			for( var i in result) {
				date=new Date(parseInt(result[i].regdate));
				year=date.getFullYear();
				month=date.getMonth();
				day=date.getDate();
				hour=date.getHours();
				minute=date.getMinutes();
				second=date.getSeconds();
				strDate = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
					
				output+="<tr>";
				output+="<td>"+result[i].username;
				output+="( "+strDate +")<br>";
				output+=result[i].replytext+"</td>";
				output+="</tr>";
			}
			output+="</table>";
			console.log(output);
			$("#listReply").html(output);
		}
	});
}
</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<h2>게시물 보기</h2>
<form name="form1" method="post">
<div>
	작성일자 :<fmt:formatDate value="${dto.regdate}"
	         pattern="yyyy-MM-dd a HH:mm:ss"/>
</div>
<div>
	조회수 : ${dto.viewcnt}
</div>
<div>
    제목
    <input name="title" id="title" 
    value="${dto.title}"
    size="80" placeholder="제목을 입력하세요">	
</div>
<div>
    내용
    <textarea name="content" rows="3" cols="80"
    placeholder="내용을 입력하세요">${dto.content}</textarea>	
</div>
<div>
    이름 : ${dto.username}
</div>
<div style="width:700px; text-align: center;">
	<input type="hidden" name="bno" value="${dto.bno}">
<!-- 본인의 게시물만 수정,삭제가 가능하도록 처리 -->
<c:if test="${sessionScope.userid == dto.writer}">
	<button type="button" id="btnUpdate">수 정</button>
	<button type="button" id="btnDelete">삭 제</button>
</c:if>	
	<button type="button" id="btnList">목록</button>
</div>
</form>

<div style="width:700px; text-align: center;">
	<br>
	<c:if test="${sessionScope.userid != null }">
		<textarea rows="5" cols="80" id="replytext"
		placeholder="댓글을 작성...."></textarea>
		<br>
		<button type="button" id="btnReply">댓글쓰기</button>
	</c:if>
</div>
<!-- 댓글 목록 출력 -->
<div id="listReply"></div>
</body>
</html>