package com.shopman.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Invoice6 {
    private int id;
    private String invoiceCode;
    private Date orderDate;
    private String status;
    private Customer6 customer;
    private Employee6 employee;
    private List<InvoiceDetail6> invoiceDetails = new ArrayList<>();
    private BigDecimal totalAmount = BigDecimal.ZERO;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getInvoiceCode() { return invoiceCode; }
    public void setInvoiceCode(String invoiceCode) { this.invoiceCode = invoiceCode; }
    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Customer6 getCustomer() { return customer; }
    public void setCustomer(Customer6 customer) { this.customer = customer; }
    public Employee6 getEmployee() { return employee; }
    public void setEmployee(Employee6 employee) { this.employee = employee; }
    public List<InvoiceDetail6> getInvoiceDetails() { return invoiceDetails; }
    public void setInvoiceDetails(List<InvoiceDetail6> invoiceDetails) { this.invoiceDetails = invoiceDetails; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount == null ? BigDecimal.ZERO : totalAmount; }
}
