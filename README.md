# Sonnet

Sonnet Core

## Project Structure

```
Sonnet/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/sonnet/
│   │           └── App.java
│   └── test/
│       └── java/
│           └── com/sonnet/
│               └── AppTest.java
├── pom.xml
└── README.md
```

## Building

To build the project, run:

```bash
mvn clean install
```

## Running Tests

To run the unit tests:

```bash
mvn test
```

## Running the Application

To run the main application:

```bash
mvn exec:java -Dexec.mainClass="com.sonnet.App"
```

## Requirements

- Java 11 or higher
- Maven 3.6 or higher
