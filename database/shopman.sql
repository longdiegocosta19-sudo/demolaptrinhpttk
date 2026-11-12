-- ShopMan DB – Đề 36
-- Schema bám theo ERD ở trang 50 và phần ánh xạ CSDL ở trang 36 của file Đề 36.
-- Chạy file này trong MySQL Workbench. Lệnh DROP sẽ xóa dữ liệu ShopMan_DB cũ.

CREATE DATABASE IF NOT EXISTS ShopMan_DB
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ShopMan_DB;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS tblInvoiceDetail6;
DROP TABLE IF EXISTS tblInvoice6;
DROP TABLE IF EXISTS tblProduct6;
DROP TABLE IF EXISTS tblSupplier6;
DROP TABLE IF EXISTS tblCustomer6;
DROP TABLE IF EXISTS tblEmployee6;
DROP TABLE IF EXISTS tblUser6;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE tblUser6 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(60) NOT NULL UNIQUE,
    passwordSalt VARCHAR(100) NOT NULL,
    passwordHash VARCHAR(100) NOT NULL,
    fullName VARCHAR(150) NOT NULL,
    dob DATE NULL,
    address VARCHAR(255),
    email VARCHAR(150),
    phone VARCHAR(30),
    role VARCHAR(30) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE tblCustomer6 (
    tblUser6id INT PRIMARY KEY,
    customerCode VARCHAR(50) NOT NULL UNIQUE,
    membershipLevel VARCHAR(50),
    CONSTRAINT fk_customer_user6 FOREIGN KEY (tblUser6id) REFERENCES tblUser6(id)
) ENGINE=InnoDB;

CREATE TABLE tblEmployee6 (
    tblUser6id INT PRIMARY KEY,
    employeeCode VARCHAR(50) NOT NULL UNIQUE,
    position VARCHAR(100),
    CONSTRAINT fk_employee_user6 FOREIGN KEY (tblUser6id) REFERENCES tblUser6(id)
) ENGINE=InnoDB;

CREATE TABLE tblSupplier6 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    supplierCode VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(150) NOT NULL,
    address VARCHAR(255),
    email VARCHAR(150),
    phone VARCHAR(30),
    description VARCHAR(500)
) ENGINE=InnoDB;

CREATE TABLE tblProduct6 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    productCode VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(180) NOT NULL,
    category VARCHAR(100),
    brand VARCHAR(100),
    unit VARCHAR(50),
    price DECIMAL(15,2) NOT NULL,
    stockQuantity INT NOT NULL DEFAULT 0,
    description TEXT,
    tblSupplier6id INT NOT NULL,
    CONSTRAINT fk_product_supplier6 FOREIGN KEY (tblSupplier6id) REFERENCES tblSupplier6(id),
    INDEX idx_product_name6 (name)
) ENGINE=InnoDB;

CREATE TABLE tblInvoice6 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    invoiceCode VARCHAR(50) NOT NULL UNIQUE,
    orderDate DATE NOT NULL,
    status VARCHAR(40) NOT NULL,
    tblCustomer6id INT NOT NULL,
    tblEmployee6id INT NULL,
    CONSTRAINT fk_invoice_customer6 FOREIGN KEY (tblCustomer6id) REFERENCES tblCustomer6(tblUser6id),
    CONSTRAINT fk_invoice_employee6 FOREIGN KEY (tblEmployee6id) REFERENCES tblEmployee6(tblUser6id),
    INDEX idx_invoice_date6 (orderDate),
    INDEX idx_invoice_customer_date6 (tblCustomer6id, orderDate)
) ENGINE=InnoDB;

CREATE TABLE tblInvoiceDetail6 (
    id INT AUTO_INCREMENT PRIMARY KEY,
    quantity INT NOT NULL,
    unitPrice DECIMAL(15,2) NOT NULL,
    tblInvoice6id INT NOT NULL,
    tblProduct6id INT NOT NULL,
    CONSTRAINT fk_detail_invoice6 FOREIGN KEY (tblInvoice6id) REFERENCES tblInvoice6(id),
    CONSTRAINT fk_detail_product6 FOREIGN KEY (tblProduct6id) REFERENCES tblProduct6(id),
    INDEX idx_detail_invoice6 (tblInvoice6id)
) ENGINE=InnoDB;

