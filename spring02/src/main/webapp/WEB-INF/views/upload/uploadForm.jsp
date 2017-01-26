<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<style>
iframe {
	width: 600px;
	height: 100px;
	border: 1px;
	border-style: solid;
}
</style>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<!-- enctype="multipart/form-data" 파일 업로드 필수 옵션 -->
<!-- enctype default 옵션 : enctype="application/x-www-form-urlencoded" 
지금 사용할 옵션 :       enctype="multipart/form-data" 
 -->
<!-- target="iframe의 name" => iframe 으로 전송 -->
<form id="form1" action="${path}/upload/uploadForm" method="post" 
enctype="multipart/form-data" target="iframe1">
<input type="file" name="file">
<input type="submit" value="업로드">
</form>

<iframe name="iframe1"></iframe>
<script>
function addfilePath(msg){
	alert(msg);
	document.getElementById("form1").reset();
}
</script>


</body>
</html>