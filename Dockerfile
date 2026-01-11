# aqui uso la imagen de Gradle para generar el archivo .jar
FROM gradle:8.5-jdk17 AS build
WORKDIR /app
COPY . .
# Generamos el archivo ejecutable
RUN gradle clean bootJar --no-daemon

# ejecuto con openjdk
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
# y luego se copia el .jar generado en la etapa anterior
COPY --from=build /app/build/libs/discografia-1.jar app.jar

# este es el puerto de salida
EXPOSE 8080
# Comando para iniciar la API
ENTRYPOINT ["java", "-jar", "app.jar"]