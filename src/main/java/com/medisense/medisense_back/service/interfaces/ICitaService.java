package com.medisense.medisense_back.service.interfaces;

import com.medisense.medisense_back.dto.base.ListPageResponse;
import com.medisense.medisense_back.dto.base.ListResponse;
import com.medisense.medisense_back.dto.base.ObjectResponse;
import com.medisense.medisense_back.dto.cita.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICitaService {
    ListResponse<CitaAllResponse> listar();
    ListPageResponse<CitaAllResponse> listarPage(Pageable pageable);
    ListResponse<CitaEShortResponse> listarPorEspecialista(Long idEspecialista);
    ListPageResponse<CitaEShortResponse> listarPagePorEspecialista(Pageable pageable, Long idEspecialista);
    ListResponse<CitaPShortResponse> listarPorPaciente(Long idPaciente);
    ListPageResponse<CitaPShortResponse> listarPagePorPaciente(Pageable pageable, Long idPaciente);
    ListResponse<CitaCShortResponse> listarPorCaso(Long idCaso);
    ListPageResponse<CitaCShortResponse> listarPagePorCaso(Pageable pageable, Long idCaso);
    ObjectResponse<CitaAllResponse> buscar(Long idCita);
    ObjectResponse<CitaAllResponse> registrar(CitaCreateRequest request);
    ListResponse<CitaAllResponse> registrarAll(List<CitaCreateRequest> requests);
    ObjectResponse<CitaAllResponse> actualizar(Long idCita, CitaUpdateRequest request);
    ObjectResponse<String> eliminar(Long idCita);
}
