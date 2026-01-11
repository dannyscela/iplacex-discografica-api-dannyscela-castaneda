# ETAPA 1: Compilación
# Uso JDK 17, que es el que tengo instalado en mi computadora
FROM gradle:8.10-jdk17 AS build
WORKDIR /app

# Copiamos solo los archivos necesarios primero
COPY --chown=gradle:gradle . .

# Ejecutamos la compilación
RUN gradle bootJar --no-daemon

# ETAPA 2: Ejecución (Run)
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copiamos el JAR generado en la etapa anterior
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

# Comando para iniciar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]