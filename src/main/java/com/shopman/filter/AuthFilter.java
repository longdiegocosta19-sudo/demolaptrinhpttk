package com.shopman.filter;

import com.shopman.model.User6;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/customer/*", "/manager/*"})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(javax.servlet.ServletRequest request, javax.servlet.ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        User6 user = session == null ? null : (User6) session.getAttribute("loginUser");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        String path = req.getRequestURI().substring(req.getContextPath().length());
        boolean allowed = (path.startsWith("/customer/") && "CUSTOMER".equals(user.getRole()))
                || (path.startsWith("/manager/") && "MANAGER".equals(user.getRole()));
        if (!allowed) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập chức năng này.");
            return;
        }
        chain.doFilter(request, response);
    }
}
