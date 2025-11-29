package com.medisense.medisense_back.service.interfaces;

import com.medisense.medisense_back.dto.base.ListPageResponse;
import com.medisense.medisense_back.dto.base.ListResponse;
import com.medisense.medisense_back.dto.base.ObjectResponse;
import com.medisense.medisense_back.dto.caso.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICasoService {
    ListResponse<CasoAllResponse> listar();
    ListPageResponse<CasoAllResponse> listarPage(Pageable pageable);
    ListResponse<CasoEShortResponse> listarPorEspecialidad(Long idEspecialidad);
    ListPageResponse<CasoEShortResponse> listarPagePorEspecialidad(Pageable pageable, Long idEspecialidad);
    ListResponse<CasoPShortResponse> listarPorPaciente(Long idPaciente);
    ListPageResponse<CasoPShortResponse> listarPagePorPaciente(Pageable pageable, Long idPaciente);
    ObjectResponse<CasoAllResponse> buscar(Long idCaso);
    ObjectResponse<CasoAllResponse> registrar(CasoCreateRequest request);
    ListResponse<CasoAllResponse> registrarAll(List<CasoCreateRequest> requests);
    ObjectResponse<CasoAllResponse> actualizar(Long idCaso, CasoUpdateRequest request);
    ObjectResponse<String> eliminar(Long idCaso);
}
