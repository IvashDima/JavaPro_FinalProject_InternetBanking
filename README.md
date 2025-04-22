# JavaPro Homework 16: Rate Retriever

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen?style=for-the-badge&logo=spring-boot)
![Build](https://img.shields.io/badge/Build-Maven-blue?style=for-the-badge&logo=apache-maven)
![License](https://img.shields.io/badge/License-Educational-lightgrey?style=for-the-badge)

📩 **A Java Spring Boot application for sending email messages.**

## 📋 Project Description

This project implements email sending via an SMTP server using Spring Boot and Spring Mail.  
The login and password for email sending are stored separately in the `.env.properties` file.

---

## 🛠️ Technologies Used

- Java 17
- Spring Boot
- Spring Mail
- Maven
- Gmail SMTP Server

---

## 🚀 Quick Start

1. **Clone the repository:**
   ```bash
   git clone https://github.com/IvashDima/JavaPro_HW15_Email.git

2. **Navigate to the project directory:**
   ```bash
   cd JavaPro_HW15_Email

3. **Create a .env.properties file in the project root:**

   ```properties
   spring.mail.username=your_email@gmail.com
   spring.mail.password=your_password
   spring.security.oauth2.client.registration.google.client-id=your_client-id
   spring.security.oauth2.client.registration.google.client-secret=your_client-secret

4. **Run the application:**

   ```bash
   mvn spring-boot:run

## 📌 Important Notes

   To use Gmail SMTP, you must allow access for less secure apps or use an App Password if you have two-factor authentication enabled.
   If emails are not being sent, double-check your Gmail security settings.


## 👨‍💻 Author

Name: Dmytro Ivashchenko

Email: dnytsyk@gmail.com

## 📝 License

This project is provided for educational purposes only and does not have a specific license.
Feel free to use and adapt it for your learning and personal projects!

