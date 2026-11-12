# ShopMan – Đề 36 (bản sửa theo tài liệu)

**Sinh viên:** Vương Anh Long – B23DCAT176  
**Công nghệ:** Java 17, JSP/JSTL, Servlet 4 (`javax.servlet`), JDBC, MySQL 8+, Maven, Tomcat 9.

Project này được sửa để bám theo ERD, biểu đồ lớp, wireframe và Scenario 3.0 trong file `36B23DCAT176-đã gộp.pdf`. Cấu trúc chia thành `model`, `dao`, `servlet` (điều khiển), `filter`, `util` và JSP trong `WEB-INF/views`.

## 1. Những điểm đã sửa so với bản đầu

- Đổi database sang `ShopMan_DB` và bảy bảng theo ERD: `tblUser6`, `tblCustomer6`, `tblEmployee6`, `tblSupplier6`, `tblProduct6`, `tblInvoice6`, `tblInvoiceDetail6`.
- Đổi thực thể thành `User6`, `Customer6`, `Employee6`, `Supplier6`, `Product6`, `Invoice6`, `InvoiceDetail6`, `CustomerStat6`.
- `CustomerStat6` kế thừa `Customer6`, chỉ là lớp dữ liệu thống kê dẫn xuất; không tạo bảng riêng.
- Module 1 dùng `ProductDAO6.searchProductByName()` và `ProductDAO6.getProductDetails()`.
- Module 2 dùng `CustomerDAO6.getCustomerRevenueStat()`, `InvoiceDAO6.getInvoicesByCustomer()` và `InvoiceDetailDAO6.getInvoiceDetails()`.
- Thay dữ liệu sản phẩm chung bằng `AK01 – Áo khoác da` và `AP05 – Áo phông Cotton`; chi tiết AK01 khớp giá, tồn kho, nhà cung cấp và mô tả trong wireframe.
- Tổng tiền hóa đơn và thành tiền từng dòng được tính từ `quantity * unitPrice`, không lưu thành cột riêng trong database, theo phần ánh xạ ERD của tài liệu.
- Các JSP được đặt tên `UI_SearchItem6`, `UI_ItemDetails6`, `UI_RevenueStatistics6`, `UI_CustomerDetails6`, `UI_OrderDetails6` để dễ đối chiếu với wireframe.

## 2. Cấu trúc chính

```text
ShopMan/
├── pom.xml
├── database/
│   └── shopman.sql
└── src/main/
    ├── java/com/shopman/
    │   ├── model/
    │   │   ├── User6.java
    │   │   ├── Customer6.java
    │   │   ├── Employee6.java
    │   │   ├── Supplier6.java
    │   │   ├── Product6.java
    │   │   ├── Invoice6.java
    │   │   ├── InvoiceDetail6.java
    │   │   └── CustomerStat6.java
    │   ├── dao/
    │   │   ├── DAO6.java
    │   │   ├── UserDAO6.java
    │   │   ├── ProductDAO6.java
    │   │   ├── CustomerDAO6.java
    │   │   ├── InvoiceDAO6.java
    │   │   └── InvoiceDetailDAO6.java
    │   ├── servlet/
    │   ├── filter/
    │   └── util/
    └── webapp/WEB-INF/views/
        ├── login.jsp
        ├── UI_SearchItem6.jsp
        ├── UI_ItemDetails6.jsp
        ├── UI_RevenueStatistics6.jsp
        ├── UI_CustomerDetails6.jsp
        └── UI_OrderDetails6.jsp
```

## 3. Cài đặt database

1. Mở MySQL Workbench.
2. Mở `database/shopman.sql` bằng `File > Open SQL Script`.
3. Chạy toàn bộ script.
4. Kiểm tra:

```sql
USE ShopMan_DB;
SHOW TABLES;
SELECT * FROM tblProduct6;
```

> Lưu ý: script có DROP và CREATE lại các bảng. Chỉ chạy trên database thực hành này vì dữ liệu cũ trong các bảng cùng tên sẽ bị xóa.

### Tài khoản chạy thử

| Vai trò | Username | Password |
|---|---|---|
| Nhân viên quản lý | `manager1` | `Manager@123` |
| Khách hàng Nguyễn Văn A | `customer1` | `Customer@123` |
| Khách hàng Trần Thị B | `customer2` | `Customer@123` |
| Nhân viên bán hàng (vai trò chưa có màn hình trong bản này) | `sales1` | `Sales@123` |

Mật khẩu demo lưu dạng PBKDF2-HMAC-SHA256 với salt riêng, không lưu bản rõ.

## 4. Cấu hình kết nối database

Mặc định `Db.java` kết nối:

- Host: `localhost`
- Port: `3306`
- Database: `ShopMan_DB`
- User: `root`
- Password: rỗng

Nếu MySQL root có mật khẩu, nên đặt biến môi trường:

- `SHOPMAN_DB_URL` (không bắt buộc; mặc định đã có URL)
- `SHOPMAN_DB_USER`
- `SHOPMAN_DB_PASSWORD`

