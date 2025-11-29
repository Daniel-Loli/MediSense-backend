package com.medisense.medisense_back.service.impl;

import com.medisense.medisense_back.Enum.Modulo;
import com.medisense.medisense_back.dto.base.ListPageResponse;
import com.medisense.medisense_back.dto.base.ListResponse;
import com.medisense.medisense_back.dto.base.ObjectResponse;
import com.medisense.medisense_back.dto.cita.*;
import com.medisense.medisense_back.mapper.IMapperService;
import com.medisense.medisense_back.model.*;
import com.medisense.medisense_back.repository.base.IGenericRepo;
import com.medisense.medisense_back.repository.interfaces.*;
import com.medisense.medisense_back.service.base.CRUDImpl;
import com.medisense.medisense_back.service.interfaces.ICitaService;
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
public class CitaServiceImpl
        extends CRUDImpl<Cita, Long>
        implements ICitaService {

    private final ICitaRepo CitaRepo;
    private final IPacienteRepo pacienteRepo;
    private final ICasoRepo casoRepo;
    private final IEspecialistaRepo especialistaRepo;
    private final IMapperService mapperService;
    @Override
    protected IGenericRepo<Cita, Long> getRepo() {
        return CitaRepo;
    }

    @Override
    public ListResponse<CitaAllResponse> listar() {
        List<CitaAllResponse> lista = CitaRepo.listar()
                .stream()
                .sorted(Comparator.comparing(Cita::getCreatedAt).reversed())
                .map(mapperService::convCitaAll)
                .toList();
        return new ListResponse<>(200, Modulo.CITA.listado(),lista,null);
    }

    @Override
    public ListPageResponse<CitaAllResponse> listarPage(Pageable pageable) {
        var page = CitaRepo.listarPage(pageable)
                .map(mapperService::convCitaAll);

        return new ListPageResponse<>(
                200,
                Modulo.CITA.listadoPage(),
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    @Override
    public ListResponse<CitaEShortResponse> listarPorEspecialista(Long idEspecialista) {
        List<CitaEShortResponse> lista = CitaRepo.listarPorEspecialista(idEspecialista)
                .stream()
                .sorted(Comparator.comparing(Cita::getCreatedAt).reversed())
                .map(mapperService::convCitaEShort)
                .toList();
        return new ListResponse<>(200, Modulo.CITA.listado(),lista,null);
    }

    @Override
    public ListPageResponse<CitaEShortResponse> listarPagePorEspecialista(Pageable pageable, Long idEspecialista) {
        var page = CitaRepo.listarPagePorEspecialista(pageable,idEspecialista)
                .map(mapperService::convCitaEShort);

        return new ListPageResponse<>(
                200,
                Modulo.CITA.listadoPage(),
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }

    @Override
    public ListResponse<CitaPShortResponse> listarPorPaciente(Long idPaciente) {
        List<CitaPShortResponse> lista = CitaRepo.listarPorPaciente(idPaciente)
                .stream()
                .sorted(Comparator.comparing(Cita::getCreatedAt).reversed())
                .map(mapperService::convCitaPShort)
                .toList();
        return new ListResponse<>(200, Modulo.CITA.listado(),lista,null);
    }

    @Override
    public ListPageResponse<CitaPShortResponse> listarPagePorPaciente(Pageable pageable, Long idPaciente) {
        var page = CitaRepo.listarPagePorPaciente(pageable,idPaciente)
                .map(mapperService::convCitaPShort);

        return new ListPageResponse<>(
                200,
                Modulo.CITA.listadoPage(),
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
    @Override
    public ListResponse<CitaCShortResponse> listarPorCaso(Long idCaso) {
        List<CitaCShortResponse> lista = CitaRepo.listarPorCaso(idCaso)
                .stream()
                .sorted(Comparator.comparing(Cita::getCreatedAt).reversed())
                .map(mapperService::convCitaCShort)
                .toList();
        return new ListResponse<>(200, Modulo.CITA.listado(),lista,null);
    }

    @Override
    public ListPageResponse<CitaCShortResponse> listarPagePorCaso(Pageable pageable, Long idCaso) {
        var page = CitaRepo.listarPagePorCaso(pageable,idCaso)
                .map(mapperService::convCitaCShort);

        return new ListPageResponse<>(
                200,
                Modulo.CITA.listadoPage(),
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }



    @Override
    public ObjectResponse<CitaAllResponse> buscar(Long idCita) {
        Optional<Cita> opt = CitaRepo.buscarPorId(idCita);
        if (opt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.CITA.noEncontrado(), null);
        }
        return new ObjectResponse<>(200, Modulo.CITA.encontrado(),
                mapperService.convCitaAll(opt.get()));
    }

    @Override
    @Transactional
    public ObjectResponse<CitaAllResponse> registrar(CitaCreateRequest request) {

        Optional<Paciente> pacienteOpt = pacienteRepo.buscarPorId(request.getIdPaciente());
        if (pacienteOpt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.PACIENTE.noEncontrado(), null);
        }

        Optional<Especialista> especialistadOpt = especialistaRepo.buscarPorId(request.getIdEspecialista());
        if (especialistadOpt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.ESPECIALISTA.noEncontrado(), null);
        }

        Optional<Caso> casoOpt = casoRepo.buscarPorId(request.getIdCaso());
        if (casoOpt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.CASO.noEncontrado(), null);
        }

        Paciente paciente = pacienteOpt.get();
        Especialista especialista = especialistadOpt.get();
        Caso caso = casoOpt.get();

        Cita cita = Cita.builder()
                .paciente(paciente)
                .especialista(especialista)
                .caso(caso)
                .fechaHora(request.getFechaHora())
                .estado(request.getEstado())
                .notas(request.getNotas())
                .build();

        Cita guardado = CitaRepo.save(cita);

        return new ObjectResponse<>(201, Modulo.CITA.registrado(), mapperService.convCitaAll(guardado));
    }


    @Override
    public ListResponse<CitaAllResponse> registrarAll(List<CitaCreateRequest> requests) {
        List<CitaAllResponse> registrados = new ArrayList<>();
        int errorCount = 0;

        for (CitaCreateRequest request : requests) {
            try {
                ObjectResponse<CitaAllResponse> response = registrar(request);
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
                Modulo.CITA.resumenAllRegistro(registrados.size(), errorCount),
                registrados,
                registrados.size()
        );
    }


    @Override
    @Transactional
    public ObjectResponse<CitaAllResponse> actualizar(Long idCita, CitaUpdateRequest request) {

        Optional<Cita> citaOpt = CitaRepo.buscarPorId(idCita);
        if (citaOpt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.CITA.noEncontrado(), null);
        }

        Cita cita = citaOpt.get();

        // Actualizar especialista si se envía uno nuevo
        if (request.getIdEspecialista() != null) {
            Optional<Especialista> espOpt = especialistaRepo.buscarPorId(request.getIdEspecialista());
            if (espOpt.isEmpty()) {
                return new ObjectResponse<>(404, Modulo.ESPECIALISTA.noEncontrado(), null);
            }
            cita.setEspecialista(espOpt.get());
        }

        // Actualizar fechaHora si viene en request
        if (request.getFechaHora() != null) {
            cita.setFechaHora(request.getFechaHora());
        }

        // Actualizar estado si se envía
        if (request.getEstado() != null) {
            cita.setEstado(request.getEstado());
        }

        // Actualizar notas si viene
        if (request.getNotas() != null && !request.getNotas().isBlank()) {
            cita.setNotas(request.getNotas());
        }

        Cita actualizado = CitaRepo.save(cita);

        return new ObjectResponse<>(200, Modulo.CITA.actualizado(),
                mapperService.convCitaAll(actualizado));
    }



    @Override
    public ObjectResponse<String> eliminar(Long idCita) {
        Optional<Cita> opt = CitaRepo.buscarPorId(idCita);
        if (opt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.CITA.noEncontrado(), null);
        }

        Cita obj = opt.get();
        obj.setEnabled(false);
        CitaRepo.save(obj);

        return new ObjectResponse<>(200, Modulo.CITA.eliminado(), null);
    }
}
