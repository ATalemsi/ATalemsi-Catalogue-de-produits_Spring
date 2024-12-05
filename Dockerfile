# Use a base image with OpenJDK
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the jar file (build the jar with 'mvn clean package' or 'gradle build' first)
COPY target/product_managementV2-0.0.1-SNAPSHOT.jar product-management.jar

# Expose the port your Spring Boot app will run on
EXPOSE 9090

# Run the app when the container starts
ENTRYPOINT ["java", "-jar", "product-management.jar"]
