package com.shopman.dao;

import com.shopman.model.InvoiceDetail6;
import com.shopman.model.Product6;
import com.shopman.model.Supplier6;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/** DAO for invoice lines, including the associated Product6 entity. */
public class InvoiceDetailDAO6 extends DAO6 {
    public List<InvoiceDetail6> getInvoiceDetails(int invoiceId) {
        List<InvoiceDetail6> result = new ArrayList<>();
        String sql = "SELECT d.id, d.quantity, d.unitPrice, p.id AS productId, " +
                "p.productCode, p.name AS productName, p.category, p.brand, p.unit, p.price, " +
                "p.stockQuantity, p.description, s.id AS supplierId, s.supplierCode, " +
                "s.name AS supplierName, s.address AS supplierAddress, s.email AS supplierEmail, " +
                "s.phone AS supplierPhone, s.description AS supplierDescription " +
                "FROM tblInvoiceDetail6 d JOIN tblProduct6 p ON p.id = d.tblProduct6id " +
                "JOIN tblSupplier6 s ON s.id = p.tblSupplier6id " +
                "WHERE d.tblInvoice6id = ? ORDER BY d.id";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, invoiceId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product6 product = new Product6();
                    product.setId(rs.getInt("productId"));
                    product.setProductCode(rs.getString("productCode"));
                    product.setName(rs.getString("productName"));
                    product.setCategory(rs.getString("category"));
                    product.setBrand(rs.getString("brand"));
                    product.setUnit(rs.getString("unit"));
                    product.setPrice(rs.getBigDecimal("price"));
                    product.setStockQuantity(rs.getInt("stockQuantity"));
                    product.setDescription(rs.getString("description"));
                    Supplier6 supplier = new Supplier6();
                    supplier.setId(rs.getInt("supplierId"));
                    supplier.setSupplierCode(rs.getString("supplierCode"));
                    supplier.setName(rs.getString("supplierName"));
                    supplier.setAddress(rs.getString("supplierAddress"));
                    supplier.setEmail(rs.getString("supplierEmail"));
                    supplier.setPhone(rs.getString("supplierPhone"));
                    supplier.setDescription(rs.getString("supplierDescription"));
                    product.setSupplier(supplier);

                    InvoiceDetail6 detail = new InvoiceDetail6();
                    detail.setId(rs.getInt("id"));
                    detail.setQuantity(rs.getInt("quantity"));
                    detail.setUnitPrice(rs.getBigDecimal("unitPrice"));
                    detail.setProduct(product);
                    result.add(detail);
                }
            }
            return result;
        } catch (Exception ex) {
            throw new RuntimeException("Lỗi lấy chi tiết hóa đơn.", ex);
        }
    }
}
