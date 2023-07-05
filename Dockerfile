FROM openjdk:11-jdk
WORKDIR /app
COPY ./target/kebos-indicacao-backend-1.0.0.jar /app
EXPOSE 8080
CMD ["java", "-jar", "kebos-indicacao-backend-1.0.0.jar"]