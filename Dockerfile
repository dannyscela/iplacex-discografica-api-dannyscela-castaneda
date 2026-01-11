# ETAPA 1: Compilación
FROM gradle:8.10-jdk17 AS build
WORKDIR /app
COPY . .

RUN gradle bootJar -x test --no-daemon

# ETAPA 2: Ejecución
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]