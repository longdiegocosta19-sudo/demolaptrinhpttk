<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head><meta charset="UTF-8"><title>Đăng nhập – ShopMan</title></head>
<body>
<h1>ShopMan – Đăng nhập</h1>
<p>Hệ thống quản lý bán hàng bách hóa trực tuyến</p>
<% if (request.getAttribute("error") != null) { %>
<p style="color:red"><%= request.getAttribute("error") %></p>
<% } %>
<form method="post" action="${pageContext.request.contextPath}/login">
    <p><label>Tên đăng nhập: <input type="text" name="username" required></label></p>
    <p><label>Mật khẩu: <input type="password" name="password" required></label></p>
    <button type="submit">Đăng nhập</button>
</form>
</body>
</html>
