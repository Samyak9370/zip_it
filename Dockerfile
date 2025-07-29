# Stage 1: Use a Maven image to build the application JAR
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file to download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of your source code
COPY src ./src

# Build the application, skipping tests for a faster deployment
RUN mvn clean package -Dmaven.test.skip=true


# Stage 2: Create the final, smaller image to run the application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the 'build' stage
COPY --from=build /app/target/zip-it-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app runs on (usually 8080)
EXPOSE 8080

# The command to run your application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]