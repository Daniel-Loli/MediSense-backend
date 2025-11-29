package com.medisense.medisense_back.service.interfaces;

import com.medisense.medisense_back.dto.base.ListPageResponse;
import com.medisense.medisense_back.dto.base.ListResponse;
import com.medisense.medisense_back.dto.base.ObjectResponse;
import com.medisense.medisense_back.dto.usuario.UsuarioAllResponse;
import org.springframework.data.domain.Pageable;

public interface IUsuarioService {
    ObjectResponse<Integer> contar();
    ListResponse<UsuarioAllResponse> listar();
    ListPageResponse<UsuarioAllResponse> listarPage(Pageable pageable);
    ObjectResponse<UsuarioAllResponse> buscar(Long idUsuario);
}
