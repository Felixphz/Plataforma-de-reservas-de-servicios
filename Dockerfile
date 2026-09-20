# Etapa 1: Construcción
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /workspace/app

# Copiar archivos de Gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src src

# Compilar el proyecto omitiendo los tests para mayor rapidez
RUN ./gradlew build -x test

# Etapa 2: Ejecución
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiar el JAR compilado desde la etapa anterior
COPY --from=build /workspace/app/build/libs/*.jar app.jar

# Exponer el puerto
EXPOSE 8080

# Ejecutar
ENTRYPOINT ["java", "-jar", "app.jar"]
