<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Home</title>
<%@ include file="include/header.jsp" %>
</head>
<body>
<%@ include file="include/menu.jsp" %>
<c:if test="${message == 'success'}">
<h2>${sessionScope.username}(${sessionScope.userid})님 환영합니다.</h2>
</c:if>

<P>  The time on the server is ${serverTime}. </P>
</body>
</html>
