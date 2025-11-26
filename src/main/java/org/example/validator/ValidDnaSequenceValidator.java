package org.example.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

    private static final Pattern VALID_PATTERN = Pattern.compile("^[ATCG]+$");

    @Override
    public void initialize(ValidDnaSequence constraintAnnotation) {
    }

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        if (dna == null || dna.length == 0) return false; // Validación de Null/Empty

        int n = dna.length;
        for (String row : dna) {
            // Validar que no sea nulo, tamaño NxN y caracteres válidos
            if (row == null || row.length() != n || !VALID_PATTERN.matcher(row).matches()) {
                return false;
            }
        }
        return true;
    }
}