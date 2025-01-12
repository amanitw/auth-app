FROM openjdk:18-oracle
WORKDIR /app
COPY target/auth-0.0.1-SNAPSHOT.jar /app/application.jar
EXPOSE 8080
CMD ["java", "-jar", "application.jar"]