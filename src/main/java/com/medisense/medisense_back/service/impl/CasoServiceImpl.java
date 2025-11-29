package com.medisense.medisense_back.service.impl;

import com.medisense.medisense_back.Enum.Modulo;
import com.medisense.medisense_back.dto.base.ListPageResponse;
import com.medisense.medisense_back.dto.base.ListResponse;
import com.medisense.medisense_back.dto.base.ObjectResponse;
import com.medisense.medisense_back.dto.caso.*;
import com.medisense.medisense_back.mapper.IMapperService;
import com.medisense.medisense_back.model.Caso;
import com.medisense.medisense_back.model.Especialidad;
import com.medisense.medisense_back.model.Paciente;
import com.medisense.medisense_back.repository.base.IGenericRepo;
import com.medisense.medisense_back.repository.interfaces.ICasoRepo;
import com.medisense.medisense_back.repository.interfaces.IEspecialidadRepo;
import com.medisense.medisense_back.repository.interfaces.IPacienteRepo;
import com.medisense.medisense_back.service.base.CRUDImpl;
import com.medisense.medisense_back.service.interfaces.ICasoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CasoServiceImpl
        extends CRUDImpl<Caso, Long>
        implements ICasoService {

    private final ICasoRepo casoRepo;
    private final IPacienteRepo pacienteRepo;
    private final IEspecialidadRepo especialidadRepo;
    private final IMapperService mapperService;
    @Override
    protected IGenericRepo<Caso, Long> getRepo() {
        return casoRepo;
    }

    @Override
    public ListResponse<CasoAllResponse> listar() {
        List<CasoAllResponse> lista = casoRepo.listar()
                .stream()
                .sorted(Comparator.comparing(Caso::getCreatedAt).reversed())
                .map(mapperService::convCasoAll)
                .toList();
        return new ListResponse<>(200, Modulo.CASO.listado(),lista,null);
    }

    @Override
    public ListPageResponse<CasoAllResponse> listarPage(Pageable pageable) {
        var page = casoRepo.listarPage(pageable)
                .map(mapperService::convCasoAll);

        return new ListPageResponse<>(
                200,
                Modulo.CASO.listadoPage(),
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    @Override
    public ListResponse<CasoEShortResponse> listarPorEspecialidad(Long idEspecialidad) {
        List<CasoEShortResponse> lista = casoRepo.listarPorEspecialidad(idEspecialidad)
                .stream()
                .sorted(Comparator.comparing(Caso::getCreatedAt).reversed())
                .map(mapperService::convCasoEShort)
                .toList();
        return new ListResponse<>(200, Modulo.CASO.listado(),lista,null);
    }

    @Override
    public ListPageResponse<CasoEShortResponse> listarPagePorEspecialidad(Pageable pageable, Long idEspecialidad) {
        var page = casoRepo.listarPagePorEspecialidad(pageable,idEspecialidad)
                .map(mapperService::convCasoEShort);

        return new ListPageResponse<>(
                200,
                Modulo.CASO.listadoPage(),
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    @Override
    public ListResponse<CasoPShortResponse> listarPorPaciente(Long idPaciente) {
        List<CasoPShortResponse> lista = casoRepo.listarPorPaciente(idPaciente)
                .stream()
                .sorted(Comparator.comparing(Caso::getCreatedAt).reversed())
                .map(mapperService::convCasoPShort)
                .toList();
        return new ListResponse<>(200, Modulo.CASO.listado(),lista,null);
    }

    @Override
    public ListPageResponse<CasoPShortResponse> listarPagePorPaciente(Pageable pageable, Long idPaciente) {
        var page = casoRepo.listarPagePorEspecialidad(pageable,idPaciente)
                .map(mapperService::convCasoPShort);

        return new ListPageResponse<>(
                200,
                Modulo.CASO.listadoPage(),
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }


    @Override
    public ObjectResponse<CasoAllResponse> buscar(Long idCaso) {
        Optional<Caso> opt = casoRepo.buscarPorId(idCaso);
        if (opt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.CASO.noEncontrado(), null);
        }
        return new ObjectResponse<>(200, Modulo.CASO.encontrado(),
                mapperService.convCasoAll(opt.get()));
    }

    @Override
    @Transactional
    public ObjectResponse<CasoAllResponse> registrar(CasoCreateRequest request) {

        // 🔹 Validar paciente
        Optional<Paciente> pacienteOpt = pacienteRepo.buscarPorId(request.getIdPaciente());
        if (pacienteOpt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.PACIENTE.noEncontrado(), null);
        }

        // 🔹 Validar especialidad
        Optional<Especialidad> especialidadOpt = especialidadRepo.buscarPorId(request.getIdEspecialidad());
        if (especialidadOpt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.ESPECIALIDAD.noEncontrado(), null);
        }

        Paciente paciente = pacienteOpt.get();
        Especialidad especialidad = especialidadOpt.get();

        // 🔹 Crear la entidad Caso
        Caso caso = Caso.builder()
                .paciente(paciente)
                .sintomas(request.getSintomas())
                .estado(request.getEstado())
                .riesgo(request.getRiesgo())
                .especialidad(especialidad)
                .recomendacionIa(request.getRecomendacionIa())
                .build();

        Caso guardado = casoRepo.save(caso);

        return new ObjectResponse<>(201, Modulo.CASO.registrado(), mapperService.convCasoAll(guardado));
    }


    @Override
    public ListResponse<CasoAllResponse> registrarAll(List<CasoCreateRequest> requests) {
        List<CasoAllResponse> registrados = new ArrayList<>();
        int errorCount = 0;

        for (CasoCreateRequest request : requests) {
            try {
                ObjectResponse<CasoAllResponse> response = registrar(request);
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
                Modulo.CASO.resumenAllRegistro(registrados.size(), errorCount),
                registrados,
                registrados.size()
        );
    }

    @Override
    @Transactional
    public ObjectResponse<CasoAllResponse> actualizar(Long idCaso, CasoUpdateRequest request) {

        Optional<Caso> casoOpt = casoRepo.buscarPorId(idCaso);
        if (casoOpt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.CASO.noEncontrado(), null);
        }

        Caso caso = casoOpt.get();

        if (request.getSintomas() != null && !request.getSintomas().isEmpty()) {
            caso.setSintomas(request.getSintomas());
        }

        if (request.getEstado() != null) {
            caso.setEstado(request.getEstado());
        }

        if (request.getRiesgo() != null) {
            caso.setRiesgo(request.getRiesgo());
        }

        if (request.getIdEspecialidad() != null) {
            Optional<Especialidad> espOpt = especialidadRepo.buscarPorId(request.getIdEspecialidad());
            if (espOpt.isEmpty()) {
                return new ObjectResponse<>(404, Modulo.ESPECIALIDAD.noEncontrado(), null);
            }
            caso.setEspecialidad(espOpt.get());
        }

        if (request.getRecomendacionIa() != null) {
            caso.setRecomendacionIa(request.getRecomendacionIa());
        }

        Caso actualizado = casoRepo.save(caso);

        return new ObjectResponse<>(200, Modulo.CASO.actualizado(), mapperService.convCasoAll(actualizado));
    }


    @Override
    public ObjectResponse<String> eliminar(Long idCaso) {
        Optional<Caso> opt = casoRepo.buscarPorId(idCaso);
        if (opt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.CASO.noEncontrado(), null);
        }

        Caso obj = opt.get();
        obj.setEnabled(false);
        casoRepo.save(obj);

        return new ObjectResponse<>(200, Modulo.CASO.eliminado(), null);
    }
}
