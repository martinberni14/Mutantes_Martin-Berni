# ========== ETAPA 1: BUILD ==========
# Usamos una imagen de Gradle con JDK 17 preinstalado para compilar
FROM gradle:8.5-jdk17-alpine AS build

# Copiamos el código
COPY --chown=gradle:gradle . .

# Compilamos (saltando tests para que el deploy sea más rápido y seguro en Render)
RUN ./gradlew bootJar -x test --no-daemon

# ========== ETAPA 2: RUNTIME ==========
# Cambiamos 'openjdk:17-alpine' por 'eclipse-temurin' que es la actual oficial
FROM eclipse-temurin:17-jre-alpine

# Exponemos el puerto
EXPOSE 8080

# Copiamos el JAR generado en la etapa anterior
COPY --from=build /home/gradle/build/libs/*.jar app.jar

# Ejecutamos
ENTRYPOINT ["java", "-jar", "app.jar"]