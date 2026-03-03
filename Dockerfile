# Stage 1: build
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copy Gradle wrapper and build files first to cache dependencies
COPY gradle gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .
RUN chmod +x ./gradlew

# Copy source code
COPY src src

# Build the jar
RUN ./gradlew clean bootJar -x test

# Stage 2: runtime
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the built jar to the runtime image
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]