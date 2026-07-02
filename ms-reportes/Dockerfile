FROM eclipse-temurin:21-jre-alpine
LABEL maintainer="Hilton"
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 9011
ENTRYPOINT ["java", "-jar", "app.jar"]