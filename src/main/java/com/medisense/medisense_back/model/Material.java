package com.medisense.medisense_back.model;

import com.medisense.medisense_back.Enum.CarpetaEnum;
import com.medisense.medisense_back.Enum.TipoFile;
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
@Table(name = "material")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_material")
    private Long idMaterial;

    @Column(name = "descripcion", length = 150)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoFile tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "carpeta")
    private CarpetaEnum carpeta;

    @Column(name="url",nullable = false, length = 500)
    private String url;

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
