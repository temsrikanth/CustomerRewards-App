# Customer Rewards Application

This is a Spring Boot application that provides a RESTful API for managing customer data and calculating rewards based on their transactions.

## Features

-   **Customer Management**: Create and retrieve customer information.
-   **Transaction Tracking**: Record customer transactions.
-   **Reward Calculation**: Calculate reward points based on transaction amounts.

## Technologies Used

-   **Java 17**: The core programming language.
-   **Spring Boot 3.3.2**: The framework for building the application.
-   **Maven**: The build automation tool.
-   **Spring Data JPA**: For data access and persistence.
-   **H2 Database**: An in-memory database used for development and testing.

## Getting Started

### Prerequisites

-   Java Development Kit (JDK) 17 or higher
-   Maven

### Running the Application

1.  Clone this repository to your local machine:
    ```bash
    git clone [https://github.com/](https://github.com/temsrikanth/CustomerRewards-App.git)
    ```
2.  Navigate to the project directory:
    ```bash
    cd CustomerRewards-App
    ```
3.  Run the application using the Spring Boot Maven plugin:
    ```bash
    mvn spring-boot:run
    ```
The application will start on `http://localhost:8080`.

## API Endpoints

The API provides the following endpoints to interact with the rewards system.

### Create a New Customer
Adds a new customer to the database.

-   **URL**: `/api/rewards/customers`
-   **Method**: `POST`
-   **Request Body**:
    ```json
    {
      "customerId": "C001",
      "name": "Srikanth",
      "email": "srikanth@example.com"
    }
    ```
### Add all transcations for customer
Adds a new customer to the database.

-   **URL**: `/api/rewards/transactions`
-   **Method**: `POST`
-   **Request Body**:
    ```json
    [
      {
        "customerId": "C001",
        "amount": 120,
        "date": "2025-06-15"
      },
      {
        "customerId": "C001",
        "amount": 85,
        "date": "2025-07-20"
      },
      {
        "customerId": "C001",
        "amount": 45,
        "date": "2025-08-01"
      },
      {
        "customerId": "C001",
        "amount": 150,
        "date": "2025-08-10"
      }
    ]
      ```

### Get Customer Rewards
Retrieves the reward points for a specific customer.

-   **URL**: `/api/rewards/{customerId}`
-   **Method**: `GET`
-   **Example Response**:
    ```json
    {
    "status": "SUCCESS",
    "message": "OK",
    "data": {
        "customerId": "C001",
        "monthlyPoints": {
            "JUNE": 90,
            "AUGUST": 150,
            "JULY": 35
        },
        "totalPoints": 275
     }
     }
    ```
