# Spring Boot RabbitMQ Microservices Demo

A simple 2-service architecture using Spring Boot 3.5, RabbitMQ, and PostgreSQL with Podman Compose.

`order-service` saves orders to DB and publishes an event.
`mail-service` listens to the event and "sends" an email.

### **Architecture**

- **order-service**: Spring Boot REST API. Publishes events to RabbitMQ when an order is created
- **mail-service**: Spring Boot Consumer. Listens to RabbitMQ and processes email notifications
- **rabbitmq**: Message broker with Management UI


### **Tech Stack**
- Java 21 + Spring Boot 3.5.16
- Spring Data JPA + PostgreSQL 16
- Spring AMQP + RabbitMQ 3.13 Management
- Lombok
- Podman + podman-compose

### **Prerequisites**
1. Podman 4.9+
2. podman-compose 1.0.6+
3. Maven 3.9+

### **How to Run**
1. Build both services
```bash
cd order-service && mvn clean package -DskipTests
cd../mail-service && mvn clean package -DskipTests
cd..
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
Postgres: localhost:5432

## **3.** ## **Test the Flow**

Create an order:
```bash
curl -X POST http://localhost:8081/orders \
-H "Content-Type: application/json" \
-d '{"email": "test@example.com"}'
```
Expected response: Order 1 saved and sent to queue

Check the DB:
```bash
podman exec -it order-db psql -U postgres -d ordersdb -c "select * from orders;"
```
Check mail-service received it:
```bash
podman logs spring-boot-rabbitmq-microservices-demo_mail-service_1 --tail 10
```
You should see:
EMAIL FIELD: test@example.com
Subject: Order 1 Confirmed

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