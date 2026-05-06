# Use a lightweight JDK image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy built jar into container
COPY target/userApi-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Run the jar
ENTRYPOINT ["java","-jar","app.jar"]
