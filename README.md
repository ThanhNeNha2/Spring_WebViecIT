👷️ IT Job Portal - Spring Boot Clone

📄 Giới thiệu

Dự án này là một bản clone của các website quảng cáo việc làm IT như TopCV, ITviec, VietnamWorks, v.v. Sử dụng Spring Boot, Spring Security, JPA, JWT Authentication, MySQL.

🚀 Tính năng

💼 Người tuyển dụng:

Đăng nhập/đăng ký

Quản lý công ty, danh sách tin tuyển dụng

Thêm/sửa/xóa bài tuyển dụng

🕵️ Người tìm việc:

Tìm kiếm và ứng tuyển việc làm

Tải lên CV, cập nhật thông tin cá nhân

Lịch sử ứng tuyển

🌐 Tính năng khác:

🌍 Phân quyền Admin/Nhà Tuyển Dụng/Người Dùng

🛠 Quản lý danh mục việc làm theo ngành nghề

🔗 API RESTful sẵn sàng tích hợp frontend

🔧 Công nghệ sử dụng

Backend: Spring Boot, Spring Security, JWT, Spring Data JPA

Database: MySQL

Frontend: (Sẽ tích hợp React/Angular sau)

Khác: Swagger API, Docker, Lombok

👨‍💻 Cách cài đặt và chạy dự án

1. Cài đặt môi trường

Java 17+

Maven 3+

MySQL

2. Cấu hình Database

Mở file src/main/resources/application.properties và chỉnh sửa:

spring.datasource.url=jdbc:mysql://localhost:3306/job_portal
spring.datasource.username=root
spring.datasource.password=yourpassword

Sau đó tạo database job_portal trong MySQL.

3. Chạy dự án

Sử dụng Maven:

mvn spring-boot:run

Hoặc build file .jar rồi chạy:

mvn clean package
java -jar target/job-portal.jar

📲 API Endpoints (Ví dụ)

Method

Endpoint

Mô tả

POST

/auth/register

Đăng ký tài khoản

POST

/auth/login

Đăng nhập nhận JWT

GET

/jobs

Lấy danh sách việc làm

POST

/jobs

Thêm công việc (cần quyền Admin/Nhà Tuyển Dụng)

👨‍💼 Nhóm thực hiện

Võ Chí Thanh

