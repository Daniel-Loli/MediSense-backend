package com.medisense.medisense_back.service.interfaces;

import com.medisense.medisense_back.dto.base.ListResponse;
import com.medisense.medisense_back.dto.base.ObjectResponse;
import com.medisense.medisense_back.dto.material.MaterialAllResponse;
import com.medisense.medisense_back.dto.material.MaterialShortResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IMaterialService {
    ObjectResponse<MaterialAllResponse> registrarMaterial(MultipartFile file, String descripcion);
    ListResponse<MaterialShortResponse> listarMaaterial();
    ObjectResponse<String> eliminarMaterial(Long idMaterial);
    ObjectResponse<MaterialAllResponse> buscarMaterial(Long idMaterial);
}
