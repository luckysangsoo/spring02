<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<a href="${path}/board/list.do">게시판</a>
<a href="${path}/upload/uploadForm">파일업로드</a>
<a href="${path}/upload/uploadAjax">파일업로드(ajax)</a>
<a href="${path}/shop/product/list.do">상품목록</a>
<a href="${path}/shop/cart/list.do">장바구니목록</a>

<c:choose>
	<c:when test="${sessionScope.userid ==null }">
		<a href="${path}/member/login.do">로그인</a>
	</c:when>
	<c:otherwise>
	    ${sessionScope.username}님이 로그인중입니다.
		<a href="${path}/member/logout.do">로그아웃</a>
	</c:otherwise>
</c:choose>

<hr>
