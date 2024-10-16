#----------------
#   Build Stage
#----------------
FROM gradle:8-jdk22-alpine AS builder

COPY --chown=gradle:gradle . /home/gradle/src

WORKDIR /home/gradle/src

RUN gradle build -x test

#------------
#   Packge
#------------

FROM openjdk:22-jdk

RUN mkdir /app

COPY --from=builder /home/gradle/src/build/libs/*.jar /app/product-service.jar

RUN echo "Contents of /app directory:" && ls /app

ENV MONGO_DB_USERNAME=admin \
    MONGO_DB_PWD=password

EXPOSE 8084

ENTRYPOINT ["java","-jar","product-service.jar"]