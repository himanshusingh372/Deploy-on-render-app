# Use an OpenJDK base image with version 16 (or the version matching your Java version)
FROM adoptopenjdk:16-jdk-hotspot

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target1/blog-app1.jar /app/blog-app1.jar

# Expose the port your application listens on
EXPOSE 8080

# Set the command to run your application when the container starts
CMD ["java", "-jar", "blog-app1.jar"]
