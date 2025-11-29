package com.medisense.medisense_back.repository.interfaces;

import com.medisense.medisense_back.model.Especialista;
import com.medisense.medisense_back.model.Rol;
import com.medisense.medisense_back.repository.base.IGenericRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IEspecialistaRepo extends IGenericRepo<Especialista, Long> {

    @Query("SELECT e FROM Especialista e WHERE e.enabled = true")
    List<Especialista> listar();

    @Query("SELECT e FROM Especialista e WHERE e.enabled = true")
    Page<Especialista> listarPage(Pageable pageable);

    @Query("SELECT e FROM Especialista e WHERE e.enabled = true AND e.especialidad.idEspecialidad =: id")
    List<Especialista> listarPorEspecialidad(@Param("id") Long idEspecialidad);

    @Query("SELECT e FROM Especialista e WHERE e.enabled = true AND e.especialidad.idEspecialidad =: id")
    Page<Especialista> listarPagePorEspecialidad(Pageable pageable,@Param("id") Long idEspecialidad);

    @Query("SELECT e FROM Especialista e WHERE e.idEspecialista = :id AND e.enabled = true")
    Optional<Especialista> buscarPorId(@Param("id") Long idEspecialista);

    @Query("SELECT e FROM Especialista e WHERE e.usuario.email = :email AND e.enabled = true")
    Optional<Especialista> buscarPorEmail(String email);

    @Query("SELECT e FROM Especialista e WHERE e.dni = :dni AND e.enabled = true")
    Optional<Especialista> buscarPorDocIdentidad(String dni);

    @Query("SELECT e FROM Especialista e WHERE e.usuario.idUsuario = :id AND e.enabled = true")
    Optional<Especialista> buscarPorIdUsuario(@Param("id") Long idUsuario);

    @Query("SELECT e FROM Especialista e WHERE e.enabled = true AND e.dni = :dni")
    List<Especialista> listarPorDocumento(String dni);

}
