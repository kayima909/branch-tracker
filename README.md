# 🏦 Branch Tracker — Multi-Branch Loan Management & Intelligence System

A production-ready REST API built with **Java 21** and **Spring Boot 3** that enables companies with multiple branches to track loan operations, monitor performance, and analyse financial data across all offices from one centralized system.

---

##  Features

- **JWT Authentication** — Secure login with role-based access control
- **Multi-Branch Management** — Track operations across unlimited branches
- **Loan Lifecycle Tracking** — From application to completion or default
- **Client Management** — Full KYC registration per branch
- **Payment Recording** — Automatic balance updates on every repayment
- **Financial Analytics** — Outstanding balances, collections, and branch performance
- **Role-Based Access** — HQ Admin sees everything; Branch Managers see their branch only
- **Soft Delete Pattern** — No data is ever permanently lost
- **Exception Handling** — Consistent error responses across all endpoints

---

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot 3.4.3 | Application framework |
| Spring Security | Authentication & authorization |
| JWT (jjwt 0.12.6) | Stateless token-based auth |
| Spring Data JPA | Database ORM |
| PostgreSQL | Relational database |
| Hibernate | SQL generation & schema management |
| BCrypt | Password hashing |
| Lombok | Boilerplate reduction |
| Maven | Dependency management |
| Postman | API testing |

---

## 👥 User Roles

| Role | Access |
|---|---|
| `HQ_ADMIN` | Full access — all branches, all data, all analytics |
| `BRANCH_MANAGER` | Their branch only — clients, loans, payments, reports |
| `LOAN_OFFICER` | Create and view clients and loans in their branch |

---

## 📁 Project Structure

```
src/main/java/com/branchtracker/branchtracker/
├── config/
│   └── SecurityConfig.java
├── controllers/
│   ├── AuthController.java
│   ├── BranchController.java
│   ├── ClientController.java
│   ├── LoanController.java
│   ├── PaymentController.java
│   └── UserController.java
├── dto/
│   └── request/
│       ├── LoginRequest.java
│       └── RegisterRequest.java
├── entity/
│   ├── Branch.java
│   ├── Client.java
│   ├── Loan.java
│   ├── Payment.java
│   └── User.java
├── enums/
│   ├── LoanStatus.java
│   ├── PaymentStatus.java
│   └── Role.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
├── repositories/
│   ├── BranchRepository.java
│   ├── ClientRepository.java
│   ├── LoanRepository.java
│   ├── PaymentRepository.java
│   └── UserRepository.java
├── response/
│   ├── AuthResponse.java
│   ├── BranchResponse.java
│   ├── ClientResponse.java
│   ├── LoanResponse.java
│   └── PaymentResponse.java
├── security/
│   ├── CustomUserDetailsService.java
│   ├── JwtFilter.java
│   └── JwtUtil.java
└── services/
    ├── AuthService.java
    ├── BranchService.java
    ├── ClientService.java
    ├── LoanService.java
    ├── PaymentService.java
    └── UserService.java
```

---

## ⚙️ Setup & Installation

### Prerequisites
- Java 21
- PostgreSQL
- Maven

### 1. Clone the repository
```bash
git clone https://github.com/kayima909/branch-tracker.git
cd branch-tracker
```

### 2. Create the database
```sql
CREATE DATABASE branch_tracker_db;
```

### 3. Configure application.properties
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/branch_tracker_db
spring.datasource.username=your_username
spring.datasource.password=your_password
jwt.secret=your_jwt_secret
jwt.expiration=86400000
```

### 4. Run the application
```bash
mvn spring-boot:run
```

The app starts on `http://localhost:8080`

---

## 🔐 Authentication

All endpoints except `/api/auth/**` require a JWT token.

**Register:**
```http
POST /api/auth/register
Content-Type: application/json

{
    "fullName": "David Kayima",
    "email": "david@example.com",
    "password": "yourpassword",
    "role": "HQ_ADMIN"
}
```

**Login:**
```http
POST /api/auth/login
Content-Type: application/json

{
    "email": "david@example.com",
    "password": "yourpassword"
}
```

Use the returned token in all subsequent requests:
```
Authorization: Bearer <your_token>
```

---

## API Endpoints

### Branches
| Method | Endpoint | Role | Description |
|---|---|---|---|
| POST | `/api/branches` | HQ_ADMIN | Create branch |
| GET | `/api/branches` | HQ_ADMIN | Get all branches |
| GET | `/api/branches/{id}` | HQ_ADMIN, BRANCH_MANAGER | Get branch by ID |
| PUT | `/api/branches/{id}` | HQ_ADMIN | Update branch |
| PATCH | `/api/branches/{id}/deactivate` | HQ_ADMIN | Deactivate branch |

### Clients
| Method | Endpoint | Role | Description |
|---|---|---|---|
| POST | `/api/clients` | All roles | Create client |
| GET | `/api/clients` | HQ_ADMIN | Get all clients |
| GET | `/api/clients/branch/{id}` | HQ_ADMIN, BRANCH_MANAGER | Get clients by branch |
| GET | `/api/clients/{id}` | All roles | Get client by ID |
| PUT | `/api/clients/{id}` | HQ_ADMIN, BRANCH_MANAGER | Update client |

### Loans
| Method | Endpoint | Role | Description |
|---|---|---|---|
| POST | `/api/loans` | All roles | Create loan |
| GET | `/api/loans` | HQ_ADMIN | Get all loans |
| GET | `/api/loans/branch/{id}` | HQ_ADMIN, BRANCH_MANAGER | Get loans by branch |
| GET | `/api/loans/status/{status}` | HQ_ADMIN | Get loans by status |
| PATCH | `/api/loans/{id}/status` | HQ_ADMIN, BRANCH_MANAGER | Update loan status |
| GET | `/api/loans/analytics/outstanding` | HQ_ADMIN | Total outstanding balance |
| GET | `/api/loans/analytics/outstanding/branch/{id}` | HQ_ADMIN, BRANCH_MANAGER | Branch outstanding balance |

### Payments
| Method | Endpoint | Role | Description |
|---|---|---|---|
| POST | `/api/payments` | All roles | Record payment |
| GET | `/api/payments/loan/{id}` | All roles | Get payments by loan |
| GET | `/api/payments/branch/{id}` | HQ_ADMIN, BRANCH_MANAGER | Get payments by branch |
| GET | `/api/payments/analytics/collected` | HQ_ADMIN | Total collected all branches |
| GET | `/api/payments/analytics/collected/branch/{id}` | HQ_ADMIN, BRANCH_MANAGER | Total collected by branch |

---

## 🗄️ Database Schema

```
branches ──< users
branches ──< clients ──< loans ──< payments
branches ──< loans
branches ──< payments
```

---

## 🔮 Upcoming Features

- [ ] AI-powered natural language reports (Claude API integration)
- [ ] Loan risk scoring using client history
- [ ] Anomaly detection for branch performance
- [ ] Docker containerisation
- [ ] Railway cloud deployment
- [ ] Frontend dashboard (HTML + CSS + JS)
- [ ] Email notifications for overdue loans
- [ ] @Scheduled auto-checks for defaulted loans

---

## 👨🏾‍💻 Author

**David Kayima**
- Junior Backend Developer
- Uganda
- GitHub: [@kayima909](https://github.com/kayima909)
- LinkedIn: [David Kayima](https://linkedin.com/in/david-kayima)

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).