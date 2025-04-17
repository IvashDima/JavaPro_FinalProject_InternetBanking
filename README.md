# JavaPro Homework 15: Email Sender

📩 **A Java Spring Boot application for sending email messages.**

## Project Description

This project implements email sending via an SMTP server using Spring Boot and Spring Mail.  
The login and password for email sending are stored separately in the `.env.properties` file.

---

## Technologies Used

- Java 17
- Spring Boot
- Spring Mail
- Maven
- Gmail SMTP Server

---

## Quick Start

1. **Clone the repository:**
   ```bash
   git clone https://github.com/IvashDima/JavaPro_HW15_Email.git

2. **Navigate to the project directory:**
   ```bash
   cd JavaPro_HW15_Email

3. **Create a .env.properties file in the project root:**

properties

spring.mail.username=your_email@gmail.com
spring.mail.password=your_password
spring.security.oauth2.client.registration.google.client-id=your_client-id
spring.security.oauth2.client.registration.google.client-secret=your_client-secret


4. **Run the application:**

   ```bash
   mvn spring-boot:run
