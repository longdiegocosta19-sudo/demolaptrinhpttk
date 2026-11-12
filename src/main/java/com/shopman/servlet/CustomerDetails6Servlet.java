package com.shopman.servlet;

import com.shopman.dao.CustomerDAO6;
import com.shopman.dao.InvoiceDAO6;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

@WebServlet("/manager/customer-details")
public class CustomerDetails6Servlet extends HttpServlet {
    private final CustomerDAO6 customerDAO = new CustomerDAO6();
    private final InvoiceDAO6 invoiceDAO = new InvoiceDAO6();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int customerId = Integer.parseInt(request.getParameter("customerId"));
            LocalDate start = LocalDate.parse(request.getParameter("startDate"));
            LocalDate end = LocalDate.parse(request.getParameter("endDate"));
            if (end.isBefore(start)) throw new IllegalArgumentException("Khoảng ngày không hợp lệ.");

            var customer = customerDAO.getCustomerById(customerId);
            if (customer == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy khách hàng.");
                return;
            }
            request.setAttribute("customer", customer);
            request.setAttribute("startDate", start.toString());
            request.setAttribute("endDate", end.toString());
            request.setAttribute("invoices", invoiceDAO.getInvoicesByCustomer(
                    customerId, Date.valueOf(start), Date.valueOf(end)));
            request.getRequestDispatcher("/WEB-INF/views/UI_CustomerDetails6.jsp").forward(request, response);
        } catch (Exception ex) {
            if (ex instanceof NumberFormatException || ex instanceof java.time.DateTimeException
                    || ex instanceof IllegalArgumentException) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thông tin khách hàng hoặc khoảng ngày không hợp lệ.");
            } else {
                getServletContext().log("Customer details failed", ex);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không tải được lịch sử mua hàng.");
            }
        }
    }
}
