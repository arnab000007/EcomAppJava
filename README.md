# EcomAppJava

EcomAppJava is a Spring Boot-based e-commerce application. This project demonstrates a simple e-commerce platform with user authentication, address management, and role-based access control.

## Features

- User Signup and Login
- Role-based Access Control
- Address Management
- JWT Authentication

## Technologies Used

- Java
- Spring Boot
- Spring Security
- Hibernate
- Maven
- Lombok

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6.0 or higher
- A database (e.g., MySQL, PostgreSQL)

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/arnab000007/EcomAppJava.git
    cd ecomappjava
    ```

2. Configure the database: 
    - Update the `application.properties` file in `src/main/resources` with your database configuration.

3. Build the project:
    ```sh
    mvn clean install
    ```

4. Run the application:
    ```sh
    mvn spring-boot:run
    ```

## API Endpoints

## API Endpoints

### Authentication

- **Signup**
    - **URL:** `/auth/signup`
    - **Method:** `POST`
    - **Request Body:**
        ```json
        {
            "email": "user@example.com",
            "password": "password",
            "name": "User Name",
            "username": "username",
            "roles": ["NORMAL_USER"]
        }
        ```
    - **Response:** `201 Created` with user details

- **Login**
    - **URL:** `/auth/login`
    - **Method:** `POST`
    - **Request Body:**
        ```json
        {
            "email": "user@example.com",
            "password": "password"
        }
        ```
    - **Response:** `200 OK` with JWT token

### Address Management

- **Add Address**
    - **URL:** `/address/`
    - **Method:** `POST`
    - **Request Body:**
        ```json
        {
            "addressLine1": "123 Main St",
            "addressLine2": "Apt 4B",
            "city": "New York",
            "countryState": "NY",
            "country": "USA",
            "zipCode": "10001",
            "phoneNumber": "123-456-7890",
            "addressType": "HOME",
            "isDefault": true
        }
        ```
    - **Response:** `200 OK` with added address details

- **Update Address**
    - **URL:** `/address/{id}`
    - **Method:** `PUT`
    - **Request Body:**
        ```json
        {
            "addressLine1": "123 Main St",
            "addressLine2": "Apt 4B",
            "city": "New York",
            "countryState": "NY",
            "country": "USA",
            "zipCode": "10001",
            "phoneNumber": "123-456-7890",
            "addressType": "HOME",
            "isDefault": true
        }
        ```
    - **Response:** `200 OK` with updated address details

### Cart Management

- **Remove from Cart**
    - **URL:** `/cart/remove`
    - **Method:** `DELETE`
    - **Request Body:**
        ```json
        {
            "productId": 1,
            "userId": 1,
            "quantity": 1
        }
        ```
    - **Response:** `200 OK` with updated cart details

- **Clear Cart**
    - **URL:** `/cart/clear`
    - **Method:** `DELETE`
    - **Request Body:**
        ```json
        {
            "userId": 1
        }
        ```
    - **Response:** `200 OK` with confirmation message

- **Checkout**
    - **URL:** `/cart/checkout`
    - **Method:** `POST`
    - **Request Body:**
        ```json
        {
            "userId": 1,
            "addressId": 1
        }
        ```
    - **Response:** `200 OK` with order details and payment link

- **Get Cart**
    - **URL:** `/cart`
    - **Method:** `GET`
    - **Request Param:** `userId`
    - **Response:** `200 OK` with cart details

### Order Management

- **Get Order**
    - **URL:** `/order/{id}`
    - **Method:** `GET`
    - **Response:** `200 OK` with order details

- **Get All Orders**
    - **URL:** `/order`
    - **Method:** `GET`
    - **Response:** `200 OK` with list of orders

- **Cancel Order**
    - **URL:** `/order/cancel/{id}`
    - **Method:** `POST`
    - **Response:** `200 OK` with updated order details

- **Update Order Status**
    - **URL:** `/order/update/{id}`
    - **Method:** `POST`
    - **Request Body:**
        ```json
        {
            "status": "PROCESSING"
        }
        ```
    - **Response:** `200 OK` with updated order details

## Project Structure

- `src/main/java/com/example/ecomappjava/` - Main application code
    - `controllers/` - REST controllers
    - `dtos/` - Data Transfer Objects
    - `exceptions/` - Custom exceptions
    - `models/` - JPA entities
    - `repository/` - Spring Data JPA repositories
    - `services/` - Service layer
    - `utility/` - Utility classes

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any changes.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.