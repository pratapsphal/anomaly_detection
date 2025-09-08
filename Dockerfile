# Use a lightweight OpenJDK runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container.
WORKDIR /app

# Set the Spring profile to be active in the container
ENV SPRING_PROFILES_ACTIVE=gcp

# JAR file Detail 
- name: List JAR files
  run: ls -lh target
# Copy the executable JAR file from the Maven build into the container
# This assumes your JAR file name is similar to 'dataIngestionService-*.jar'
COPY target/anomaly_detection-*.jar /app/app.jar


# Define the port on which the application will run
# Make sure this matches the port your Spring Boot app listens on (e.g., in application.properties)
EXPOSE 8080

# Specify the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
