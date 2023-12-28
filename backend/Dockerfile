# Use una imagen de Maven para construir la aplicación
FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app

# Copia solo los archivos necesarios para resolver las dependencias
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copia los archivos restantes y construye la aplicación
COPY src src
RUN mvn clean package -DskipTests

# Imagen de producción
FROM openjdk:17-jdk-alpine

WORKDIR /app

# Copia solo los archivos necesarios para ejecutar la aplicación
COPY .env .
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.war .

# Expone el puerto
EXPOSE 5000

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "backend-0.0.1-SNAPSHOT.war"]
