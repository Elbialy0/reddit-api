# Reddit API
## Description

This project is a clone of the Reddit API, which allows users to create and manage subreddits, posts, and comments. 
It is built using Spring Boot and uses a MySQL database to store the data.

## Features
- User registration and authentication (JWT-based security)
- Subreddit creation and management
- Post creation and management
- Comment creation and management
- Upvote and downvote functionality for posts and comments
- Follows RESTful best practices

## Technologies Used
- Java 17
- Spring Boot 3.4.3
- Spring Security (JWT-based authentication)
- Spring Data JPA
- MySQL
- Lombok
- MapStruct
- Maven
- Java mail API
- Swagger documentation

## Installation 
### pre-requisites

- Java 17
- MySQL
- Maven
### Steps

1. Clone the repository
 ```bash
   git clone https://github.com/Elbialy0/reddit-api.git
   cd reddit-api
 ```
2. Configure the MySQL database

```bash 
spring.datasource.url=jdbc:postgresql://localhost:5432/reddit_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. Build the project
```bash 
mvn clean install
mvn spring-boot:run
```
4. Access the API at http://localhost:8080
5. Access the Swagger documentation at http://localhost:8080/swagger-ui/index.html

