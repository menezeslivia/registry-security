# Etapa 1: Build do JAR (multi-stage build para imagens menores e seguras)
FROM maven:3.9.7-eclipse-temurin-17-alpine AS build

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final apenas com o JAR
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copia o JAR gerado na etapa anterior
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
