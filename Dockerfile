FROM openjdk:11-jdk
WORKDIR /app
ARG JAR_FILE
COPY ${JAR_FILE} /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]