<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head><meta charset="UTF-8"><title>UI_RevenueStatistics6 – Thống kê doanh thu</title></head>
<body>
<h1>UI_RevenueStatistics6 (Thống kê khách hàng theo doanh thu)</h1>
<p>Xin chào, <c:out value="${sessionScope.loginUser.fullName}"/> |
   <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a></p>
<form method="get" action="${pageContext.request.contextPath}/manager/revenue-statistics">
    <label>Ngày bắt đầu: <input type="date" name="startDate" value="<c:out value='${startDate}'/>" required></label>
    <label>Ngày kết thúc: <input type="date" name="endDate" value="<c:out value='${endDate}'/>" required></label>
    <button type="submit">Xem thống kê</button>
</form>
<c:if test="${not empty error}"><p style="color:red"><c:out value="${error}"/></p></c:if>
<c:if test="${searched}">
    <h2>Danh sách thống kê khách hàng</h2>
    <c:choose>
        <c:when test="${empty customerStats}"><p>Không có dữ liệu thống kê.</p></c:when>
        <c:otherwise>
            <table border="1" cellpadding="6" cellspacing="0">
                <tr><th>STT</th><th>Mã khách hàng</th><th>Họ tên</th><th>Số đơn</th><th>Tổng doanh thu</th><th>Thao tác</th></tr>
                <c:forEach var="cst" items="${customerStats}" varStatus="st">
                    <tr>
                        <td>${st.count}</td>
                        <td><c:out value="${cst.customerCode}"/></td>
                        <td><c:out value="${cst.fullName}"/></td>
                        <td><c:out value="${cst.totalOrders}"/></td>
                        <td><fmt:formatNumber value="${cst.totalRevenue}" type="number" maxFractionDigits="0"/>đ</td>
                        <td><a href="${pageContext.request.contextPath}/manager/customer-details?customerId=${cst.id}&amp;startDate=${startDate}&amp;endDate=${endDate}">Xem chi tiết</a></td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</c:if>
</body>
</html>
