# CLNotes
Java | Spring Boot | Docker | JWT | MySQL

CLNotes is a **secure backend application for managing personal notes** built with **Spring Boot**.  
The application exposes RESTful APIs that allow authenticated users to create, retrieve, update, and delete notes while enforcing strict **user-level authorization**.

Security is implemented using **Spring Security with JWT authentication**, ensuring stateless session management and secure API access. The application also includes **IDOR (Insecure Direct Object Reference) prevention**, ensuring users can only access resources they own.

The application is **containerized with Docker** and can be run easily using **Docker Compose**, providing a consistent development and deployment environment.

---

# Features

- Secure authentication using **JWT**
- **Spring Security** integration
- **User-specific authorization**
- **IDOR prevention** for resource access
- RESTful APIs for **notes management (CRUD)**
- **Layered architecture** (Controller → Service → Repository)
- **MySQL database integration**
- **Docker containerization**
- **Docker Compose orchestration**

---

# Tech Stack

- Java
- Spring Boot
- Spring Security
- JWT
- JPA / Hibernate
- MySQL
- Docker
- Docker Compose
- Maven
- Git

---

# Architecture

The application follows a **layered architecture**:

Controller Layer  
⬇  
Service Layer  
⬇  
Repository Layer  
⬇  
MySQL Database

---

# Running the Application

Run with Docker Compose

docker compose -f clnotes-compose.yaml up --build

This will start:

- Spring Boot application
- MySQL database

---

# Security

CLNotes includes several security mechanisms:

- JWT-based authentication
- Stateless session management
- Protected endpoints using Spring Security
- User-level authorization
- IDOR prevention by validating note ownership before access

---

# Author

Vicky

Backend Developer

