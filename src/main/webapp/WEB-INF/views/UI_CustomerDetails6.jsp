<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head><meta charset="UTF-8"><title>UI_CustomerDetails6 – Lịch sử mua hàng</title></head>
<body>
<h1>UI_CustomerDetails6 (Lịch sử mua hàng của khách hàng)</h1>
<p>Khách hàng: <strong><c:out value="${customer.fullName}"/></strong>
   (<c:out value="${customer.customerCode}"/>) – Từ ngày <c:out value="${startDate}"/> đến <c:out value="${endDate}"/></p>
<c:choose>
    <c:when test="${empty invoices}"><p>Khách hàng chưa có giao dịch nào trong khoảng thời gian này.</p></c:when>
    <c:otherwise>
        <table border="1" cellpadding="6" cellspacing="0">
            <tr><th>STT</th><th>Mã hóa đơn</th><th>Ngày mua</th><th>Tổng tiền</th><th>Thao tác</th></tr>
            <c:forEach var="inv" items="${invoices}" varStatus="st">
                <tr>
                    <td>${st.count}</td>
                    <td><c:out value="${inv.invoiceCode}"/></td>
                    <td><fmt:formatDate value="${inv.orderDate}" pattern="dd/MM/yyyy"/></td>
                    <td><fmt:formatNumber value="${inv.totalAmount}" type="number" maxFractionDigits="0"/>đ</td>
                    <td><a href="${pageContext.request.contextPath}/manager/order-details?invoiceId=${inv.id}&amp;startDate=${startDate}&amp;endDate=${endDate}">Xem hóa đơn</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>
<p><a href="${pageContext.request.contextPath}/manager/revenue-statistics?startDate=${startDate}&amp;endDate=${endDate}">Quay lại</a></p>
</body>
</html>
