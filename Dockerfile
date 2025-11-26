# ========== ETAPA 1: BUILD ==========
FROM gradle:8.5-jdk17-alpine AS build

COPY --chown=gradle:gradle . .

# --- NUEVA LÍNEA: Damos permisos de ejecución al wrapper de Gradle ---
RUN chmod +x ./gradlew

# Compilamos
RUN ./gradlew bootJar -x test --no-daemon

# ========== ETAPA 2: RUNTIME ==========
FROM eclipse-temurin:17-jre-alpine

EXPOSE 8080

COPY --from=build /home/gradle/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]