FROM amazoncorretto:17.0.14-alpine3.21
LABEL authors="kittu"

WORKDIR /app

COPY target/CorpSuite-Log-Svc-*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]