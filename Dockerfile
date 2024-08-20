FROM --platform=linux/amd64 openjdk:22

EXPOSE 8080

ADD target/todo-app-java-backend-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]