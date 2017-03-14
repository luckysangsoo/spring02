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
	  <form id="form1" name="form1" method="post" action="${path}/shop/cart/update.do" }>
		<table border="1">
			<tr>
				<th>상품명</th>
				<th>단가</th>
				<th>수량</th>
				<th>금액</th>
				<th>&nbsp;</th>
			</tr>
		<c:forEach var="row" items="${map.list}" varStatus="i" >
			<tr align="right">
				<td>${row.product_name}</td>
				<td><fmt:formatNumber value="${row.price}" pattern="###,###" /></td>
				<%-- <td><input type="number" name="amount[${i.index}]" value="${row.amount}">
				    <input type="number" name="product_id[${i.index}]" value="${row.product_id}">
				</td> --%>
				<!-- 배열로 표기 했던 부분을 그냥 변수명으로 바꿉니다. 이게 쌓이면 배열로 쌓이게 됩니다. -->
				<!-- name 이름이 똑같이 반복되면 배열로 저장해서 서버로 던지게 된다. -->
				<td><input type="number" name="amount" value="${row.amount}">
				    <input type="number" name="product_id" value="${row.product_id}">
				</td>
				<td><fmt:formatNumber value="${row.money}" pattern="###,###" /></td>
				<td><a href="${path}/shop/cart/delete.do?cart_id=${row.cart_id}">삭제</a></td>
			</tr>
			
		</c:forEach>
		<tr>
			<td colspan="5" align="right">
				장바구니 금액 합계 : <fmt:formatNumber value="${map.sumMoney}" pattern="###,###" /><br>
				배송료 : <fmt:formatNumber value="${map.fee}" pattern="###,###" /><br>
				총합계 : <fmt:formatNumber value="${map.sum}" pattern="###,###" />
			</td>
		</tr>
		</table>
		<%-- <input type="hidden" name="count" value="${map.count}"> --%>
		<button type="submit" id="btnUpdate">수정</button>
	  </form>
	</c:otherwise>
</c:choose>
	<button type="button" id="btnList">상품목록</button>
</body>
</html>