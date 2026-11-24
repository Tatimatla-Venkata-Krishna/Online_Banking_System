# Online Banking System

A simple online banking web application built with Java, Spring Boot, Thymeleaf, Spring Data JPA, and MySQL. It provides customer registration/login and basic banking operations with transaction records.

## Features

- Customer registration and generated account numbers
- Login and dashboard
- Deposits and withdrawals
- Money transfer between accounts
- Transaction history and recent transactions
- Deposit and withdrawal totals

## Tech stack

- Java 17
- Spring Boot
- Spring MVC
- Thymeleaf
- Spring Data JPA / Hibernate
- MySQL-compatible database (configured for TiDB Cloud)
- Maven

## Requirements (running without Docker)

- Java 17 or later
- MySQL-compatible database and database credentials

## Database configuration

Configure these environment variables before starting the app:

| Variable | Description |
|---|---|
| `DB_URL` | JDBC URL for the database |
| `DB_USERNAME` | Database username |
| `DB_PASSWORD` | Database password |

Example for a local MySQL database (adjust database name and credentials):

```bash
export DB_URL='jdbc:mysql://localhost:3306/online_banking'
export DB_USERNAME='root'
export DB_PASSWORD='your_password'
```

On Windows PowerShell:

```powershell
$env:DB_URL='jdbc:mysql://localhost:3306/online_banking'
$env:DB_USERNAME='root'
$env:DB_PASSWORD='your_password'
```

## Run locally

From the project directory:

```bash
./mvnw spring-boot:run
```

On Windows:

```bat
mvnw.cmd spring-boot:run
```

Open http://localhost:8080 in your browser.

## Build the JAR

```bash
./mvnw clean package
```

Windows:

```bat
mvnw.cmd clean package
```

## Run with Docker

Build the image:

```bash
docker build -t online-banking .
```

Run it (provide database details):

```bash
docker run --rm -p 8080:8080 \
  -e DB_URL='jdbc:mysql://host.docker.internal:3306/online_banking' \
  -e DB_USERNAME='root' \
  -e DB_PASSWORD='your_password' \
  online-banking
```

On Windows PowerShell, use a single line:

```powershell
docker run --rm -p 8080:8080 -e DB_URL='jdbc:mysql://host.docker.internal:3306/online_banking' -e DB_USERNAME='root' -e DB_PASSWORD='your_password' online-banking
```

For a remote MySQL/TiDB database, use its JDBC URL and credentials instead. Then open http://localhost:8080.

## Important security note

This project is a learning/demo application, not production banking software. It currently includes simple login and transaction logic and should not be deployed for real financial use without implementing security controls (for example, password hashing, proper authentication/authorization, CSRF protection, validation, secure transaction handling, and auditing). Never commit database credentials or real customer data.
