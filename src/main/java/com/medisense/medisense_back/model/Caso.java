package com.medisense.medisense_back.model;

import com.medisense.medisense_back.Enum.EstadoCasoEnum;
import com.medisense.medisense_back.Enum.NivelRiesgoEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name="caso")
public class Caso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caso")
    @EqualsAndHashCode.Include
    private Long idCaso;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @ElementCollection
    @CollectionTable(name = "caso_sintoma", joinColumns = @JoinColumn(name = "id_caso"))
    @Column(name = "sintoma")
    private List<String> sintomas;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoCasoEnum estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "riesgo", nullable = false)
    private NivelRiesgoEnum riesgo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_especialidad", nullable = false)
    private Especialidad especialidad;

    @Column(name = "recomendacion_ia", length = 500)
    private String recomendacionIa;

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
