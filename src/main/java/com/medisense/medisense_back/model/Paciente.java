package com.medisense.medisense_back.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "paciente")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_paciente")
    @EqualsAndHashCode.Include
    private Long idPaciente;

    @Column(name = "nombres", nullable = false)
    private String nombres;
    private String apellidos;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "celular", nullable = false, length = 9)
    private String celular;

    @Column(name = "dni", nullable = false, length = 8)
    private String dni;

    @Column(name = "peso")
    private Double peso;

    @Column(name = "talla")
    private Double talla;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    @Builder.Default
    @Column(nullable = false, name = "enabled")
    private Boolean enabled = true;
}
