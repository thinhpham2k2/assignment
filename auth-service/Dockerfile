# Stage 1: Use Maven for building common library
FROM maven:3.9.9-sapmachine-17 AS common-lib-build
WORKDIR /app
COPY ./common-library/ ./common-library
WORKDIR /app/common-library
RUN mvn clean package

# Stage 2: Use Maven for building authenticate service
FROM maven:3.9.9-sapmachine-17 AS build
LABEL authors="ctv"
WORKDIR /app
COPY ./auth-service/ ./auth
WORKDIR /app/auth
ENV DB_URL=jdbc:mysql://host.docker.internal:3306/auth_db
COPY --from=common-lib-build /app/common-library/target/common-library-0.0.1-SNAPSHOT.jar ./lib/
RUN mvn install:install-file -Dfile=./lib/common-library-0.0.1-SNAPSHOT.jar \
    -DgroupId=com.assignment \
    -DartifactId=common-library \
    -Dversion=0.0.1-SNAPSHOT \
    -Dpackaging=jar
RUN mvn clean package

# Stage 3: Use OpenJDK for running
FROM openjdk:24-slim-bullseye
WORKDIR /app
COPY --from=build /app/auth/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]

# Build command: docker build -t auth_image -f auth-service/Dockerfile .
# Run command: docker run --name auth_container -p 8081:8081 auth_image