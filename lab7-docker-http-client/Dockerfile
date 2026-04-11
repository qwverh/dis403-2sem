FROM eclipse-temurin:19

WORKDIR /app

RUN mkdir -p "/app/logs"

COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]