package org.example.service;

import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;
    private static final Pattern VALID_DNA_PATTERN = Pattern.compile("^[ATCG]+$");

    public boolean isMutant(String[] dna) {
        if (dna == null || dna.length == 0) return false;

        int n = dna.length;
        // Validación: Matriz cuadrada y caracteres válidos
        for (String row : dna) {
            if (row == null || row.length() != n || !VALID_DNA_PATTERN.matcher(row).matches()) {
                return false; // O lanzar excepción según preferencia
            }
        }

        //  Convertir a char[][] para acceso rápido O(1)
        char[][] matrix = new char[n][n];
        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }

        int sequenceCount = 0;

        //  Single Pass (Un solo recorrido)
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {

                // Buscar Horizontal
                if (col <= n - SEQUENCE_LENGTH) {
                    if (checkDirection(matrix, row, col, 0, 1)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true; // Optimización 1: Early Termination
                    }
                }

                // Buscar Vertical
                if (row <= n - SEQUENCE_LENGTH) {
                    if (checkDirection(matrix, row, col, 1, 0)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }

                // Buscar Diagonal Principal
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (checkDirection(matrix, row, col, 1, 1)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }

                // Buscar Diagonal Inversa
                if (row <= n - SEQUENCE_LENGTH && col >= SEQUENCE_LENGTH - 1) {
                    if (checkDirection(matrix, row, col, 1, -1)) {
                        sequenceCount++;
                        if (sequenceCount > 1) return true;
                    }
                }
            }
        }
        return false;
    }

    // Optimización 4: Comparación directa sin loops internos
    private boolean checkDirection(char[][] matrix, int row, int col, int dRow, int dCol) {
        char base = matrix[row][col];
        return base == matrix[row + dRow][col + dCol] &&
                base == matrix[row + 2 * dRow][col + 2 * dCol] &&
                base == matrix[row + 3 * dRow][col + 3 * dCol];
    }
}