# 🧬 Mutant DNA Analyzer
> **MercadoLibre Technical Challenge - Backend**

Este repositorio contiene una API REST de alto rendimiento diseñada para detectar secuencias genéticas mutantes. El sistema ha sido construido bajo una arquitectura de microservicios utilizando **Java 17** y **Spring Boot**, cumpliendo con los estándares de calidad, eficiencia algorítmica y cobertura de pruebas requeridos para el Nivel 3 del desafío.

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Coverage](https://img.shields.io/badge/Coverage-90%25-success?style=for-the-badge)

---

## 🌐 Despliegue en la Nube

La aplicación está operativa y alojada en la plataforma **Render**.

🔗 **URL Principal:** [https://mutantes-martin-berni.onrender.com](https://mutantes-martin-berni.onrender.com)

* 📖 **Documentación (Swagger UI):** [https://mutantes-martin-berni.onrender.com/swagger-ui.html](https://mutantes-martin-berni.onrender.com/swagger-ui.html)
* 💾 **Consola H2:** [https://mutantes-martin-berni.onrender.com/h2-console](https://mutantes-martin-berni.onrender.com/h2-console)

---

## 👨‍💻 Información del Estudiante

| Campo | Dato                    |
| :--- |:------------------------|
| **Nombre** | Martin                  |
| **Legajo** | Berni                   |
| **Comisión** | 3K09                    |
| **Email** | martinberni14@gmail.com |

---

## ⚡ Highlights Técnicos

El proyecto se destaca por la optimización de recursos y una arquitectura escalable:

### 1. Motor de Detección Optimizado ⚙️
El algoritmo `isMutant` fue diseñado para operar con complejidad **O(N)** en el mejor de los casos:
* [cite_start]**Early Termination:** El análisis se detiene inmediatamente al confirmar la condición de mutante (más de 1 secuencia), evitando iteraciones redundantes[cite: 90, 98].
* **Manejo de Memoria:** Conversión temprana de `String[]` a `char[][]` para agilizar el acceso a memoria y reducir el overhead de Java Strings.
* [cite_start]**Validación Estricta:** Implementación de validadores personalizados (`ConstraintValidator`) para asegurar la integridad de la matriz NxN y los caracteres permitidos (A, T, C, G)[cite: 81].

### 2. Arquitectura Robusta 🏗️
[cite_start]Diseño en **6 capas** para asegurar la separación de responsabilidades y mantenibilidad[cite: 112]:
* `Controller`: Punto de entrada REST.
* `Service`: Lógica de negocio y orquestación.
* `Repository`: Capa de persistencia JPA.
* `Entity` & `DTO`: Modelado de datos y transferencia.
* `Config` & `Validator`: Configuraciones transversales.

### 3. Persistencia Inteligente 🧠
* [cite_start]Base de datos **H2 en memoria** para alta velocidad[cite: 61].
* Estrategia de **Hashing (SHA-256)** para indexar secuencias de ADN. [cite_start]Esto previene el re-procesamiento de ADNs ya analizados, funcionando como una caché de base de datos y garantizando la unicidad de registros[cite: 106].

---

## 📦 Instrucciones de Ejecución

### 🔹 Opción A: Ejecución Local (Gradle)

1.  **Clonar el proyecto:**
    ```bash
    git clone [https://github.com/martinberni14/Mutantes_Martin-Berni.git](https://github.com/martinberni14/Mutantes_Martin-Berni.git)
    cd Mutantes_Martin-Berni
    ```

2.  **Levantar la aplicación:**
    ```bash
    # En Mac/Linux
    ./gradlew bootRun

    # En Windows
    .\gradlew bootRun
    ```

### 🔹 Opción B: Contenedores (Docker)

Si prefieres un entorno aislado, puedes utilizar la imagen Docker optimizada (Eclipse Temurin Alpine).

1.  **Construir:**
    ```bash
    docker build -t mutant-api .
    ```

2.  **Ejecutar:**
    ```bash
    docker run -p 8080:8080 mutant-api
    ```

---

## 📖 Guía de Uso de la API

[cite_start]La API expone documentación bajo el estándar **OpenAPI 3.0** (Swagger)[cite: 22].

### 🔍 Detección de Mutantes
**POST** `/mutant`

Envía una matriz de ADN para su análisis.

* **Cuerpo de la Petición:**
    ```json
    {
      "dna": [
        "ATGCGA",
        "CAGTGC",
        "TTATGT",
        "AGAAGG",
        "CCCCTA",
        "TCACTG"
      ]
    }
    ```

* **Códigos de Respuesta:**
    * [cite_start]`200 OK`: 👽 ADN Mutante detectado[cite: 26].
    * [cite_start]`403 Forbidden`: 🧍 ADN Humano detectado[cite: 26].
    * `400 Bad Request`: 🚫 Entrada inválida (caracteres erróneos o matriz no cuadrada).

### 📊 Reporte de Estadísticas
**GET** `/stats`

[cite_start]Obtiene el conteo y la proporción de mutantes vs. humanos[cite: 30].

* **Respuesta JSON:**
    ```json
    {
        "count_mutant_dna": 40,
        "count_human_dna": 100,
        "ratio": 0.4
    }
    ```

---

## ✅ Testing y Calidad

El proyecto garantiza la fiabilidad mediante una suite completa de pruebas:

* [cite_start]**Unitarias:** Validación lógica de `MutantDetector` cubriendo casos horizontales, verticales, diagonales y casos borde[cite: 128].
* [cite_start]**Integración:** Pruebas de `MutantController` usando `MockMvc` para validar los códigos de estado HTTP[cite: 129].
* [cite_start]**Cobertura:** Supera el **80%** de líneas de código cubiertas[cite: 31].

Para ejecutar los tests y generar el reporte de Jacoco:
```bash
./gradlew test jacocoTestReport