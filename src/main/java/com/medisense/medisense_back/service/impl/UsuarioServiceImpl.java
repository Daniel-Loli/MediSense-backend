package com.medisense.medisense_back.service.impl;


import com.medisense.medisense_back.Enum.Modulo;
import com.medisense.medisense_back.dto.base.ListPageResponse;
import com.medisense.medisense_back.dto.base.ListResponse;
import com.medisense.medisense_back.dto.base.ObjectResponse;
import com.medisense.medisense_back.dto.usuario.UsuarioAllResponse;
import com.medisense.medisense_back.mapper.IMapperService;
import com.medisense.medisense_back.model.Usuario;
import com.medisense.medisense_back.repository.base.IGenericRepo;
import com.medisense.medisense_back.repository.interfaces.IUsuarioRepo;
import com.medisense.medisense_back.service.base.CRUDImpl;
import com.medisense.medisense_back.service.interfaces.IUsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl
        extends CRUDImpl<Usuario, Long>
        implements IUsuarioService {

    private final IMapperService mapperService;
    private final IUsuarioRepo usuarioRepo;

    @Override
    protected IGenericRepo<Usuario, Long> getRepo() {
        return usuarioRepo;
    }

    // === CONTAR ===
    @Override
    public ObjectResponse<Integer> contar() {
        Integer total = usuarioRepo.contar();
        return new ObjectResponse<>(200, Modulo.USUARIO.contar(), total);
    }

    // === LISTAR ===
    @Override
    public ListResponse<UsuarioAllResponse> listar() {
        List<UsuarioAllResponse> lista = usuarioRepo.listar()
                .stream()
                .sorted(Comparator.comparing(Usuario::getCreatedAt).reversed())
                .map(mapperService::convUsuarioAll)
                .toList();
        return new ListResponse<>(200, Modulo.USUARIO.listado(), lista,lista.size());
    }

    // === LISTAR PAGINADO ===
    @Override
    public ListPageResponse<UsuarioAllResponse> listarPage(Pageable pageable) {
        var page = usuarioRepo.listarPage(pageable)
                .map(mapperService::convUsuarioAll);
        return new ListPageResponse<>(
                200,
                Modulo.USUARIO.listadoPage(),
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()

        );
    }

    // === BUSCAR POR ID ===
    @Override
    public ObjectResponse<UsuarioAllResponse> buscar(Long idUsuario) {
        return usuarioRepo.buscarPorId(idUsuario)
                .map(usuario -> new ObjectResponse<>(200, Modulo.USUARIO.encontrado(), mapperService.convUsuarioAll(usuario)))
                .orElseGet(() -> new ObjectResponse<>(404, Modulo.USUARIO.noEncontrado(), null));
    }
}