# Stage 1: Use Maven for building
FROM maven:3.9.9-sapmachine-17 AS build
LABEL authors="ctv"
WORKDIR /app
COPY . ./auth
WORKDIR /app/auth
RUN mvn clean package

# Stage 2: Use OpenJDK for running
FROM openjdk:24-slim-bullseye
WORKDIR /app
COPY --from=build /app/auth/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]

# Build command: docker build -t auth_image .
# Run command: docker run --name auth_container -p 8080:8080 auth