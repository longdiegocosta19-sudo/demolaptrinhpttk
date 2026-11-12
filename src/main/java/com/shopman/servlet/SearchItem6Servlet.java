package com.shopman.servlet;

import com.shopman.dao.ProductDAO6;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/customer/search-item")
public class SearchItem6Servlet extends HttpServlet {
    private final ProductDAO6 productDAO = new ProductDAO6();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        request.setAttribute("keyword", keyword == null ? "" : keyword.trim());
        if (keyword != null && !keyword.isBlank()) {
            try {
                request.setAttribute("products", productDAO.searchProductByName(keyword));
                request.setAttribute("searched", true);
            } catch (RuntimeException ex) {
                getServletContext().log("Search Item 6 failed", ex);
                request.setAttribute("error", "Không thể tìm kiếm mặt hàng. Hãy kiểm tra kết nối dữ liệu.");
            }
        }
        request.getRequestDispatcher("/WEB-INF/views/UI_SearchItem6.jsp").forward(request, response);
    }
}
