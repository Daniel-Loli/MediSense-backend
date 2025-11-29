package com.medisense.medisense_back.service.interfaces;

import com.medisense.medisense_back.dto.base.ListPageResponse;
import com.medisense.medisense_back.dto.base.ListResponse;
import com.medisense.medisense_back.dto.base.ObjectResponse;
import com.medisense.medisense_back.dto.especialista.EspecialistaAllResponse;
import com.medisense.medisense_back.dto.especialista.EspecialistaCreateRequest;
import com.medisense.medisense_back.dto.especialista.EspecialistaShortResponse;
import com.medisense.medisense_back.dto.especialista.EspecialistaUpdateRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IEspecialistaService {
    ListResponse<EspecialistaAllResponse> listar();
    ListPageResponse<EspecialistaShortResponse> listarPage(Pageable pageable);
    ListResponse<EspecialistaShortResponse> listarPorEspecialidad(Long idEspecialidad);
    ListPageResponse<EspecialistaShortResponse> listarPagePorEspecialidad(Pageable pageable, Long idEspecialidad);
    ObjectResponse<EspecialistaAllResponse> buscar(Long idEspecialista);
    ObjectResponse<EspecialistaAllResponse> buscarPorEmail(String email);
    ObjectResponse<EspecialistaAllResponse> buscarPorDocumentoIdentidad(String documentoIdentidad);
    ObjectResponse<EspecialistaAllResponse> buscarPorIdUsuario(Long idUsuario);
    ObjectResponse<EspecialistaAllResponse> registrar(EspecialistaCreateRequest request);
    ListResponse<EspecialistaAllResponse> registrarAll(List<EspecialistaCreateRequest> requests);
    ObjectResponse<EspecialistaAllResponse> actualizar(Long idEspecialista, EspecialistaUpdateRequest request);
    ObjectResponse<String> eliminar(Long idEspecialista);
}
