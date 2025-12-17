# 🧑‍💼 Employee Management Portal  
**Spring Boot + Spring Security + MySQL + Server-Rendered HTML (Thymeleaf-friendly)**

A secure **Employee Management CRUD** web app built with **Spring Boot**, **Spring MVC**, **Spring Security**, and **MySQL**, using a clean **server-rendered UI** (Thymeleaf-friendly).  
Think “internal admin tool vibes”: **login, roles, protected actions, and employee lifecycle management**.

---

## ✨ Highlights
- ✅ Clean layered architecture (**Controller → Service → Repository**)
- 🔐 Spring Security (**form login + logout**) + role-based access control
- 🗄 MySQL + JPA/Hibernate persistence
- 🧾 Server-side validation + clean UI workflow
- 🔒 Password hashing with **BCrypt**

---

## 🚀 Features

### ✅ Core CRUD
- Create, View, Update, Delete employees
- Employee list page
- Employee details page
- Server-side validation with clear error messages  
  (Bean Validation: `@NotBlank`, `@Email`, `@Size`, etc.)

### 🔐 Security
- Form-based login + logout
- BCrypt password hashing
- Roles: `ADMIN`, `HR`, `USER`
- Protected routes:
  - View: allowed for authenticated users
  - Create/Edit: restricted by role
  - Delete: restricted by role

### 🗄 Database
- MySQL integration via Spring Data JPA / Hibernate
- Supports:
  - Auto schema update (`ddl-auto=update`) for dev
  - Manual schema for production (recommended)

---

## 🧱 Layered Architecture
```mermaid
flowchart TB
  U["Browser / UI<br/>HTML (Thymeleaf)"] -->|HTTP Request| F["Spring Security<br/>SecurityFilterChain"]

  F -->|Authenticated & Authorized| D["DispatcherServlet<br/>Spring MVC Front Controller"]
  D --> C["Controller Layer<br/>Spring MVC"]
  C -->|calls| S["Service Layer<br/>Business Logic"]
  S -->|calls| R["Repository Layer<br/>Spring Data JPA"]
  R -->|SQL via JPA/Hibernate| DB[(MySQL Database)]

  DB -->|Result| R
  R -->|returns| S
  S -->|returns| C
  C --> V["View Layer<br/>Thymeleaf + ViewResolver"]
  V -->|HTML Response| U


