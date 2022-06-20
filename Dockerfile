FROM docker.io/maven:3.8.6-eclipse-temurin-17 as builder
WORKDIR /app
COPY . /app
RUN mvn clean package

FROM docker.io/amazoncorretto:17-alpine3.13 as runner
WORKDIR /app
COPY --from=builder /app/target/*.jar ./app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-XX:+UseZGC", "/app/app.jar"]