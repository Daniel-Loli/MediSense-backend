package com.medisense.medisense_back.repository.interfaces;

import com.medisense.medisense_back.model.Caso;
import com.medisense.medisense_back.model.Caso;
import com.medisense.medisense_back.repository.base.IGenericRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ICasoRepo extends IGenericRepo<Caso,Long> {
    @Query("SELECT e FROM Caso e WHERE e.enabled = true")
    List<Caso> listar();

    @Query("SELECT e FROM Caso e WHERE e.enabled = true")
    Page<Caso> listarPage(Pageable pageable);

    @Query("SELECT e FROM Caso e WHERE e.enabled = true AND e.paciente.idPaciente =: id")
    List<Caso> listarPorPaciente(@Param("id") Long idPaciente);

    @Query("SELECT e FROM Caso e WHERE e.enabled = true AND e.paciente.idPaciente =: id")
    Page<Caso> listarPagePorPaciente(Pageable pageable,@Param("id") Long idPaciente);

    @Query("SELECT e FROM Caso e WHERE e.enabled = true AND e.especialidad.idEspecialidad =: id")
    List<Caso> listarPorEspecialidad(@Param("id") Long idEspecialidad);

    @Query("SELECT e FROM Caso e WHERE e.enabled = true AND e.especialidad.idEspecialidad =: id")
    Page<Caso> listarPagePorEspecialidad(Pageable pageable,@Param("id") Long idEspecialidad);

    @Query("SELECT e FROM Caso e WHERE e.idCaso = :id AND e.enabled = true")
    Optional<Caso> buscarPorId(@Param("id") Long idCaso);

}
