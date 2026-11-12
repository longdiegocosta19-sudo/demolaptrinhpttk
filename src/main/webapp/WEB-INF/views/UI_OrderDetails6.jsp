<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head><meta charset="UTF-8"><title>UI_OrderDetails6 – Chi tiết hóa đơn</title></head>
<body>
<h1>UI_OrderDetails6 (Chi tiết hóa đơn)</h1>
<p>Mã hóa đơn: <strong><c:out value="${invoice.invoiceCode}"/></strong> –
   Ngày mua: <fmt:formatDate value="${invoice.orderDate}" pattern="dd/MM/yyyy"/> –
   Khách hàng: <c:out value="${invoice.customer.fullName}"/></p>
<c:if test="${not empty warning}"><p style="color:red"><c:out value="${warning}"/></p></c:if>
<table border="1" cellpadding="6" cellspacing="0">
    <tr><th>STT</th><th>Mã mặt hàng</th><th>Tên mặt hàng</th><th>Đơn giá</th><th>Số lượng</th><th>Thành tiền</th></tr>
    <c:forEach var="d" items="${details}" varStatus="st">
        <tr>
            <td>${st.count}</td>
            <td><c:out value="${d.product.productCode}"/></td>
            <td><c:out value="${d.product.name}"/></td>
            <td><fmt:formatNumber value="${d.unitPrice}" type="number" maxFractionDigits="0"/>đ</td>
            <td><c:out value="${d.quantity}"/></td>
            <td><fmt:formatNumber value="${d.subTotal}" type="number" maxFractionDigits="0"/>đ</td>
        </tr>
    </c:forEach>
    <tr><th colspan="5" style="text-align:right">Tổng</th><th><fmt:formatNumber value="${invoice.totalAmount}" type="number" maxFractionDigits="0"/>đ</th></tr>
</table>
<p><a href="${pageContext.request.contextPath}/manager/customer-details?customerId=${invoice.customer.id}&amp;startDate=${startDate}&amp;endDate=${endDate}">Quay lại</a></p>
</body>
</html>
