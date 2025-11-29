package com.medisense.medisense_back.service.impl;

import com.medisense.medisense_back.Enum.EstadoUsuarioEnum;
import com.medisense.medisense_back.Enum.Modulo;
import com.medisense.medisense_back.Enum.RolEnum;
import com.medisense.medisense_back.dto.base.ListPageResponse;
import com.medisense.medisense_back.dto.base.ListResponse;
import com.medisense.medisense_back.dto.base.ObjectResponse;
import com.medisense.medisense_back.dto.especialista.EspecialistaAllResponse;
import com.medisense.medisense_back.dto.especialista.EspecialistaCreateRequest;
import com.medisense.medisense_back.dto.especialista.EspecialistaShortResponse;
import com.medisense.medisense_back.dto.especialista.EspecialistaUpdateRequest;
import com.medisense.medisense_back.mapper.IMapperService;
import com.medisense.medisense_back.model.Especialidad;
import com.medisense.medisense_back.model.Especialista;
import com.medisense.medisense_back.model.Rol;
import com.medisense.medisense_back.model.Usuario;
import com.medisense.medisense_back.repository.base.IGenericRepo;
import com.medisense.medisense_back.repository.interfaces.IEspecialidadRepo;
import com.medisense.medisense_back.repository.interfaces.IEspecialistaRepo;
import com.medisense.medisense_back.repository.interfaces.IRolRepo;
import com.medisense.medisense_back.repository.interfaces.IUsuarioRepo;
import com.medisense.medisense_back.service.base.CRUDImpl;
import com.medisense.medisense_back.service.interfaces.IEspecialistaService;
import com.medisense.medisense_back.utils.GeneratorUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.medisense.medisense_back.Enum.Message.CORREO_EN_USO;

