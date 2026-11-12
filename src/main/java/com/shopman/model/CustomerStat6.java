package com.shopman.model;

import java.math.BigDecimal;

/** Derived view model for revenue statistics; no separate database table. */
public class CustomerStat6 extends Customer6 {
    private int totalOrders;
    private BigDecimal totalRevenue = BigDecimal.ZERO;

    public int getTotalOrders() { return totalOrders; }
    public void setTotalOrders(int totalOrders) { this.totalOrders = totalOrders; }
    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue == null ? BigDecimal.ZERO : totalRevenue;
    }
}
