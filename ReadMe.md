# Commentator API

A Spring Boot REST API for managing articles, user comments (including nested replies), and likes on both articles and comments.  
Includes simple rate-limiting and duplicate-like prevention.

## Features

- **User Management**
    - Create, update and delete users
    - Fetch all articles by a specific user

- **Articles**
    - Create an article
    - Like an article (prevents duplicate likes)
    - Fetch all articles by user
    - Update and delete articles (deletes but comments first)

- **Comments**
    - Add comments to articles
    - Reply to existing comments (nested replies supported)
    - Like comments (prevents duplicate likes)
    - Update and delete comments
    - Fetch all comments for a specific article with nested structure

- **Rate Limiting**
    - Restricts comment creation to **1 comment per user every 10 seconds** using Resilience4j's `@RateLimiter`

- **Swagger API Docs**
    - Integrated with Springdoc OpenAPI for interactive documentation

---

## Tech Stack

- **Java 17**
- **Spring Boot 3**
    - Spring Web
    - Spring Data JPA
    - Spring Validation
- **Hibernate**
- **H2 Database** (in-memory for testing)
- **Lombok**
- **MapStruct** (DTO <-> Entity mapping)
- **Resilience4j** (Rate Limiting)
- **Springdoc OpenAPI** (Swagger UI)

---

## API Endpoints

### **User**
| Method | Endpoint              | Description |
|--------|-----------------------|-------------|
| POST   | `/users`               | Create user |
| GET     | `/users`               | Select user |
| UPDATE | `/users/{userId}`      | Update user |
| DELETE | `/users/{userId}`      | Delete user |

### **Articles**
| Method | Endpoint                     | Description             |
|--------|------------------------------|-------------------------|
| POST   | `/articles`                  | Create article          |
| POST   | `/articles/{articleId}/like` | Like article            |
| GET    | `/articles/user/{userId}`    | Get user's all articles |
| PUT    | `/articles`                  | Update article          |
| DELETE | `/articles/{articleId}`      | Delete article          |

### **Comments**
| Method | Endpoint                     | Description |
|--------|------------------------------|-------------|
| POST   | `/comments/{articleId}`      | Add comment to article or reply to comment |
| POST   | `/comments/{commentId}/like` | Like a comment |
| GET    | `/comments/{articleId}`      | Get all comments for an article (nested replies) |
| PUT    | `/comments/{commentId}`      | Update comment |
| DELETE | `/comments/{commentId}`      | Delete comment |

- **Things can be improved**
    -  Api paths can be more precise.
    -  Responding error messages with more proper way such as creating Global Error Handler (Exceptions).
    -  Implement an endpoint to retrieve a specific user by ID.
    -  When attempting to retrieve a user that does not exist (prior to creation), return a clear response such as "User not found — please create the user first."
    -  When deleting a user, ensure all related articles and comments authored by that user are also removed. This can be achieved using cascade operations in JPA.
    -  Enhance the username update functionality so that if the provided userId does not exist, the service responds with a clear error message indicating that no such user exists.
    -  When deleting an article with associated comments, either:
    1) First remove the comments before deleting the article, and notify the client that the article had comments linked to it.
    2) Configure cascade delete so that associated comments are removed automatically when the article is deleted.

---

## **Running Locally**
Follow these steps to run the application on your local machine:

1. **Clone the repository**
   ```bash
   git clone git@github.com:hasanbeyli/Commentator.git
   cd commentator-api
* Ensure you have Java and Gradle installed
* Java 17+
* Gradle (or use the included Gradle wrapper ./gradlew)
* Build the project
* ./gradlew clean build
* Run the application
* ./gradlew bootRun
* Or using the generated JAR:
* java -jar build/libs/commentator-0.0.1-SNAPSHOT.jar
* Access the application
* Swagger UI: http://localhost:8080/swagger-ui.html
* H2 Console: http://localhost:8080/h2-console
---
