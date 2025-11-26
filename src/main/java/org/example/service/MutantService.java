package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MutantService {

    private final DnaRecordRepository repository;
    private final MutantDetector detector;

    public boolean analyze(String[] dna) {
        // Calcula Hash
        String hash = calculateHash(dna);

        // Verificar existencia en BD
        Optional<DnaRecord> existing = repository.findByDnaHash(hash);
        if (existing.isPresent()) {
            return existing.get().isMutant();
        }

        // Detectar si es nuevo
        boolean isMutant = detector.isMutant(dna);

        // guardar resultado
        DnaRecord record = DnaRecord.builder()
                .dnaHash(hash)
                .isMutant(isMutant)
                .createdAt(LocalDateTime.now())
                .build();
        repository.save(record);

        return isMutant;
    }

    private String calculateHash(String[] dna) {
        try {
            String raw = String.join("", dna);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(raw.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error calculando hash", e);
        }
    }
}