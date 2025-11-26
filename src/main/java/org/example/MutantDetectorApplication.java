package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MutantDetectorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MutantDetectorApplication.class, args);
        System.out.println("Mutant Detector iniciado correctamente!");
        System.out.println("Swagger UI disponible en: http://localhost:8080/swagger-ui.html");
    }

}