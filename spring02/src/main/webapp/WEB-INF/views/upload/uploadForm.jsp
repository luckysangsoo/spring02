<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<!-- enctype default 옵션 : enctype="application/x-www-form-urlencoded" 
지금 사용할 옵션 :       enctype="multipart/form-data" 
 -->
<form action="${path}/upload/uploadForm" method="post" enctype="multipart/form-data">
<input type="file" name="file">
<input type="submit" value="업로드">
</form>
</body>
</html>