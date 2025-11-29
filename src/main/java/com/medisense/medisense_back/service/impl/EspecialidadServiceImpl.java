package com.medisense.medisense_back.service.impl;

import com.medisense.medisense_back.Enum.Modulo;
import com.medisense.medisense_back.dto.base.ListResponse;
import com.medisense.medisense_back.dto.base.ObjectResponse;
import com.medisense.medisense_back.dto.especialidad.EspecialidadAllResponse;
import com.medisense.medisense_back.dto.especialidad.EspecialidadCreateRequest;
import com.medisense.medisense_back.dto.especialidad.EspecialidadShortResponse;
import com.medisense.medisense_back.dto.especialidad.EspecialidadUpdateRequest;
import com.medisense.medisense_back.dto.paciente.PacienteAllResponse;
import com.medisense.medisense_back.dto.paciente.PacienteCreateRequest;
import com.medisense.medisense_back.mapper.IMapperService;
import com.medisense.medisense_back.model.Especialidad;
import com.medisense.medisense_back.repository.base.IGenericRepo;
import com.medisense.medisense_back.repository.interfaces.IEspecialidadRepo;
import com.medisense.medisense_back.service.base.CRUDImpl;
import com.medisense.medisense_back.service.interfaces.IEspecialidadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EspecialidadServiceImpl
    extends CRUDImpl<Especialidad, Long>
    implements IEspecialidadService {
    
    private final IEspecialidadRepo especialidadRepo;
    private final IMapperService mapperService;
    
    @Override
    protected IGenericRepo<Especialidad, Long> getRepo() {
        return especialidadRepo;
    }

    @Override
    public ListResponse<EspecialidadShortResponse> listar(){
        List<EspecialidadShortResponse> lista = especialidadRepo.listar()
                .stream()
                .sorted(Comparator.comparing(Especialidad::getCreatedAt).reversed())
                .map(mapperService::convEspecialidadShort)
                .toList();
        return new ListResponse<>(200, Modulo.ESPECIALIDAD.listado(),lista,null);
    }

    @Override
    public ObjectResponse<EspecialidadAllResponse> buscar(Long idEspecialidad) {
        Optional<Especialidad> EspecialidadOpt = especialidadRepo.buscarPorId(idEspecialidad);
        if (EspecialidadOpt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.ESPECIALIDAD.noEncontrado(), null);
        }

        return new ObjectResponse<>(200, Modulo.ESPECIALIDAD.encontrado(),
                mapperService.convEspecialidadAll(EspecialidadOpt.get()));
    }

    @Override
    public ObjectResponse<EspecialidadAllResponse> registrar(EspecialidadCreateRequest request) {
        Especialidad nuevo = Especialidad.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .build();
        especialidadRepo.save(nuevo);

        return new ObjectResponse<>(
                201,
                Modulo.ESPECIALIDAD.registrado(),
                mapperService.convEspecialidadAll(nuevo)
        );
    }

    @Override
    public ObjectResponse<EspecialidadAllResponse> actualizar(Long idEspecialidad, EspecialidadUpdateRequest request) {
        Optional<Especialidad> EspecialidadOpt = especialidadRepo.buscarPorId(idEspecialidad);
        if (EspecialidadOpt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.ESPECIALIDAD.noEncontrado(), null);
        }
        Especialidad obj = EspecialidadOpt.get();
        obj.setNombre(request.getNombre());
        obj.setDescripcion(request.getDescripcion());
        especialidadRepo.save(obj);

        return new ObjectResponse<>(
                201,
                Modulo.ESPECIALIDAD.actualizado(),
                mapperService.convEspecialidadAll(obj)
        );
    }

    @Override
    public ListResponse<EspecialidadAllResponse> registrarAll(List<EspecialidadCreateRequest> requests) {
        List<EspecialidadAllResponse> registrados = new ArrayList<>();
        int errorCount = 0;

        for (EspecialidadCreateRequest request : requests) {
            try {
                ObjectResponse<EspecialidadAllResponse> response = registrar(request);
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
                Modulo.ESPECIALIDAD.resumenAllRegistro(registrados.size(), errorCount),
                registrados,
                registrados.size()
        );
    }

    @Override
    public ObjectResponse<String> eliminar(Long idEspecialidad) {
        Optional<Especialidad> EspecialidadOpt = especialidadRepo.buscarPorId(idEspecialidad);
        if (EspecialidadOpt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.ESPECIALIDAD.noEncontrado(), null);
        }
        Especialidad obj = EspecialidadOpt.get();
        obj.setEnabled(false);
        especialidadRepo.save(obj);
        return new ObjectResponse<>(200, Modulo.ESPECIALIDAD.eliminado(), null);

    }
}
