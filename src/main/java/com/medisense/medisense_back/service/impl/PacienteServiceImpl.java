package com.medisense.medisense_back.service.impl;

import com.medisense.medisense_back.Enum.Modulo;
import com.medisense.medisense_back.dto.base.ListPageResponse;
import com.medisense.medisense_back.dto.base.ListResponse;
import com.medisense.medisense_back.dto.base.ObjectResponse;
import com.medisense.medisense_back.dto.paciente.PacienteAllResponse;
import com.medisense.medisense_back.dto.paciente.PacienteCreateRequest;
import com.medisense.medisense_back.dto.paciente.PacienteShortResponse;
import com.medisense.medisense_back.dto.paciente.PacienteUpdateRequest;
import com.medisense.medisense_back.mapper.IMapperService;
import com.medisense.medisense_back.model.Paciente;
import com.medisense.medisense_back.repository.base.IGenericRepo;
import com.medisense.medisense_back.repository.interfaces.IPacienteRepo;
import com.medisense.medisense_back.service.base.CRUDImpl;
import com.medisense.medisense_back.service.interfaces.IPacienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteServiceImpl
        extends CRUDImpl<Paciente,Long>
        implements IPacienteService {
    private final IPacienteRepo pacienteRepo;
    private final IMapperService mapperService;

    @Override
    protected IGenericRepo<Paciente, Long> getRepo() {
        return pacienteRepo;
    }

    @Override
    public ListResponse<PacienteShortResponse> listar() {
        List<PacienteShortResponse> lista = pacienteRepo.listar()
                .stream()
                .sorted(Comparator.comparing(Paciente::getCreatedAt).reversed())
                .map(mapperService::convPacienteShort)
                .collect(Collectors.toList());
        return new ListResponse<>(200, Modulo.PACIENTE.listado(), lista,lista.size());
    }
    @Override
    public ListPageResponse<PacienteShortResponse> listarPage(Pageable pageable) {
        var page = pacienteRepo.listarPage(pageable)
                .map(mapperService::convPacienteShort);
        return new ListPageResponse<>(
                200,
                Modulo.PACIENTE.listadoPage(),
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    @Override
    public ObjectResponse<PacienteAllResponse> buscar(Long idPaciente) {
        Optional<Paciente> opt = pacienteRepo.buscarPorId(idPaciente);
        if (opt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.PACIENTE.noEncontrado(), null);
        }
        return new ObjectResponse<>(200, Modulo.PACIENTE.encontrado(),
                mapperService.convPacienteAll(opt.get()));
    }

    @Override
    public ObjectResponse<PacienteAllResponse> buscarPorEmail(String email) {
        Optional<Paciente> opt = pacienteRepo.buscarPorEmail(email);

        if (opt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.PACIENTE.noEncontrado(), null);
        }

        return new ObjectResponse<>(
                200,
                Modulo.PACIENTE.encontrado(),
                mapperService.convPacienteAll(opt.get())
        );
    }

    @Override
    public ObjectResponse<PacienteAllResponse> buscarPorDni(String dni) {
        Optional<Paciente> opt = pacienteRepo.buscarPorDni(dni);

        if (opt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.PACIENTE.noEncontrado(), null);
        }

        return new ObjectResponse<>(
                200,
                Modulo.PACIENTE.encontrado(),
                mapperService.convPacienteAll(opt.get())
        );
    }


    @Override
    public ObjectResponse<PacienteAllResponse> registrar(PacienteCreateRequest request) {

        // Validar email duplicado
        if (pacienteRepo.buscarPorEmail(request.getEmail()).isPresent()) {
            return new ObjectResponse<>(409, "Correo electrónico ya registrado", null);
        }

        // Validar DNI duplicado
        if (pacienteRepo.buscarPorDni(request.getDni()).isPresent()) {
            return new ObjectResponse<>(409, "Documento de identidad ya registrado", null);
        }

        // Crear Paciente
        Paciente paciente = Paciente.builder()
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .email(request.getEmail())
                .celular(request.getCelular())
                .dni(request.getDni())
                .peso(request.getPeso())
                .talla(request.getTalla())
                .build();

        pacienteRepo.save(paciente);

        return new ObjectResponse<>(
                201,
                Modulo.PACIENTE.registrado(),
                mapperService.convPacienteAll(paciente)
        );
    }


    @Override
    public ListResponse<PacienteAllResponse> registrarAll(List<PacienteCreateRequest> requests) {
        List<PacienteAllResponse> registrados = new ArrayList<>();
        int errorCount = 0;

        for (PacienteCreateRequest request : requests) {
            try {
                ObjectResponse<PacienteAllResponse> response = registrar(request);
                if (response.status() == 201 && response.data() != null) {
                    registrados.add(response.data());
                } else {
                    errorCount++;
                }
            } catch (Exception e) {
                log.error("Error registrando: {}", e.getMessage());
                errorCount++;
            }
        }

        return new ListResponse<>(
                201,
                Modulo.PACIENTE.resumenAllRegistro(registrados.size(), errorCount),
                registrados,
                registrados.size()
        );
    }

    @Override
    public ObjectResponse<PacienteAllResponse> actualizar(Long idPaciente, PacienteUpdateRequest request) {

        Optional<Paciente> opt = pacienteRepo.buscarPorId(idPaciente);
        if (opt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.PACIENTE.noEncontrado(), null);
        }

        Paciente paciente = opt.get();

        // Validar email duplicado si viene en request
        if (request.getEmail() != null &&
                !request.getEmail().equalsIgnoreCase(paciente.getEmail()) &&
                pacienteRepo.buscarPorEmail(request.getEmail()).isPresent()) {
            return new ObjectResponse<>(409, "Correo electrónico ya registrado", null);
        }

        // Validar DNI duplicado si viene en request
        if (request.getDni() != null &&
                !request.getDni().equals(paciente.getDni()) &&
                pacienteRepo.buscarPorDni(request.getDni()).isPresent()) {
            return new ObjectResponse<>(409, "Documento de identidad ya registrado", null);
        }

        // Actualizar campos opcionales
        if (request.getNombres() != null && !request.getNombres().isBlank()) {
            paciente.setNombres(request.getNombres());
        }
        if (request.getApellidos() != null && !request.getApellidos().isBlank()) {
            paciente.setApellidos(request.getApellidos());
        }
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            paciente.setEmail(request.getEmail());
        }
        if (request.getCelular() != null && !request.getCelular().isBlank()) {
            paciente.setCelular(request.getCelular());
        }
        if (request.getDni() != null && !request.getDni().isBlank()) {
            paciente.setDni(request.getDni());
        }
        if (request.getPeso() != null && request.getPeso() > 0) {
            paciente.setPeso(request.getPeso());
        }
        if (request.getTalla() != null && request.getTalla() > 0) {
            paciente.setTalla(request.getTalla());
        }

        pacienteRepo.save(paciente);

        return new ObjectResponse<>(
                200,
                Modulo.PACIENTE.actualizado(),
                mapperService.convPacienteAll(paciente)
        );
    }


    @Override
    public ObjectResponse<String> eliminar(Long idPaciente) {
        Optional<Paciente> opt = pacienteRepo.buscarPorId(idPaciente);
        if (opt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.PACIENTE.noEncontrado(), null);
        }

        Paciente obj = opt.get();
        obj.setEnabled(false);
        pacienteRepo.save(obj);

        return new ObjectResponse<>(200, Modulo.PACIENTE.eliminado(), null);
    }
}
