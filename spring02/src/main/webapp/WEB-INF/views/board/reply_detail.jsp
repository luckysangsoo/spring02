<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<script>
$(document).ready(function(){
	
	// 댓글 삭제 버튼
	$("#btnReplyDelete").click(function(){
		$.ajax({
			type: "delete",
			url: "${path}/reply/delete/${vo.rno}",
			success: function(result){
				if(result=="success"){
					alert("댓글이 삭제되었습니다.");
					/* $("#modifyReply").hide(); */
					$("#modifyReply").css("visibility","hidden");
					listReply_rest("1");
				}
			}
		});
	});
	
	
	// 댓글 수정 버튼 클릭
	// put : 전체 수정, patch : 일부 수정
	$("#btnReplyUpdate").click(function(){
		
		var replytext = $("#detail_replytext").val();
		$.ajax({
			type: "put",
			url: "${path}/reply/update/${vo.rno}",
			headers: {
				"Content-Type":"application/json"
			},
			data: JSON.stringify({
				replytext : replytext
			}),
			dataType: "text",
			success: function(result){
				if(result == "success"){
					// div 숨김 처리
					/* $("#modifyReply").hide(); */
					$("#modifyReply").css("visibility","hidden");
					// 댓글 목록 갱신
					listReply_rest("1");
				}
			}		
		});	
	});
	
	$("#btnReplyClose").click(function(){
		$("#modifyReply").css("visibility","hidden");
	});
	
});
</script>
</head>
<body>
${vo.rno}<br> <!-- 댓글번호 -->
<textarea id="detail_replytext" rows="3" cols="40">${vo.replytext}</textarea>
<div style="text-align: center;">
<!-- 본인의 게시물만 수정,삭제 가능 처리 -->
<c:if test="${sessionScope.userid == vo.replyer }">
<button id="btnReplyUpdate" typr="button">수정</button>
<button id="btnReplyDelete" typr="button">삭제</button>
</c:if>
<button id="btnReplyClose" typr="button">닫기</button>
</div>
</body>
</html>