# Use a small JDK 17 base image
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Spring Boot jar will be named order-service-0.0.1-SNAPSHOT.jar by default
# We copy whatever jar is in target/ and rename it to app.jar
COPY target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]