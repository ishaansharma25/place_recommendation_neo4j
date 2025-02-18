# Use the official OpenJDK 11 image from Docker Hub
FROM openjdk:11-jre-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from your local machine to the container
COPY target/neo4j-java-crud-0.0.1-SNAPSHOT.jar /app/neo4j-java-crud.jar

# Expose the port your application will run on (default Spring Boot port is 8080)
EXPOSE 8080

# Run the JAR file when the container starts
ENTRYPOINT ["java", "-jar", "/app/neo4j-java-crud.jar"]

