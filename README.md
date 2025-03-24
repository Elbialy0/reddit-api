# Reddit API Clone

A robust Reddit API clone built with Spring Boot, implementing core Reddit functionality including user management, subreddits, posts, and comments.

## Features

- 🔐 User Authentication & Authorization (JWT-based)
- 👥 User Registration and Profile Management
- 📝 Subreddit Creation and Management
- 📋 Post Creation and Management
- 💬 Comment System with Nested Comments
- ⬆️ Upvote/Downvote System
- 📧 Email Verification
- 📚 Swagger API Documentation
- �� Docker Support

## Tech Stack

- **Backend Framework:** Spring Boot 3.4.3
- **Language:** Java 17
- **Database:** MySQL
- **ORM:** Spring Data JPA
- **Security:** Spring Security with JWT
- **Documentation:** Swagger/OpenAPI
- **Build Tool:** Maven
- **Containerization:** Docker & Docker Compose
- **Additional Tools:**
  - Lombok
  - MapStruct
  - Java Mail API
  - PrettyTime

## Prerequisites

- Java 17 or higher
- Docker and Docker Compose (for containerized setup)
- MySQL (for local development)

## Getting Started

### Option 1: Using Docker (Recommended)

1. Clone the repository:
```bash
git clone https://github.com/Elbialy0/reddit-api.git
cd reddit-api
```

2. Start the application using Docker Compose:
```bash
docker-compose up -d
```

The application will be available at:
- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui/index.html

### Option 2: Local Development

1. Clone the repository:
```bash
git clone https://github.com/Elbialy0/reddit-api.git
cd reddit-api
```

2. Configure MySQL database:
   - Create a new database named `spring-reddit-clone`
   - Update the database credentials in `src/main/resources/application.properties`

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

## API Documentation

Once the application is running, you can access the Swagger UI documentation at:
http://localhost:8080/swagger-ui/index.html

## Docker Configuration

The project includes Docker configuration for both the application and database:

- **MySQL Container:**
  - Port: 3307
  - Database: spring-reddit-clone
  - Username: user
  - Password: userpassword

- **Spring Boot Container:**
  - Port: 8080
  - Connected to MySQL container via Docker network

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Reddit for inspiration
- Spring Boot team for the excellent framework
- All contributors and maintainers

