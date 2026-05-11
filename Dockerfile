# Build stage
FROM maven:3.9.9-eclipse-temurin-26 AS build
WORKDIR /app
COPY . /app
RUN mvn -B package -DskipTests

# Runtime stage
FROM openjdk:26-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]