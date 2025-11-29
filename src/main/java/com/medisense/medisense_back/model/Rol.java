package com.medisense.medisense_back.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name="rol")
public class Rol {
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "id_rol")
    private Long idRol;

    @Column(nullable = false,name = "nombre")
    private String nombre;


}
