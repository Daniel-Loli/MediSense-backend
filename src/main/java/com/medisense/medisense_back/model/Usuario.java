package com.medisense.medisense_back.model;

import com.medisense.medisense_back.Enum.EstadoUsuarioEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Table(name="usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    @EqualsAndHashCode.Include
    private Long idUsuario;

    @Column(nullable = false, name = "email")
    @Email
    private String email;

    @Column(nullable = false,name="password")
    private String password;

    @Column(nullable = false, name = "codigo",length = 8)
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "id_rol",nullable = false,
            foreignKey = @ForeignKey(name = "FK_USUARIO_ROL"))
    private Rol rol;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "estado")
    private EstadoUsuarioEnum estado;

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
