package com.shopman.servlet;

import com.shopman.dao.ProductDAO6;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/customer/item-details")
public class ItemDetails6Servlet extends HttpServlet {
    private final ProductDAO6 productDAO = new ProductDAO6();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("id"));
            var product = productDAO.getProductDetails(productId);
            if (product == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy mặt hàng.");
                return;
            }
            request.setAttribute("product", product);
            request.getRequestDispatcher("/WEB-INF/views/UI_ItemDetails6.jsp").forward(request, response);
        } catch (NumberFormatException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Mã mặt hàng không hợp lệ.");
        } catch (RuntimeException ex) {
            getServletContext().log("Item details failed", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không tải được thông tin mặt hàng.");
        }
    }
}
