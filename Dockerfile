# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine


# Set the working directory to /app
WORKDIR /app

EXPOSE 7021

# Copy the executable jar file and the application.properties file to the container
COPY target/msinnovacion-build-report-0.0.1.jar /app/

# Set the command to run the Spring Boot application
CMD ["java", "-jar", "msinnovacion-build-report-0.0.1.jar"]


