
# Quiz App API - Spring Boot

## Overview

This project implements a simple Quiz App using Spring Boot. The API allows users to:

1. Start a new quiz session.
2. Get a random multiple-choice question from the database.
3. Submit answers for the questions.
4. Get the total number of questions answered, along with the correct and incorrect submissions.

The application uses an **H2 Database** for simplicity, and is seeded with a set of questions and a single user.

## Assumptions

- The application assumes the presence of a database with a **single user** and a **set of pre-seeded questions**.
- The **Quiz Session** is linked to the user, and every time the user starts a quiz, a new session is created, and the previous session is deactivated.
- After answering all the questions, the quiz session ends.
- The number of correct and incorrect answers is tracked and can be retrieved via the stats API.

## Technologies Used

- Spring Boot
- H2 Database (In-memory)
- Java
- Spring Web
- Spring Data JPA

## API Endpoints

### 1. Start Quiz Session

**POST** `/api/v1/quiz/start{userId}`

Starts a new quiz session for the user.

**Request Parameters:**

- `userId` (Long) - The ID of the user.

**Response:**

- `"Session Created, Quiz Started Successfully...."`

---

### 2. Get Random Question

**GET** `/api/quiz/v1/question{userId}`

Fetches a random multiple-choice question from the database.

**Response:**

```json
{
  "questionId": 1,
  "questionText": "What is the capital of France?",
  "optionA": "Berlin",
  "optionB": "Madrid",
  "optionC": "Paris",
  "optionD": "Rome"
}
```

---

### 3. Submit Answer

**POST** `/api/v1/quiz/answer{userId}`

Submits the answer for a specific question.

**Request Body:**

```json
{
  "questionId": 1,
  "correctAnswer": "C"
}
```

**Response:**

- `"Answer submitted successfully."`

---

### 4. Get Quiz Stats

**GET** `/api/quiz/stats{userId}`

Fetches the user's quiz stats: the total number of correct and incorrect answers.

**Request Parameters:**

- `userId` (Long) - The ID of the user.

**Response:**

```json
{
  "correctAnswers": 3,
  "incorrectAnswers": 1
}
```

---

## Database Setup

The application uses H2 as the in-memory database. By default, it is preconfigured to use a set of questions and a user. You can access the H2 console at:

```
http://localhost:8080/h2-console
```

Use the following credentials:

- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: `password`

## Running the Application

1. Clone the repository to your local machine.
2. Navigate to the project directory.
3. Build the project using Maven or Gradle.

For Maven:

```bash
mvn clean install
```

4. Run the application:

```bash
mvn spring-boot:run
```

The application will start on port `8080`.

## Conclusion

This is a simple yet functional quiz app API that demonstrates handling basic CRUD operations, random data retrieval, and session management using Spring Boot and H2 database.
