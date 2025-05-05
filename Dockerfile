FROM adoptopenjdk:17-jre-hotspot

WORKDIR /app

COPY target/entrevista.0.0.1.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