@Slf4j
@Service
@RequiredArgsConstructor
public class EspecialistaServiceImpl 
    extends CRUDImpl<Especialista,Long>
    implements IEspecialistaService {
    
    private final IEspecialidadRepo especialidadRepo;
    private final IEspecialistaRepo especialistaRepo;
    private final IUsuarioRepo usuarioRepo;
    private final IRolRepo rolRepo;
    private final PasswordEncoder passwordEncoder;
    private final IMapperService mapperService;
    @Override
    protected IGenericRepo<Especialista, Long> getRepo() {
        return especialistaRepo;
    }
    @Override
    public ListResponse<EspecialistaAllResponse> listar() {
        List<EspecialistaAllResponse> lista = especialistaRepo.listar()
                .stream()
                .sorted(Comparator.comparing(Especialista::getCreatedAt).reversed())
                .map(mapperService::convEspecialistaAll)
                .collect(Collectors.toList());
        return new ListResponse<>(200, Modulo.ESPECIALISTA.listado(), lista,lista.size());
    }

    // === LISTAR PAGINADO ===
    @Override
    public ListPageResponse<EspecialistaShortResponse> listarPage(Pageable pageable) {
        var page = especialistaRepo.listarPage(pageable)
                .map(mapperService::convEspecialistaShort);
        return new ListPageResponse<>(
                200,
                Modulo.ESPECIALISTA.listadoPage(),
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
    // === LISTAR POR ESPECIALIDAD ===
    @Override
    public ListResponse<EspecialistaShortResponse> listarPorEspecialidad(Long idEspecialidad) {
        var lista = especialistaRepo.listarPorEspecialidad(idEspecialidad)
                .stream()
                .map(mapperService::convEspecialistaShort)
                .collect(Collectors.toList());

        return new ListResponse<>(
                200,
                Modulo.ESPECIALISTA.listado(),
                lista,
                lista.size()
        );
    }

    // === LISTAR PAGINADO POR ESPECIALIDAD ===
    @Override
    public ListPageResponse<EspecialistaShortResponse> listarPagePorEspecialidad(Pageable pageable, Long idEspecialidad) {
        var page = especialistaRepo.listarPagePorEspecialidad(pageable, idEspecialidad)
                .map(mapperService::convEspecialistaShort);

        return new ListPageResponse<>(
                200,
                Modulo.ESPECIALISTA.listadoPage(),
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }


    // === BUSCAR ===
    @Override
    public ObjectResponse<EspecialistaAllResponse> buscar(Long idEspecialista) {
        Optional<Especialista> opt = especialistaRepo.buscarPorId(idEspecialista);
        if (opt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.ESPECIALISTA.noEncontrado(), null);
        }
        return new ObjectResponse<>(200, Modulo.ESPECIALISTA.encontrado(),
                mapperService.convEspecialistaAll(opt.get()));
    }
    // === BUSCAR POR EMAIL ===
    @Override
    public ObjectResponse<EspecialistaAllResponse> buscarPorEmail(String email) {
        Optional<Especialista> opt = especialistaRepo.buscarPorEmail(email);

        if (opt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.ESPECIALISTA.noEncontrado(), null);
        }

        return new ObjectResponse<>(
                200,
                Modulo.ESPECIALISTA.encontrado(),
                mapperService.convEspecialistaAll(opt.get())
        );
    }

    // === BUSCAR POR DNI / DOCUMENTO IDENTIDAD ===
    @Override
    public ObjectResponse<EspecialistaAllResponse> buscarPorDocumentoIdentidad(String documentoIdentidad) {
        Optional<Especialista> opt = especialistaRepo.buscarPorDocIdentidad(documentoIdentidad);

        if (opt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.ESPECIALISTA.noEncontrado(), null);
        }

        return new ObjectResponse<>(
                200,
                Modulo.ESPECIALISTA.encontrado(),
                mapperService.convEspecialistaAll(opt.get())
        );
    }

    // === BUSCAR POR ID USUARIO ===
    @Override
    public ObjectResponse<EspecialistaAllResponse> buscarPorIdUsuario(Long idUsuario) {
        Optional<Especialista> opt = especialistaRepo.buscarPorIdUsuario(idUsuario);

        if (opt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.ESPECIALISTA.noEncontrado(), null);
        }

        return new ObjectResponse<>(
                200,
                Modulo.ESPECIALISTA.encontrado(),
                mapperService.convEspecialistaAll(opt.get())
        );
    }


    // === REGISTRAR ===
    @Override
    public ObjectResponse<EspecialistaAllResponse> registrar(EspecialistaCreateRequest request) {
        if (usuarioRepo.buscarPorEmail(request.getEmail()).isPresent()) {
            return new ObjectResponse<>(409, CORREO_EN_USO.toString(), null);
        }

        List<Especialista> duplicado = especialistaRepo.listarPorDocumento(request.getDni());
        if (!duplicado.isEmpty()) {
            return new ObjectResponse<>(409, "Documento de identidad ya existente", null);
        }

        RolEnum rolEnum = RolEnum.ESPECIALISTA;
        Optional<Rol> rolOpt = rolRepo.buscarPorId(rolEnum.getValor());
        if (rolOpt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.ROL.noEncontrado(), null);
        }
        Rol rolUsuario = rolOpt.get();

        String codigo;
        do {
            codigo = "US" + GeneratorUtil.generarCodigoNumerico(6);
        } while (usuarioRepo.existePorCodigo(codigo));

        Optional<Especialidad> especialidadOpt = especialidadRepo.buscarPorId(request.getIdEspecialidad());
        if (especialidadOpt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.ESPECIALIDAD.noEncontrado(), null);
        }
        Especialidad especialidad = especialidadOpt.get();

        // === Crear usuario ===
        Usuario usuario = Usuario.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .codigo(codigo)
                .rol(rolUsuario)
                .estado(EstadoUsuarioEnum.ACTIVO)
                .build();
        usuarioRepo.save(usuario);

        // === Crear Especialista ===
        Especialista especialista = Especialista.builder()
                .usuario(usuario)
                .nombres(request.getNombres())
                .dni(request.getDni())
                .celular(request.getCelular())
                .especialidad(especialidad)
                .enabled(true)
                .build();

        especialistaRepo.save(especialista);

        return new ObjectResponse<>(201, Modulo.ESPECIALISTA.registrado(),
                mapperService.convEspecialistaAll(especialista));
    }

    @Override
    public ListResponse<EspecialistaAllResponse> registrarAll(List<EspecialistaCreateRequest> requests) {
        List<EspecialistaAllResponse> registrados = new ArrayList<>();
        int errorCount = 0;

        for (EspecialistaCreateRequest request : requests) {
            try {
                ObjectResponse<EspecialistaAllResponse> response = registrar(request);
                if (response.status() == 201 && response.data() != null) {
                    registrados.add(response.data());
                } else {
                    errorCount++;
                }
            } catch (Exception e) {
                log.error("Error registrando Especialista: {}", e.getMessage());
                errorCount++;
            }
        }

        return new ListResponse<>(
                201,
                Modulo.ESPECIALISTA.resumenAllRegistro(registrados.size(), errorCount),
                registrados,
                registrados.size()
        );
    }
    // === REGISTRAR MÚLTIPLES ===

    // === ACTUALIZAR ===
    @Override
    public ObjectResponse<EspecialistaAllResponse> actualizar(Long idEspecialista, EspecialistaUpdateRequest request) {
        Optional<Especialista> opt = especialistaRepo.buscarPorId(idEspecialista);
        if (opt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.ESPECIALISTA.noEncontrado(), null);
        }

        Especialista especialista = opt.get();

        if (request.getDni() != null &&
                !request.getDni().equals(especialista.getDni())) {

            List<Especialista> duplicados = especialistaRepo.listarPorDocumento(request.getDni())
                    .stream()
                    .filter(e -> !e.getIdEspecialista().equals(idEspecialista))
                    .toList();

            if (!duplicados.isEmpty()) {
                return new ObjectResponse<>(409, "Documento de identidad ya existente", null);
            }

            especialista.setDni(request.getDni());
        }

        if (request.getIdEspecialidad() != null) {
            Optional<Especialidad> especialidadOpt = especialidadRepo.buscarPorId(request.getIdEspecialidad());
            if (especialidadOpt.isEmpty()) {
                return new ObjectResponse<>(404, Modulo.ESPECIALIDAD.noEncontrado(), null);
            }
            especialista.setEspecialidad(especialidadOpt.get());
        }

        if (request.getNombres() != null) {
            especialista.setNombres(request.getNombres());
        }

        if (request.getCelular() != null) {
            especialista.setCelular(request.getCelular());
        }

        especialistaRepo.save(especialista);

        return new ObjectResponse<>(
                200,
                Modulo.ESPECIALISTA.actualizado(),
                mapperService.convEspecialistaAll(especialista)
        );
    }


    // === ELIMINAR ===
    @Override
    public ObjectResponse<String> eliminar(Long idEspecialista) {
        Optional<Especialista> opt = especialistaRepo.buscarPorId(idEspecialista);
        if (opt.isEmpty()) {
            return new ObjectResponse<>(404, Modulo.ESPECIALISTA.noEncontrado(), null);
        }

        Especialista especialista = opt.get();
        especialista.setEnabled(false);
        especialistaRepo.save(especialista);

        return new ObjectResponse<>(200, Modulo.ESPECIALISTA.eliminado(), null);
    }
}