Hoặc sửa giá trị mặc định trong `src/main/java/com/shopman/util/Db.java` cho môi trường thực hành cá nhân. Không commit mật khẩu thật lên GitHub.

## 5. Import và chạy trong Eclipse

1. Cài JDK 17, Eclipse IDE for Enterprise Java and Web Developers, MySQL 8+, Apache Tomcat 9.
2. Chọn `File > Import > Maven > Existing Maven Projects`.
3. Chọn thư mục chứa `pom.xml`, nhấn `Finish`.
4. Chờ Maven tải dependency; chuột phải project > `Maven > Update Project`.
5. Chọn `Project > Clean`.
6. Trong tab `Servers`, thêm Tomcat 9.0, sau đó `Add and Remove` project `ShopMan`.
7. Start Tomcat và mở `http://localhost:8080/ShopMan/`.

Hoặc dùng Maven tại thư mục project:

```bash
mvn clean package
```

WAR đầu ra: `target/ShopMan.war`.

## 6. Kịch bản kiểm thử theo Đề 36

### Module 1 – Tìm kiếm mặt hàng

1. Đăng nhập `customer1` / `Customer@123`.
2. Nhập từ khóa `Áo`.
3. Kết quả có `AK01 – Áo khoác da` và `AP05 – Áo phông Cotton`.
4. Chọn AK01 để xem: loại Áo khoác, nhãn hiệu Thời trang Việt, đơn vị Chiếc, giá 500.000đ, tồn 25, nhà cung cấp Công ty May Mặc A, mô tả Áo khoác da cao cấp giữ nhiệt mùa đông.

### Module 2 – Thống kê doanh thu khách hàng

1. Đăng nhập `manager1` / `Manager@123`.
2. Chọn từ `2023-10-01` đến `2023-10-31`.
3. Bảng thống kê dự kiến:

| Mã KH | Họ tên | Số đơn | Tổng doanh thu |
|---|---|---:|---:|
| KH012 | Nguyễn Văn A | 3 | 1.400.000đ |
| KH045 | Trần Thị B | 1 | 500.000đ |

4. Chọn Nguyễn Văn A để xem lịch sử 3 hóa đơn: HD1001, HD10XX (mã demo thay thế vì tài liệu không ghi mã cho giao dịch ngày 23/10) và HD1089.
5. Chọn HD1089 để xem hai dòng AK01 (500.000đ × 1) và AP05 (140.000đ × 2), tổng 780.000đ.

## 7. Ghi chú về khác biệt trong tài liệu

Trong kịch bản chữ (phần mô tả thống kê), Nguyễn Văn A có 3 lần mua: 350.000đ, 270.000đ và 780.000đ, tổng 1.400.000đ. Nhưng wireframe màn hình lịch sử ở phần thiết kế chỉ vẽ 2 dòng HD1001 (350.000đ) và HD1089 (780.000đ), trong khi bảng tổng hợp vẫn ghi 3 đơn và 1.400.000đ.

Để không làm sai tổng doanh thu và số đơn trong thống kê, bản demo thêm hóa đơn HD10XX ngày 23/10/2023 trị giá 270.000đ theo kịch bản chữ. Mã HD10XX chỉ là mã thay thế do tài liệu không cung cấp mã hóa đơn thứ ba. Tương tự, mã HD2010 và ngày 19/10/2023 của Trần Thị B là dữ liệu demo bổ sung vì wireframe chỉ cho biết 1 đơn và 500.000đ. Chi tiết của HD1001/HD10XX cũng là dữ liệu minh họa để cộng đủ số tiền; phần chi tiết HD1089 mới là nội dung khớp trực tiếp wireframe. Nếu giảng viên yêu cầu hiển thị đúng hai dòng của wireframe, cần xác nhận nên ưu tiên bảng wireframe hay kịch bản chữ.

Các giá unitPrice trong chi tiết hóa đơn là giá lịch sử tại thời điểm mua, có thể khác giá hiện tại của danh mục sản phẩm. Bản demo loại hóa đơn có trạng thái `CANCELLED` khỏi thống kê và lịch sử; tài liệu không quy định rõ bộ trạng thái tính doanh thu, nên đây là quy ước nghiệp vụ được ghi nhận trong code.

## 8. Giới hạn phạm vi

Bản sửa tập trung cài đặt hai module được thiết kế chi tiết trong tài liệu: tìm kiếm mặt hàng và thống kê doanh thu khách hàng. Các chức năng quản lý hàng hóa, nhà cung cấp, nhập hàng, đăng ký thành viên, đặt mua, duyệt/đóng gói/giao hàng chưa được cài đặt đầy đủ trong bản này; không nên báo cáo chúng là chức năng đã hoàn thành.

Giao diện hiện ở dạng JSP tối giản để kiểm chứng luồng nghiệp vụ. Bootstrap/CSS và ảnh chụp giao diện nên thực hiện ở bước tiếp theo sau khi kết nối database và kiểm thử logic thành công.
