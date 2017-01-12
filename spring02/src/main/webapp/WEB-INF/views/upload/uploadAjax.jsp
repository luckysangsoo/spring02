<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<style type="text/css">
.fileDrop {
	width: 100%;
	height: 200px;
	border: 1px dotted blue;
}
small {
	margin-left: 3px;
	font-weight: bold;
	color: gray
}
</style>
<script>
$(document).ready(function(){
	// 클래스가 fileDrop 인 태그
	$(".fileDrop").on("dragenter dragover", function(event){
		event.preventDefault(); // 기본 효과를 막음
	});
	//event : jquery의 이벤트
	$(".fileDrop").on("drop", function(event){
		// 기본 효과음 막음
		event.preventDefault();
		// 드래그된 파일 정보
		var files = event.originalEvent.dataTransfer.files;
		// 첫번째 파일
		var file=files[0];
		console.log(file);
		
		// ajax로 전달할 폼 객체( 파일 업로드시는 form이 꼭 필요하다.)
		var formData = new FormData();
		// 폼 객체에 파일 추가
		// append( "변수명", 값)
		formData.append("file", file);
		// procdssData: false => post 방식
		// contentType: false => multipart/form-data
		$.ajax({
			type: "post",
			url: "${path}/upload/uploadAjax",
			data: formData,
			dataType: "text",
			processData: false,
			contentType: false,
			success: function(data){
				alert(data);
			}
		});
	
		
	});
	
});

</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<h2>Ajax File Upload</h2>
<!-- 파일을 업로드할 영역 -->
<div class="fileDrop"></div>
<!-- 업로드된 파일 목록 -->
<div class="uploadList"></div>



</body>
</html>