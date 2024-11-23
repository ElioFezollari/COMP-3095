#--------------
# Build Stage
#--------------
FROM gradle:8-jdk22-alpine AS builder
WORKDIR /home/gradle/src
COPY . .
RUN gradle build -x test
FROM openjdk:22-jdk
RUN mkdir /app
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/inventory-service.jar
ENV POSTGRES_USER=admin \
    POSTGRES_PWD=password
EXPOSE 9093
ENTRYPOINT ["java", "-jar", "/app/inventory-service.jar"]