FROM gradle:8-jdk22-alpine AS builder
WORKDIR /home/gradle/src
COPY . .
RUN gradle build -x test
FROM openjdk:22-jdk
RUN mkdir /app
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/notification-service.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "/app/notification-service.jar"]