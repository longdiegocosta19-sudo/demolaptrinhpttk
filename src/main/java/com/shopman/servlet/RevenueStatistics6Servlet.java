package com.shopman.servlet;

import com.shopman.dao.CustomerDAO6;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@WebServlet("/manager/revenue-statistics")
public class RevenueStatistics6Servlet extends HttpServlet {
    private final CustomerDAO6 customerDAO = new CustomerDAO6();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String startText = request.getParameter("startDate");
        String endText = request.getParameter("endDate");
        request.setAttribute("startDate", startText == null ? "" : startText);
        request.setAttribute("endDate", endText == null ? "" : endText);

        if ((startText == null || startText.isBlank()) && (endText == null || endText.isBlank())) {
            request.getRequestDispatcher("/WEB-INF/views/UI_RevenueStatistics6.jsp").forward(request, response);
            return;
        }
        if (startText == null || startText.isBlank() || endText == null || endText.isBlank()) {
            request.setAttribute("error", "Vui lòng chọn đầy đủ ngày bắt đầu và ngày kết thúc.");
        } else {
            try {
                LocalDate start = LocalDate.parse(startText);
                LocalDate end = LocalDate.parse(endText);
                if (end.isBefore(start)) {
                    request.setAttribute("error", "Ngày kết thúc phải bằng hoặc sau ngày bắt đầu.");
                } else {
                    request.setAttribute("customerStats", customerDAO.getCustomerRevenueStat(
                            Date.valueOf(start), Date.valueOf(end)));
                    request.setAttribute("searched", true);
                }
            } catch (DateTimeParseException ex) {
                request.setAttribute("error", "Ngày không hợp lệ. Vui lòng chọn lại.");
            } catch (RuntimeException ex) {
                getServletContext().log("Revenue statistics failed", ex);
                request.setAttribute("error", "Không thể lấy thống kê. Hãy kiểm tra cơ sở dữ liệu.");
            }
        }
        request.getRequestDispatcher("/WEB-INF/views/UI_RevenueStatistics6.jsp").forward(request, response);
    }
}
