<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head><meta charset="UTF-8"><title>UI_SearchItem6 – Tìm kiếm mặt hàng</title></head>
<body>
<h1>UI_SearchItem6 (Tìm kiếm mặt hàng)</h1>
<p>Xin chào, <c:out value="${sessionScope.loginUser.fullName}"/> |
   <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a></p>
<form method="get" action="${pageContext.request.contextPath}/customer/search-item">
    <label>Từ khóa mặt hàng:
        <input type="text" name="keyword" value="<c:out value='${keyword}'/>" required>
    </label>
    <button type="submit">Tìm kiếm</button>
</form>
<c:if test="${not empty error}"><p style="color:red"><c:out value="${error}"/></p></c:if>
<c:if test="${searched}">
    <h2>Danh sách mặt hàng</h2>
    <c:choose>
        <c:when test="${empty products}"><p>Không tìm thấy mặt hàng phù hợp.</p></c:when>
        <c:otherwise>
            <table border="1" cellpadding="6" cellspacing="0">
                <tr><th>STT</th><th>Mã MH</th><th>Tên mặt hàng</th><th>Loại</th><th>Đơn giá</th><th>Thao tác</th></tr>
                <c:forEach var="p" items="${products}" varStatus="st">
                    <tr>
                        <td>${st.count}</td>
                        <td><c:out value="${p.productCode}"/></td>
                        <td><c:out value="${p.name}"/></td>
                        <td><c:out value="${p.category}"/></td>
                        <td><fmt:formatNumber value="${p.price}" type="number" maxFractionDigits="0"/>đ</td>
                        <td><a href="${pageContext.request.contextPath}/customer/item-details?id=${p.id}">Xem chi tiết</a></td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</c:if>
<p><a href="${pageContext.request.contextPath}/customer/search-item">Quay lại trang chủ</a></p>
</body>
</html>
