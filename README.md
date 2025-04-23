# JavaPro Final Project: Internet Banking

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen?style=for-the-badge&logo=spring-boot)
![Build](https://img.shields.io/badge/Build-Maven-blue?style=for-the-badge&logo=apache-maven)
![License](https://img.shields.io/badge/License-Educational-lightgrey?style=for-the-badge)

📩 **A Java Spring Boot application for demonstrating:**
- Spring Security authentication
- Google OAuth2 authentication integration
- Rates retriever integration
- Sending email messages

## 📋 Project Description

This project implements a Java Spring Boot application.
User can open an account, make deposit an account and transfer between accounts, if currencies are different,
automatically exchange by rate.
However, an application retrieves and displays the EUR/UAH exchange rate from an external API 
and presents it on a web page using HTML and Thymeleaf.

Admin can manage users and clients.

This project implements email sending via an SMTP server using Spring Mail.
The login and password for email sending are stored separately in the .env.properties file.

---

## 🛠️ Technologies Used

- Java 17
- Spring Boot 3.2
- Spring Security
  Spring Security OAuth2 Client
- Spring Mail
- Maven
- Thymeleaf & Bootstrap 5
- Gmail SMTP Server
- Google OAuth2 API
- Fixer API (for currency rates)

---

## 🚀 Quick Start

1. **Clone the repository:**
   ```bash
   git clone https://github.com/IvashDima/JavaPro_HW16_RateRetriever.git

2. **Navigate to the project directory:**
   ```bash
   cd JavaPro_HW16_RateRetriever

3. **Create a .env.properties file in the project root:**
   ```bash
   spring.mail.username=your_email@gmail.com
   spring.mail.password=your_password
   spring.security.oauth2.client.registration.google.client-id=your_client-id
   spring.security.oauth2.client.registration.google.client-secret=your_client-secret

⚠️ Important: Set your Authorized redirect URIs in Google Cloud Console correctly. 
Example: http://localhost:8886/login/oauth2/code/google

4. **Run the application:**

   ```bash
   mvn spring-boot:run

5. **Access the application Open your browser and navigate to:**
   
   http://localhost:8886/

You will be redirected to the login page.

## 📂 Project Structure

      src/
      └── main/
         ├── java/
         │    └── org/example/springbank/
         │         ├── config/            # AppConfig, Spring security & OAuth2 security configuration
         │         ├── controllers/       # Controllers handling HTTP & API requests
         │         ├── dto/               # DTO for OAuth2 (results, user) & Mail notification
         │         ├── enums/             # OAuth2 security configuration
         │         ├── exceptions/        # OAuth2 security configuration
         │         ├── json/              # OAuth2 security configuration
         │         ├── mail/              # OAuth2 security configuration
         │         ├── models/            # OAuth2 security configuration
         │         ├── repositories/      # OAuth2 security configuration
         │         ├── retrievers/        # OAuth2 security configuration
         │         ├── services/          # OAuth2 security configuration
         │         └── Application.java   # Main application class
         └── resources/
               ├── templates/             # HTML templates
               └── application.properties # Application configuration

## 📌 Important Notes

Google might require OAuth2 consent screen verification for apps requesting sensitive scopes.
Always protect sensitive information (like client secret) in production (use environment variables, vaults, etc.).
This project is educational and focuses on OAuth2 integration basics.

To use Gmail SMTP, you must allow access for less secure apps or use an App Password if you have two-factor authentication enabled. 
If emails are not being sent, double-check your Gmail security settings.

Make sure to handle exceptions gracefully if the exchange rate is unavailable.
The /rate endpoint retrieves the EUR/UAH rate, which can be updated via the button on the webpage.


## 👨‍💻 Author

Name: Dmytro Ivashchenko

Email: dnytsyk@gmail.com

## 📝 License

This project is provided for educational purposes only and does not have a specific license.
Feel free to use and adapt it for your learning and personal projects!

