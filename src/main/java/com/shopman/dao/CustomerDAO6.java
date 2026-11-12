package com.shopman.dao;

import com.shopman.model.CustomerStat6;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/** DAO for customer revenue statistics. CustomerStat6 is calculated, not a DB table. */
public class CustomerDAO6 extends DAO6 {
    public List<CustomerStat6> getCustomerRevenueStat(Date startDate, Date endDate) {
        List<CustomerStat6> result = new ArrayList<>();
        String sql = "SELECT u.id, u.username, u.fullName, u.dob, u.address, u.email, u.phone, u.role, " +
                "c.customerCode, c.membershipLevel, COUNT(i.id) AS totalOrders, " +
                "COALESCE(SUM(COALESCE(dsum.invoiceTotal, 0)), 0) AS totalRevenue " +
                "FROM tblCustomer6 c JOIN tblUser6 u ON u.id = c.tblUser6id " +
                "JOIN tblInvoice6 i ON i.tblCustomer6id = c.tblUser6id " +
                "LEFT JOIN (SELECT tblInvoice6id, SUM(quantity * unitPrice) AS invoiceTotal " +
                "FROM tblInvoiceDetail6 GROUP BY tblInvoice6id) dsum ON dsum.tblInvoice6id = i.id " +
                "WHERE i.orderDate >= ? AND i.orderDate <= ? AND i.status <> 'CANCELLED' " +
                "GROUP BY u.id, u.username, u.fullName, u.dob, u.address, u.email, u.phone, u.role, " +
                "c.customerCode, c.membershipLevel ORDER BY totalRevenue DESC, c.customerCode";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CustomerStat6 c = new CustomerStat6();
                    c.setId(rs.getInt("id"));
                    c.setUsername(rs.getString("username"));
                    c.setFullName(rs.getString("fullName"));
                    c.setDob(rs.getDate("dob"));
                    c.setAddress(rs.getString("address"));
                    c.setEmail(rs.getString("email"));
                    c.setPhone(rs.getString("phone"));
                    c.setRole(rs.getString("role"));
                    c.setCustomerCode(rs.getString("customerCode"));
                    c.setMembershipLevel(rs.getString("membershipLevel"));
                    c.setTotalOrders(rs.getInt("totalOrders"));
                    c.setTotalRevenue(rs.getBigDecimal("totalRevenue"));
                    result.add(c);
                }
            }
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Lỗi thống kê doanh thu khách hàng.", ex);
        }
    }

    public CustomerStat6 getCustomerById(int customerUserId) {
        String sql = "SELECT u.id, u.username, u.fullName, u.dob, u.address, u.email, u.phone, u.role, " +
                "c.customerCode, c.membershipLevel FROM tblCustomer6 c " +
                "JOIN tblUser6 u ON u.id = c.tblUser6id WHERE c.tblUser6id = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, customerUserId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                CustomerStat6 c = new CustomerStat6();
                c.setId(rs.getInt("id"));
                c.setUsername(rs.getString("username"));
                c.setFullName(rs.getString("fullName"));
                c.setDob(rs.getDate("dob"));
                c.setAddress(rs.getString("address"));
                c.setEmail(rs.getString("email"));
                c.setPhone(rs.getString("phone"));
                c.setRole(rs.getString("role"));
                c.setCustomerCode(rs.getString("customerCode"));
                c.setMembershipLevel(rs.getString("membershipLevel"));
                return c;
            }
        } catch (Exception ex) {
            throw new RuntimeException("Lỗi lấy thông tin khách hàng.", ex);
        }
    }
}
