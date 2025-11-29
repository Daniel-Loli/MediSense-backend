package com.medisense.medisense_back.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name="especialista")
public class Especialista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_especialista")
    @EqualsAndHashCode.Include
    private Long idEspecialista;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_usuario",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_ESPECIALISTA_USUARIO")
    )
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_especialidad",
            foreignKey = @ForeignKey(name = "FK_ESPECIALISTA_ESPECIALIDAD"),nullable = false)
    private Especialidad especialidad;

    @Column(name = "dni")
    private String dni;

    @Column(name = "nombres", nullable = false, length = 50)
    private String nombres;

    @Column(name = "celular", length = 9)
    private String celular;


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
