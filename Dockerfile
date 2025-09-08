# Use a lightweight OpenJDK runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container.
WORKDIR /app

# Set the Spring profile to be active in the container
ENV SPRING_PROFILES_ACTIVE=gcp

# Copy the executable JAR file from the Maven build into the container.
# This must be the correct name from your Maven build output.
COPY target/AnomalyDetectionService-*.jar /app/app.jar


# Define the port on which the application will run
EXPOSE 8080

# Specify the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
