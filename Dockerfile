FROM openjdk:17-jdk

COPY target/reddit-0.0.1-SNAPSHOT.jar /app/java.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/java.jar"]