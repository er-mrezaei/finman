# Use the official OpenJDK image with Java 21
FROM eclipse-temurin:21-jdk

# Set the working directory
WORKDIR /app

# Copy the Maven build files (pom.xml and source code)
COPY ./target/finman-app.jar /app/finman-app.jar

# Expose the port the application will run on (8080 in this case)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/finman-app.jar"]
