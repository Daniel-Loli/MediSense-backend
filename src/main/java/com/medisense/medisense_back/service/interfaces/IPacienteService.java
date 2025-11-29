package com.medisense.medisense_back.service.interfaces;

import com.medisense.medisense_back.dto.base.ListPageResponse;
import com.medisense.medisense_back.dto.base.ListResponse;
import com.medisense.medisense_back.dto.base.ObjectResponse;

import com.medisense.medisense_back.dto.paciente.PacienteAllResponse;
import com.medisense.medisense_back.dto.paciente.PacienteCreateRequest;
import com.medisense.medisense_back.dto.paciente.PacienteShortResponse;
import com.medisense.medisense_back.dto.paciente.PacienteUpdateRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPacienteService {
    ListResponse<PacienteShortResponse> listar();
    ListPageResponse<PacienteShortResponse> listarPage(Pageable pageable);
    ObjectResponse<PacienteAllResponse> buscar(Long idPaciente);
    ObjectResponse<PacienteAllResponse> buscarPorEmail(String email);
    ObjectResponse<PacienteAllResponse> buscarPorDni(String dni);
    ObjectResponse<PacienteAllResponse> registrar(PacienteCreateRequest request);
    ListResponse<PacienteAllResponse> registrarAll(List<PacienteCreateRequest> requests);
    ObjectResponse<PacienteAllResponse> actualizar(Long idPaciente, PacienteUpdateRequest request);
    ObjectResponse<String> eliminar(Long idPaciente);
}
