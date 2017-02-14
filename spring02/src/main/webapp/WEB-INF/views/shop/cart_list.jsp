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
	$("#btnList").click(function(){
		location.href="${path}/shop/product/list.do";
	});
});
</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<h2>장바구니 확인</h2>
<c:choose>
	<c:when test="${map.count == 0 }">
		장바구니가 비었습니다.
	</c:when>
	<c:otherwise>
		<table border="1">
			<tr>
				<th>상품명</th>
				<th>단가</th>
				<th>수량</th>
				<th>금액</th>
			</tr>
		<c:forEach var="row" items="${map.list}">
			<tr align="right">
				<td>${row.product_name}</td>
				<td><fmt:formatNumber value="${row.price}" pattern="###,###" /></td>
				<td>${row.amount}</td>
				<td><fmt:formatNumber value="${row.money}" pattern="###,###" /></td>
			</tr>
			
		</c:forEach>
		<tr>
			<td colspan="4" align="right">
				장바구니 금액 합계 : <fmt:formatNumber value="${map.sumMoney}" pattern="###,###" /><br>
				배송료 : <fmt:formatNumber value="${map.fee}" pattern="###,###" /><br>
				총합계 : <fmt:formatNumber value="${map.sum}" pattern="###,###" />
			</td>
		</tr>
		</table>
	</c:otherwise>
</c:choose>
	<button type="button" id="btnList">상품목록</button>
</body>
</html>