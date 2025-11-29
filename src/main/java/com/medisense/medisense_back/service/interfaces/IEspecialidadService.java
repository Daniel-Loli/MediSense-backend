package com.medisense.medisense_back.service.interfaces;

import com.medisense.medisense_back.dto.base.ListResponse;
import com.medisense.medisense_back.dto.base.ObjectResponse;
import com.medisense.medisense_back.dto.especialidad.EspecialidadAllResponse;
import com.medisense.medisense_back.dto.especialidad.EspecialidadCreateRequest;
import com.medisense.medisense_back.dto.especialidad.EspecialidadShortResponse;
import com.medisense.medisense_back.dto.especialidad.EspecialidadUpdateRequest;
import com.medisense.medisense_back.dto.especialista.EspecialistaAllResponse;
import com.medisense.medisense_back.dto.especialista.EspecialistaCreateRequest;

import java.util.List;

public interface IEspecialidadService {
    ListResponse<EspecialidadShortResponse> listar();
    ObjectResponse<EspecialidadAllResponse> buscar(Long idEspecialidad);
    ObjectResponse<EspecialidadAllResponse> registrar(EspecialidadCreateRequest request);
    ObjectResponse<EspecialidadAllResponse> actualizar(Long idEspecialidad, EspecialidadUpdateRequest request);
    ListResponse<EspecialidadAllResponse> registrarAll(List<EspecialidadCreateRequest> requests);
    ObjectResponse<String> eliminar(Long idEspecialidad);

}
