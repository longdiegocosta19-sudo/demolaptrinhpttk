package com.shopman.dao;

import com.shopman.model.User6;
import com.shopman.util.PasswordUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO6 extends DAO6 {
    public User6 authenticate(String username, String password) {
        String sql = "SELECT id, username, passwordSalt, passwordHash, fullName, dob, address, email, phone, role " +
                "FROM tblUser6 WHERE username = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next() || !PasswordUtil.verify(password,
                        rs.getString("passwordSalt"), rs.getString("passwordHash"))) return null;
                User6 user = new User6();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setFullName(rs.getString("fullName"));
                user.setDob(rs.getDate("dob"));
                user.setAddress(rs.getString("address"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (Exception ex) {
            throw new RuntimeException("Không thể xác thực tài khoản.", ex);
        }
    }
}
