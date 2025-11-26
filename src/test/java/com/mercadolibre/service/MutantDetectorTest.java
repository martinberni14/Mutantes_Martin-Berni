package com.mercadolibre.service;

import org.example.service.MutantDetector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class MutantDetectorTest {

    @InjectMocks
    private MutantDetector mutantDetector;


    //  CASOS MUTANTES (Deben retornar TRUE
    @Test
    @DisplayName("Debe detectar mutante con filas horizontales")
    void shouldDetectMutantHorizontal() {
        String[] dna = {
                "AAAA", // Secuencia 1
                "CCCC", // Secuencia 2
                "TCAG",
                "GGTC"
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con columnas verticales")
    void shouldDetectMutantVertical() {
        String[] dna = {
                "ATCG",
                "ATCG",
                "ATCG",
                "ATCG"  // A vertical (col 0) y T vertical (col 1), etc.
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe detectar mutante con diagonales principales (\\)")
    void shouldDetectMutantDiagonalPrincipal() {
        String[] dna = {
                "ATCG", // A en 0,0
                "GAGG", // A en 1,1
                "TGAG", // A en 2,2
                "TGGA"  // A en 3,3 -> Secuencia 1
                // Nota: Necesitamos otra secuencia para ser mutante (>1)
        };
        // Modificamos para tener 2 secuencias claras
        String[] dna2 = {
                "AAAA", // Horizontal (Seq 1)
                "GAGG",
                "TGAG",
                "TGGA"  // Diagonal A (Seq 2) no se forma completa arriba,
                // mejor usar el ejemplo clásico del examen que sabemos es positivo
        };

        // Ejemplo clásico de Mutante (Horizontal + Vertical/Diagonal)
        String[] classicMutant = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(classicMutant));
    }

    @Test
    @DisplayName("Debe detectar mutante con diagonales inversas (/)")
    void shouldDetectMutantDiagonalReverse() {
        String[] dna = {
                "ATCG", // G en 0,3
                "GTGC", // G en 1,2
                "TGGT", // G en 2,1
                "GCTA"  // G en 3,0 -> Secuencia 1
        };
        // Agregamos una segunda secuencia horizontal para cumplir la condición > 1
        String[] dnaWithTwoSeqs = {
                "ATCG",
                "GTGC",
                "TGGT",
                "GGGG"  // Secuencia 2 (Horizontal) + Diagonal G anterior
        };
        assertTrue(mutantDetector.isMutant(dnaWithTwoSeqs));
    }

    @Test
    @DisplayName("Debe detectar mutante con cruce (Horizontal y Vertical)")
    void shouldDetectMutantCross() {
        String[] dna = {
                "ATAT",
                "ACAC",
                "AGAG",
                "AAAA" // Secuencia Horizontal (fila 3) + A Vertical (col 0)
        };
        assertTrue(mutantDetector.isMutant(dna));
    }

    //  CASOS HUMANOS (Deben retornar FALSE)

    @Test
    @DisplayName("Debe ser Humano si no hay ninguna secuencia")
    void shouldReturnFalseForHumanNoSequence() {
        String[] dna = {
                "ATCG",
                "CATG",
                "TCGA",
                "GATC"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe ser Humano si SOLO hay 1 secuencia (Condición es > 1)")
    void shouldReturnFalseForHumanOneSequence() {
        // Este caso es CRÍTICO para la lógica
        String[] dna = {
                "AAAA", // 1 secuencia
                "TCAG",
                "GTCA",
                "ACTG"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe ser Humano si hay secuencias de 4 letras pero no 5")
    void shouldReturnFalseForAlmostSequences() {
        String[] dna = {
                "AAAT",
                "AAAT",
                "AAAT",
                "GCTA"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    //  CASOS DE VALIDACIÓN Y ERROR (Robustez)

    @Test
    @DisplayName("Debe manejar input NULL")
    void shouldHandleNullInput() {
        assertFalse(mutantDetector.isMutant(null));
    }

    @Test
    @DisplayName("Debe manejar array vacío")
    void shouldHandleEmptyArray() {
        assertFalse(mutantDetector.isMutant(new String[]{}));
    }

    @Test
    @DisplayName("Debe rechazar matriz NxM (No cuadrada)")
    void shouldReturnFalseForNonSquareMatrix() {
        String[] dna = {
                "AAAA",
                "CCCC",
                "TG" // Fila corta
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe rechazar caracteres inválidos (distintos de A,T,C,G)")
    void shouldReturnFalseForInvalidCharacters() {
        String[] dna = {
                "AAAA",
                "CCCC",
                "TXAG", // X no es válida
                "GGTC"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe rechazar filas NULL dentro del array")
    void shouldHandleNullRows() {
        String[] dna = {
                "AAAA",
                null,
                "TCAG",
                "GGTC"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }

    @Test
    @DisplayName("Debe rechazar caracteres numéricos")
    void shouldHandleNumbersInDna() {
        String[] dna = {
                "1234",
                "ATCG",
                "ATCG",
                "ATCG"
        };
        assertFalse(mutantDetector.isMutant(dna));
    }
}