package com.shopman.dao;

import com.shopman.model.Product6;
import com.shopman.model.Supplier6;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/** DAO for Search Item 6. */
public class ProductDAO6 extends DAO6 {
    private static final String SELECT_PRODUCT =
            "SELECT p.id, p.productCode, p.name, p.category, p.brand, p.unit, p.price, " +
            "p.stockQuantity, p.description, s.id AS supplierId, s.supplierCode, " +
            "s.name AS supplierName, s.address AS supplierAddress, s.email AS supplierEmail, " +
            "s.phone AS supplierPhone, s.description AS supplierDescription " +
            "FROM tblProduct6 p JOIN tblSupplier6 s ON s.id = p.tblSupplier6id ";

    /** searchProductByName(keyword): name contains the entered keyword. */
    public List<Product6> searchProductByName(String keyword) {
        List<Product6> result = new ArrayList<>();
        String sql = SELECT_PRODUCT + "WHERE p.name LIKE ? ORDER BY p.productCode";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword.trim() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) result.add(mapProduct(rs));
            }
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Lỗi tìm kiếm mặt hàng.", ex);
        }
    }

    /** getProductDetails(productId): detailed product and supplier information. */
    public Product6 getProductDetails(int productId) {
        String sql = SELECT_PRODUCT + "WHERE p.id = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapProduct(rs) : null;
            }
        } catch (Exception ex) {
            throw new RuntimeException("Lỗi lấy chi tiết mặt hàng.", ex);
        }
    }

    private Product6 mapProduct(ResultSet rs) throws Exception {
        Product6 p = new Product6();
        p.setId(rs.getInt("id"));
        p.setProductCode(rs.getString("productCode"));
        p.setName(rs.getString("name"));
        p.setCategory(rs.getString("category"));
        p.setBrand(rs.getString("brand"));
        p.setUnit(rs.getString("unit"));
        p.setPrice(rs.getBigDecimal("price"));
        p.setStockQuantity(rs.getInt("stockQuantity"));
        p.setDescription(rs.getString("description"));

        Supplier6 supplier = new Supplier6();
        supplier.setId(rs.getInt("supplierId"));
        supplier.setSupplierCode(rs.getString("supplierCode"));
        supplier.setName(rs.getString("supplierName"));
        supplier.setAddress(rs.getString("supplierAddress"));
        supplier.setEmail(rs.getString("supplierEmail"));
        supplier.setPhone(rs.getString("supplierPhone"));
        supplier.setDescription(rs.getString("supplierDescription"));
        p.setSupplier(supplier);
        return p;
    }
}
