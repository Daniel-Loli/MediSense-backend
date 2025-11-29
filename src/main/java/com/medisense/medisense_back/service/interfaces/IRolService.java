package com.medisense.medisense_back.service.interfaces;

import com.medisense.medisense_back.dto.base.ListResponse;
import com.medisense.medisense_back.dto.base.ObjectResponse;
import com.medisense.medisense_back.dto.rol.RolAllResponse;

public interface IRolService {
    ListResponse<RolAllResponse> listar();
    ObjectResponse<RolAllResponse> buscar(Long idRol);
}
