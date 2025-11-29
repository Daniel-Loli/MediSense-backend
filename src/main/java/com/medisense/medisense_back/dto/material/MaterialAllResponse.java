package com.medisense.medisense_back.dto.material;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.medisense.medisense_back.Enum.CarpetaEnum;
import com.medisense.medisense_back.Enum.TipoFile;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MaterialAllResponse {
    private Long idMaterial;
    private String descripcion;
    private TipoFile tipo;
    private CarpetaEnum carpeta;
    private String url;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private Boolean enabled;
}
