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
	// originalEvent 
	$(".fileDrop").on("drop", function(event){
		// 기본 효과 막음
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
				//alert(data);
				// data 는  full fileName
				console.log(data);
				var str="";
				if(checkImageType(data)) { // 이미지 파일일 경우
					str="<div><a href='${path}/upload/displayFile?fileName="
						+getImageLink(data)+"'>";
					str+="<img src='${path}/upload/displayFile?fileName="
						+data+"'></a></div>";
				}else{ // 이미지 파일이 아닌 경우
					str="<div>"+getOriginalName(data)+"</div>";	
				}
				$(".uploadedList").append(str);
			}
		});
	});
});

function getOriginalName(fileName){
	if( checkImageType(fileName)){
		return; // 이미지 파일이면 리턴
	}
	// uuid를 제외한 원래 파일 이름을 리턴
	var idx=fileName.indexOf("_")+1;
	return fileName.substr(idx);
}
function getImageLink(fileName){
	if(!checkImageType(fileName)){ // 이미지 형식이 아니면
		return; // 함수 종료
	}
	// /2017/01/26/s_970c8a15-f5d0-4791-8f59-bb8c2434b9b8_20170119_171308.png
	var front=fileName.substr(0,12); // 년월일 경로 추출 제거
	var end=fileName.substr(14); // s_ 제거, 14번 이후
	console.log(front);
	console.log(end);
	return front+end;
}
function checkImageType(fileName){
	// i : ignore case(대소문자 무관)
	var pattern= /jpg|gif|png|jpeg/i; // 정규표현식
	return fileName.match(pattern);   // 규칙에 맞으면 true
}

</script>

</head>
<body>
<%@ include file="../include/menu.jsp" %>
<h2>Ajax File Upload</h2>
<!-- 파일을 업로드할 영역 -->
<div class="fileDrop"></div>

<!-- 업로드된 파일 목록 -->
<div class="uploadedList"></div>


</body>
</html>