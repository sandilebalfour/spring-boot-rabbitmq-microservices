# Spring Boot RabbitMQ Microservices Demo

A demo of event-driven microservices using Spring Boot 3.5, RabbitMQ, and Podman.

**Flow:** `OrderService` publishes an `OrderCreated` event -> `RabbitMQ` -> `MailService` consumes and "sends" email

## Architecture

- **order-service**: Spring Boot REST API. Publishes events to RabbitMQ when an order is created
- **mail-service**: Spring Boot Consumer. Listens to RabbitMQ and processes email notifications
- **rabbitmq**: Message broker with Management UI

## Tech Stack

- **Java**: 21
- **Spring Boot**: 3.5.16
- **Spring AMQP**: For RabbitMQ integration
- **RabbitMQ**: 3.13 with Management Plugin
- **Podman + podman-compose**: For container orchestration
- **Maven**: Build tool

## Prerequisites

1. Java 21 JDK
2. Maven 3.9+
3. Podman 4+
4. podman-compose

## **Check versions:**
```bash
java -version
podman --version 
```
Requirements: Java 21, Maven 3.9+, Podman 4+

# **Quick Start**

## **1.** ## **Build** 

```bash 
mvn clean package -DskipTests
```
2. ## **Run**

```bash
podman-compose up --build
```
Services:rabbitmq: localhost:5672 | Management: localhost:15672
order-service: localhost:8081
mail-service: localhost:8082
Login for RabbitMQ: guest / guest

## **3.** ## **Test the Flow**

```bash
curl -X POST http://localhost:8081/orders \
   -H "Content-Type: application/json" \
   -d '{
   "email": "wtc@test.com",
   "product": "PopOS Laptop",
   "quantity": 1
   }'
```
   Expected Response:json{"status":"Order created","orderId":"550e8400-e29b-41d4-a716-446655440000"}Expected MailService Log:javascript=========================================
   SENDING EMAIL TO: wtc@test.com
   Subject: Order 550e8400-e29b-41d4-a716-446655440000 Confirmed
   Body: Thanks for order #550e8400-e29b-41d4-a716-446655440000 - PopOS Laptop x1
   =========================================

## **4. Stop**

```bash
podman-compose down
```
# To also remove volumes
podman-compose down -v

Project Structure
spring-boot-rabbitmq-microservices-demo/
├── order-service/
│   ├── src/main/java/com/services/order_service/
│   │   ├── controller/OrderController.java
│   │   ├── event/OrderEvent.java
│   │   └── config/RabbitConfig.java
│   ├── src/main/resources/application.yml
│   ├── Dockerfile
│   └── pom.xml
├── mail-service/
│   ├── src/main/java/com/services/mail_service/
│   │   ├── listener/OrderListener.java
│   │   ├── event/OrderEvent.java
│   │   └── config/RabbitConfig.java
│   ├── src/main/resources/application.yml
│   ├── Dockerfile
│   └── pom.xml
├── docker-compose.yml
├── pom.xml
└── README.mdEnvironment Variables

## **_Both services use:_**

yaml
spring:
    rabbitmq:
        host: rabbitmq
        port: 5672
        username: guest
        password: guest


## **# **Dockerfile****

dockerfileFROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

Built with ❤️ for learning Spring Boot + RabbitMQ