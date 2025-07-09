# VaultNote 🗝️

VaultNote is a secure, scalable backend service for creating and managing personal notes with optional public sharing, encryption, expiry, and tagging support. Designed with scalability and extensibility in mind.

## 🔐 Features

- User Authentication using JWT
- Secure note creation with AES encryption
- Private and Public Notes
- Note Expiry Support
- Tag-based Note Classification
- MongoDB Atlas integration
- Modular architecture for easy scaling

## 🚀 Upcoming Enhancements

- Swagger API Documentation
- Redis caching for faster access
- Email notifications (e.g., note expiring)
- Rate limiting & request throttling
- Frontend with React (optional)

## 📦 Tech Stack

- **Backend**: Java, Spring Boot 3
- **Security**: Spring Security, JWT
- **Database**: MongoDB (Atlas)
- **Utilities**: Lombok, AES Encryption, JJWT
- **Build Tool**: Maven

## 📂 API Overview

| Endpoint                     | Method | Auth Required | Description                            |
|-----------------------------|--------|----------------|----------------------------------------|
| `/public/health-check`      | GET    | ❌             | Health check API                       |
| `/public/signup`            | POST   | ❌             | Register a new user                    |
| `/public/login`             | POST   | ❌             | Authenticate user and return JWT       |
| `/public/notes`             | GET    | ❌             | Get all public notes                   |
| `/public/notes/{id}`        | GET    | ❌             | Get a specific public note by ID       |
| `/user`                     | GET    | ✅             | Get logged-in user’s details           |
| `/user`                     | PUT    | ✅             | Update logged-in user’s details        |
| `/user/notes`               | GET    | ✅             | Get all notes of the logged-in user    |
| `/user/notes/{id}`          | GET    | ✅             | Get a specific note of the user        |
| `/user/notes`               | POST   | ✅             | Create a new note                      |
| `/user/notes/{id}`          | PUT    | ✅             | Update a note                          |
| `/user/notes/{id}`          | DELETE | ✅             | Delete a note                          |

> Use JWT Bearer token in the `Authorization` header for authenticated requests.

## 🛠️ Local Setup

```bash
git clone https://github.com/Ayush2102/vaultnote.git
cd vaultnote
mvn clean install
