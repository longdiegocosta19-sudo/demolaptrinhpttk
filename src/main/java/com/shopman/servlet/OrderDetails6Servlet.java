package com.shopman.servlet;

import com.shopman.dao.InvoiceDAO6;
import com.shopman.dao.InvoiceDetailDAO6;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/manager/order-details")
public class OrderDetails6Servlet extends HttpServlet {
    private final InvoiceDAO6 invoiceDAO = new InvoiceDAO6();
    private final InvoiceDetailDAO6 invoiceDetailDAO = new InvoiceDetailDAO6();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int invoiceId = Integer.parseInt(request.getParameter("invoiceId"));
            var invoice = invoiceDAO.getInvoiceById(invoiceId);
            if (invoice == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy hóa đơn.");
                return;
            }
            var details = invoiceDetailDAO.getInvoiceDetails(invoiceId);
            if (details.isEmpty()) {
                request.setAttribute("warning", "Dữ liệu hóa đơn trống.");
            }
            invoice.setInvoiceDetails(details);
            request.setAttribute("invoice", invoice);
            request.setAttribute("details", details);
            request.setAttribute("startDate", request.getParameter("startDate"));
            request.setAttribute("endDate", request.getParameter("endDate"));
            request.getRequestDispatcher("/WEB-INF/views/UI_OrderDetails6.jsp").forward(request, response);
        } catch (NumberFormatException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Mã hóa đơn không hợp lệ.");
        } catch (RuntimeException ex) {
            getServletContext().log("Order details failed", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không tải được chi tiết hóa đơn.");
        }
    }
}
