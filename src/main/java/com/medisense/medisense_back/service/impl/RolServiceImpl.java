package com.medisense.medisense_back.service.impl;

import com.medisense.medisense_back.Enum.Modulo;
import com.medisense.medisense_back.dto.base.ListResponse;
import com.medisense.medisense_back.dto.base.ObjectResponse;
import com.medisense.medisense_back.dto.rol.RolAllResponse;
import com.medisense.medisense_back.mapper.IMapperService;
import com.medisense.medisense_back.model.Rol;
import com.medisense.medisense_back.repository.base.IGenericRepo;
import com.medisense.medisense_back.repository.interfaces.IRolRepo;
import com.medisense.medisense_back.service.base.CRUDImpl;
import com.medisense.medisense_back.service.interfaces.IRolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RolServiceImpl
        extends CRUDImpl<Rol, Long>
        implements IRolService {

    private final IRolRepo rolRepo;
    private final IMapperService mapperService;
    @Override
    protected IGenericRepo<Rol, Long> getRepo() {
        return null;
    }

    @Override
    public ListResponse<RolAllResponse> listar() {
        List<RolAllResponse> lista = rolRepo.listar()
                .stream()
                .map(mapperService::convRolAll)
                .toList();
        return new ListResponse<>(200, Modulo.ROL.listado(), lista,lista.size());
    }
    @Override
    public ObjectResponse<RolAllResponse> buscar(Long idRol) {
        return rolRepo.buscarPorId(idRol)
                .map(rol -> new ObjectResponse<>(200, Modulo.ROL.encontrado(), mapperService.convRolAll(rol)))
                .orElseGet(() -> new ObjectResponse<>(404, Modulo.ROL.noEncontrado(), null));
    }
}
