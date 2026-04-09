# 🚀 AuthBridge – Full Stack Authentication System

A secure and scalable authentication system built using **Spring Boot** and **React (Vite)**, implementing modern authentication mechanisms like JWT and OAuth2.

---

## ✨ Features

- 🔐 **JWT-Based Authentication**
  - Access & Refresh token lifecycle  
  - Stateless and scalable architecture  

- 🍪 **Secure Token Handling**
  - HttpOnly cookies for refresh tokens  
  - Protection against XSS & CSRF (OWASP aligned)  

- 👤 **User Authentication**
  - Register & Login with credentials  
  - Secure password storage  

- 🔑 **OAuth2 Integration**
  - Google & GitHub login support  

- ⚛️ **Modern Frontend**
  - React + Vite  
  - Protected routes  
  - Seamless authentication flow  

- 🐳 **Containerized Setup**
  - Docker-ready architecture  
  - Cloud deployment friendly  

---

## 🧱 Tech Stack

### ⚙️ Backend
- Spring Boot 3.x  
- Spring Security 6.x  
- Spring Data JPA  
- MySQL  
- JWT Authentication  
- OAuth2 Client  
- Lombok  

### 🖥️ Frontend
- React (Vite)  
- Tailwind CSS  
- Axios  
- React Router DOM  

---

## 📁 Project Structure
auth-app-boot-react/
│
├── backend/ # Spring Boot Backend
│ ├── src/
│ ├── pom.xml
│ └── application.yml
│
├── frontend/ # React Frontend
│ ├── src/
│ └── package.json
│
└── README.md


---

## 🔐 API Overview

| Method | Endpoint        | Description              |
|--------|----------------|--------------------------|
| POST   | /auth/register | Register user            |
| POST   | /auth/login    | Authenticate user        |
| POST   | /auth/refresh  | Refresh access token     |
| GET    | /user/me       | Get authenticated user   |

---

## 🎯 Key Highlights

- Stateless authentication using JWT  
- Secure refresh token handling via cookies  
- Designed with **industry-standard security practices (OWASP)**  
- Production-oriented backend architecture  
- Real-world authentication flow implementation  

---

## 📬 Contact

**Swapnil Thakur**  
📧 thakur7swapnil@gmail.com  