-- Tài khoản demo. Mật khẩu được băm PBKDF2-HMAC-SHA256, 120.000 vòng lặp.
-- manager1 / Manager@123 | customer1 / Customer@123 | customer2 / Customer@123
-- sales1 / Sales@123
INSERT INTO tblUser6
(id, username, passwordSalt, passwordHash, fullName, dob, address, email, phone, role) VALUES
(1, 'manager1', 'U2hvcE1hbk1nclNhbHQyMDI2', 'PIbMmfh5c0MQ5Wc4dLlopfLr+Vb9ZyQ+BGoGyDDHBoE=', 'Nhân viên quản lý', '1990-01-01', 'Hà Nội', 'manager@shopman.local', '0900000001', 'MANAGER'),
(2, 'customer1', 'U2hvcE1hbkN1c1NhbHQyMDI2', '5mjO1l6zjPTOaINSR0VzRJOlZux2A1TkfAPZ6NwVamI=', 'Nguyễn Văn A', '2000-02-02', 'Hà Nội', 'a@example.local', '0901000001', 'CUSTOMER'),
(3, 'customer2', 'U2hvcE1hbkN1c1NhbHQyMDI2', '5mjO1l6zjPTOaINSR0VzRJOlZux2A1TkfAPZ6NwVamI=', 'Trần Thị B', '2001-03-03', 'Hà Nội', 'b@example.local', '0901000002', 'CUSTOMER'),
(4, 'sales1', 'U2hvcE1hblNhbGVTYWx0MjY=', 'TzG4neny3w+55Mgti4K6RD/7S4oc9IRgZSPkzr4tvuY=', 'Nhân viên bán hàng', '1998-04-04', 'Hà Nội', 'sales@shopman.local', '0900000004', 'SALES');

INSERT INTO tblCustomer6 (tblUser6id, customerCode, membershipLevel) VALUES
(2, 'KH012', 'Thành viên vàng'),
(3, 'KH045', 'Thành viên bạc');

INSERT INTO tblEmployee6 (tblUser6id, employeeCode, position) VALUES
(1, 'NVQL001', 'Nhân viên quản lý'),
(4, 'NVBH001', 'Nhân viên bán hàng');

INSERT INTO tblSupplier6 (id, supplierCode, name, address, email, phone, description) VALUES
(1, 'NCC001', 'Công ty May Mặc A', 'Hà Nội', 'contact@maymaca.example', '0243000001', 'Nhà cung cấp mặt hàng thời trang');

INSERT INTO tblProduct6
(id, productCode, name, category, brand, unit, price, stockQuantity, description, tblSupplier6id) VALUES
(1, 'AK01', 'Áo khoác da', 'Áo khoác', 'Thời trang Việt', 'Chiếc', 500000, 25,
 'Áo khoác da cao cấp giữ nhiệt mùa đông', 1),
(2, 'AP05', 'Áo phông Cotton', 'Áo phông', 'Thời trang Việt', 'Chiếc', 140000, 60,
 'Áo phông Cotton mềm mại, thoáng mát', 1);

-- Dữ liệu minh họa theo Đề 36.
-- File có chỗ lệch nhau: kịch bản chữ ghi 3 lần mua (350.000, 270.000, 780.000)
-- nhưng wireframe lịch sử mua chỉ minh họa 2 dòng HD1001 và HD1089.
-- Bản demo giữ 3 hóa đơn để tổng số đơn = 3 và doanh thu = 1.400.000đ.
INSERT INTO tblInvoice6 (id, invoiceCode, orderDate, status, tblCustomer6id, tblEmployee6id) VALUES
(1, 'HD1001', '2023-10-12', 'DELIVERED', 2, 1),
(2, 'HD10XX', '2023-10-23', 'DELIVERED', 2, 1),
(3, 'HD1089', '2023-10-26', 'DELIVERED', 2, 1),
(4, 'HD2010', '2023-10-19', 'DELIVERED', 3, 1);

-- HD1001 và HD10XX là các dòng dữ liệu demo bổ sung để thể hiện số tiền trong kịch bản chữ.
-- HD2010/ngày 19-10 và chi tiết của hóa đơn KH045 cũng là dữ liệu demo phát sinh vì wireframe
-- chỉ cung cấp tổng doanh thu/số đơn của khách hàng này, không ghi mã hóa đơn và ngày mua.
-- Giá ở chi tiết hóa đơn là giá tại thời điểm mua; có thể khác giá niêm yết hiện tại.
INSERT INTO tblInvoiceDetail6 (id, quantity, unitPrice, tblInvoice6id, tblProduct6id) VALUES
(1, 1, 350000, 1, 1),
(2, 1, 270000, 2, 2),
(3, 1, 500000, 3, 1),
(4, 2, 140000, 3, 2),
(5, 1, 500000, 4, 1);

-- Kiểm tra nhanh tổng tiền hóa đơn được tính từ quantity * unitPrice.
SELECT i.invoiceCode, i.orderDate, SUM(d.quantity * d.unitPrice) AS totalAmount
FROM tblInvoice6 i JOIN tblInvoiceDetail6 d ON d.tblInvoice6id = i.id
GROUP BY i.id, i.invoiceCode, i.orderDate ORDER BY i.orderDate;
