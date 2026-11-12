<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head><meta charset="UTF-8"><title>UI_ItemDetails6 – Chi tiết mặt hàng</title></head>
<body>
<h1>UI_ItemDetails6 (Chi tiết mặt hàng)</h1>
<table cellpadding="5">
    <tr><td>Mã mặt hàng:</td><td><c:out value="${product.productCode}"/></td></tr>
    <tr><td>Tên mặt hàng:</td><td><c:out value="${product.name}"/></td></tr>
    <tr><td>Loại hàng:</td><td><c:out value="${product.category}"/></td></tr>
    <tr><td>Nhãn hiệu:</td><td><c:out value="${product.brand}"/></td></tr>
    <tr><td>Đơn vị tính:</td><td><c:out value="${product.unit}"/></td></tr>
    <tr><td>Giá bán:</td><td><fmt:formatNumber value="${product.price}" type="number" maxFractionDigits="0"/>đ</td></tr>
    <tr><td>Số lượng tồn:</td><td><c:out value="${product.stockQuantity}"/></td></tr>
    <tr><td>Nhà cung cấp:</td><td><c:out value="${product.supplier.name}"/></td></tr>
    <tr><td>Mô tả chi tiết:</td><td><c:out value="${product.description}"/></td></tr>
</table>
<p><a href="${pageContext.request.contextPath}/customer/search-item">Quay lại</a></p>
</body>
</html>
