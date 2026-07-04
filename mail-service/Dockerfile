FROM docker.io/eclipse-temurin:21-jre-alpine

WORKDIR /app
COPY target/*.jar app.jar

# mail-service has no exposed port, it only consumes from RabbitMQ
ENTRYPOINT ["java","-jar","/app/app.jar"]