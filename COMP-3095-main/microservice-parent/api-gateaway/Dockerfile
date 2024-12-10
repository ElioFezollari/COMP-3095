#--------------
# Build Stage
#--------------
FROM gradle:8-jdk22-alpine AS builder
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test
FROM openjdk:22-jdk
RUN mkdir /app
COPY --from=builder /home/gradle/src/build/libs/*.jar /app/api-gateway.jar
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "/app/api-gateway.jar"]