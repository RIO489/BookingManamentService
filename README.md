# Bookstore Inventory Management System

## Description

This Bookstore Inventory Management System is a Spring Boot application that manages a bookstore's inventory. It provides functionality to add, update, delete, and retrieve book details via gRPC services. The application uses PostgreSQL for persistence and MapStruct for mapping entities to and from DTOs.

## Installation

**Prerequisites:**
- Java JDK 11+
- PostgreSQL
- Gradle

**Steps:**
1. Clone the repository to your local machine.
git clone https://github.com/RIO489/BookingManamentService.git

2. Navigate to the application directory.
cd BookstoreInventoryManagmentSystem

3. Configure PostgreSQL database settings in `application.properties`.

4. Build the application using Gradle.
gradle build

5. Run the application.
To run server side
java -jar /server/build/libs/server.jar
To run client side
java - jar /client/build/libs/client.jar

## Usage

Once the application is running, it exposes gRPC services to manage the book inventory.

To interact with the application, use a gRPC client with the provided `.proto` files to generate the necessary stubs.

## Running the tests

To run integration tests with testcontainers:
gradle integrationTest

To run unit tests:
gradle test


## Deployment

The application can be deployed using Docker or on cloud platforms like AWS or Heroku. Make sure to configure the database connection and any other environment variables accordingly.

## Contributing

If you wish to contribute to this project, please fork the repository and submit a pull request.

## Versioning

The project uses version numbers to track changes and improvements. As new features are added and issues are fixed, new versions will be released. Please refer to the repository's [releases section](https://github.com/RIO489/BookingManamentService/releases) for a history of changes.

## Authors

- **Oleksandr Hamaiunov** - *Initial work* - [RIO489](https://github.com/RIO489)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## Acknowledgments

- Hat tip to anyone whose code was used
- Youtube(gRPC Client & Server Microservices Implementation Using Spring Boot) by Dev Problems - https://www.youtube.com/watch?v=zCXN4wj0uPo&list=PL9sWWgDDTZOZR2UiZFglyoSpOKrrUSGyr
- Youtube(Error Handling in gRPC Spring Boot Microservice) by Dev Problems - https://www.youtube.com/watch?v=Ewtgmvd5xFU
- ChatGPT4
- Stack Overflow
