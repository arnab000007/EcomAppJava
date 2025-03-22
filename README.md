# Project Overview

**EcomAppJava** is a Spring Boot-based e-commerce application that manages user authentication, order processing, product inventory, and cart operations, providing a seamless online shopping experience.

## User Registration and Login Summary
**_User Registration:_** To register a new user, send a POST request to /auth/register with the following JSON request body:

```json
{
"email": "user@example.com",
"password": "securePassword",
"name": "John Doe"
}
```

**_User Login:_** To log in an existing user, send a POST request to /auth/login with the following JSON request body:

```json
{
  "email": "user@example.com",
  "password": "securePassword"
}
```
