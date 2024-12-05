# Stage 1: Use Maven for building
FROM maven:3.9.9-sapmachine-17 AS build
LABEL authors="ctv"
WORKDIR /app
COPY . ./core
WORKDIR /app/core
ENV DB_URL=jdbc:mysql://host.docker.internal:3306/core_db
RUN mvn clean package

# Stage 2: Use OpenJDK for running
FROM openjdk:24-slim-bullseye
WORKDIR /app
COPY --from=build /app/core/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

# Build command: docker build -t core_image .
# Run command: docker run --name core_container -p 8080:8080 core