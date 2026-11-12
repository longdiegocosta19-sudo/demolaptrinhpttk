package com.shopman.model;

import java.math.BigDecimal;

public class InvoiceDetail6 {
    private int id;
    private int quantity;
    private BigDecimal unitPrice;
    private Product6 product;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public Product6 getProduct() { return product; }
    public void setProduct(Product6 product) { this.product = product; }
    /** Derived field: quantity multiplied by historical unit price; not stored in DB. */
    public BigDecimal getSubTotal() {
        if (unitPrice == null) return BigDecimal.ZERO;
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
