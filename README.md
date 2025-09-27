# Gestion des Emplois du Temps – BackEnd

This is the **BackEnd application** of the academic project **Gestion des Emplois du Temps** (Timetable Management System), built with **Spring Boot**.  
It provides the API and business logic to manage timetables, professors, students, notifications, and administrative operations.

---

## 📌 Project Overview

The backend serves as the **core engine** of the system, offering RESTful APIs consumed by the Angular FrontEnd.  
Key responsibilities include:
- Managing academic entities (professors, students, courses, timetables).
- Handling **CRUD operations** for administrators (add, update, delete users and schedules).
- Supporting **professor requests** for make-up sessions (*séances de rattrapage*).
- Sending **notifications** to students and professors about schedule changes.
- Ensuring secure authentication and **role-based access control** using **OAuth2**.

---

## 🚀 Features

- 🛠️ **Admin Role**  
  - Full CRUD operations on professors, students, and schedules.  
  - Validates professor requests for rattrapage sessions.  
  - Oversees timetable updates.  

- 👩‍🏫 **Professor Role**  
  - View and manage personal timetable.  
  - Submit requests for make-up sessions (rattrapage).  
  - Receive notifications on approved or denied requests.  

- 🎓 **Student Role**  
  - View personalized timetable.  
  - Receive instant notifications when changes or rattrapage sessions are scheduled.  

- 🔔 **Notifications**: Event-driven system to notify users of timetable changes.  
- 🔐 **Security**: Authentication and authorization handled via **Spring Security with OAuth2**.  
- 📊 **REST APIs** for integration with the Angular frontend.  

---

## 🛠️ Tech Stack

- **Framework**: Spring Boot (v3+)  
- **Language**: Java  
- **Database**: MySQL  
- **Security**: Spring Security + OAuth2  
- **Build Tool**: Maven or Gradle  
- **Other Tools**: JPA/Hibernate, Lombok, REST  

---

## 📂 Project Structure

```bash
src/
 ├── main/
 │   ├── java/com/ensak/emploi/
 │   │    ├── web/      # REST Controllers
 │   │    ├── services/         # Business logic
 │   │    ├── repository/      # JPA Repositories
 │   │    ├── model/           # Entities
 │   │    └── EmploiApplication.java  # Main entry point
 │   └── resources/
 │        ├── application.properties  # Configurations (DB, OAuth2, etc.)
 └── test/                     # Unit & integration tests


▶️ Steps to Run the Project

# 1. Clone the repository
git clone https://github.com/chorfichaymaa101/university-schedule-backend.git
cd Gestions-des-emplois-du-temps-ENSA-BackEnd

# 2. Configure the database in application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/emploi_db
    username: root
    password: yourpassword
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

# 3. Configure OAuth2 (example with Google)
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: your-client-id
            client-secret: your-client-secret
            scope: profile,email
        provider:
          google:
            authorization-uri: https://accounts.google.com/o/oauth2/auth
            token-uri: https://oauth2.googleapis.com/token
            user-info-uri: https://www.googleapis.com/oauth2/v3/userinfo

# 4. Build the project
mvn clean install

# 5. Run the backend
mvn spring-boot:run

# 6. Access the backend at:
http://localhost:8080
