# 🧑‍💼 Employee Management Portal  
**Spring Boot + Spring Security + MySQL + HTML (Server-Rendered UI)**

A secure **Employee Management CRUD** web application built with **Spring Boot**, **Spring MVC**, **Spring Security**, and **MySQL**, using a clean **server-rendered HTML UI** (Thymeleaf-friendly).  
Built like a real internal admin tool: **login, roles, protected actions, and employee lifecycle management**.

---

## ✨ Highlights
- ✅ Clean layered architecture (Controller → Service → Repository)
- 🔐 Spring Security login + role-based access control
- 🗄 MySQL + JPA/Hibernate persistence
- 🧾 Server-side validation + neat UI workflow

---

## 🚀 Features

### ✅ Core
- Create, View, Update, Delete employees
- Employee list page (search/filter optional)
- Employee details page
- Input validation with meaningful error messages

### 🔐 Security
- Form login + logout
- Password hashing with **BCrypt**
- Role-based access control (`ADMIN`, `HR`, `USER`)
- Protected routes (edit/delete restricted by role)

### 🗄 Database
- MySQL integration using Spring Data JPA / Hibernate
- JPA-based schema generation (`ddl-auto=update`) or manual schema support

---

## 🧱 Architecture

This project follows a **Layered (N-Tier) Architecture**:

### 🔁 Request Flow
```text
Browser (HTML UI)
   ↓ HTTP Request
Controller Layer (Spring MVC)
   ↓ calls
Service Layer (Business Logic)
   ↓ calls
Repository Layer (Spring Data JPA)
   ↓ queries
MySQL Database
   ↑ returns
Repository → Service → Controller → HTML View Response
