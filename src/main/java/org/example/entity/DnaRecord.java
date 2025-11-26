package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dna_records")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DnaRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Index único para evitar duplicados y búsqueda rápida
    @Column(unique = true, nullable = false)
    private String dnaHash;

    @Column(nullable = false)
    private boolean isMutant;

    private LocalDateTime createdAt;
}