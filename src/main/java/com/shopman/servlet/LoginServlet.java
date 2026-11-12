package com.shopman.servlet;

import com.shopman.dao.UserDAO6;
import com.shopman.model.User6;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO6 userDAO = new UserDAO6();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            request.setAttribute("error", "Vui lòng nhập tên đăng nhập và mật khẩu.");
            doGet(request, response);
            return;
        }
        try {
            User6 user = userDAO.authenticate(username.trim(), password);
            if (user == null) {
                request.setAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng.");
                doGet(request, response);
                return;
            }
            HttpSession old = request.getSession(false);
            if (old != null) old.invalidate();
            HttpSession session = request.getSession(true);
            session.setAttribute("loginUser", user);
            session.setMaxInactiveInterval(30 * 60);

            if ("MANAGER".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/manager/revenue-statistics");
            } else if ("CUSTOMER".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/customer/search-item");
            } else {
                session.invalidate();
                request.setAttribute("error", "Vai trò tài khoản chưa được triển khai trong hai module này.");
                doGet(request, response);
            }
        } catch (RuntimeException ex) {
            getServletContext().log("Login failed", ex);
            request.setAttribute("error", "Không kết nối được cơ sở dữ liệu. Hãy kiểm tra cấu hình MySQL.");
            doGet(request, response);
        }
    }
}
