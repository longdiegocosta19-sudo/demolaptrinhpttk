package com.shopman.dao;

import com.shopman.model.Customer6;
import com.shopman.model.Employee6;
import com.shopman.model.Invoice6;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/** DAO for the customer's purchase history and invoice header. */
public class InvoiceDAO6 extends DAO6 {
    private static final String TOTAL_JOIN =
            " LEFT JOIN (SELECT tblInvoice6id, SUM(quantity * unitPrice) AS invoiceTotal " +
            "FROM tblInvoiceDetail6 GROUP BY tblInvoice6id) dsum ON dsum.tblInvoice6id = i.id ";

    public List<Invoice6> getInvoicesByCustomer(int customerUserId, Date startDate, Date endDate) {
        List<Invoice6> result = new ArrayList<>();
        String sql = "SELECT i.id, i.invoiceCode, i.orderDate, i.status, " +
                "COALESCE(dsum.invoiceTotal, 0) AS totalAmount FROM tblInvoice6 i " + TOTAL_JOIN +
                "WHERE i.tblCustomer6id = ? AND i.orderDate >= ? AND i.orderDate <= ? " +
                "AND i.status <> 'CANCELLED' ORDER BY i.orderDate DESC, i.id DESC";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerUserId);
            ps.setDate(2, startDate);
            ps.setDate(3, endDate);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Invoice6 invoice = mapInvoice(rs);
                    Customer6 customer = new Customer6();
                    customer.setId(customerUserId);
                    invoice.setCustomer(customer);
                    result.add(invoice);
                }
            }
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Lỗi lấy lịch sử mua hàng.", ex);
        }
    }

    public Invoice6 getInvoiceById(int invoiceId) {
        String sql = "SELECT i.id, i.invoiceCode, i.orderDate, i.status, " +
                "COALESCE(dsum.invoiceTotal, 0) AS totalAmount, " +
                "u.id AS customerUserId, u.fullName AS customerName, c.customerCode, " +
                "e.employeeCode, e.position, eu.id AS employeeUserId, eu.fullName AS employeeName " +
                "FROM tblInvoice6 i JOIN tblCustomer6 c ON c.tblUser6id = i.tblCustomer6id " +
                "JOIN tblUser6 u ON u.id = c.tblUser6id " +
                "LEFT JOIN tblEmployee6 e ON e.tblUser6id = i.tblEmployee6id " +
                "LEFT JOIN tblUser6 eu ON eu.id = e.tblUser6id " + TOTAL_JOIN +
                "WHERE i.id = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, invoiceId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                Invoice6 invoice = mapInvoice(rs);
                Customer6 customer = new Customer6();
                customer.setId(rs.getInt("customerUserId"));
                customer.setFullName(rs.getString("customerName"));
                customer.setCustomerCode(rs.getString("customerCode"));
                invoice.setCustomer(customer);

                if (rs.getObject("employeeUserId") != null) {
                    Employee6 employee = new Employee6();
                    employee.setId(rs.getInt("employeeUserId"));
                    employee.setEmployeeCode(rs.getString("employeeCode"));
                    employee.setPosition(rs.getString("position"));
                    employee.setFullName(rs.getString("employeeName"));
                    invoice.setEmployee(employee);
                }
                return invoice;
            }
        } catch (Exception ex) {
            throw new RuntimeException("Lỗi lấy thông tin hóa đơn.", ex);
        }
    }

    private Invoice6 mapInvoice(ResultSet rs) throws Exception {
        Invoice6 invoice = new Invoice6();
        invoice.setId(rs.getInt("id"));
        invoice.setInvoiceCode(rs.getString("invoiceCode"));
        invoice.setOrderDate(rs.getDate("orderDate"));
        invoice.setStatus(rs.getString("status"));
        invoice.setTotalAmount(rs.getBigDecimal("totalAmount"));
        return invoice;
    }
}
