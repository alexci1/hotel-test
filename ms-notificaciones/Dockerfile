FROM eclipse-temurin:21-jre-alpine
LABEL maintainer="Hilton"
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 9009
ENTRYPOINT ["java", "-jar", "app.jar"]