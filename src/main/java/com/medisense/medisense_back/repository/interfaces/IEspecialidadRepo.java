package com.medisense.medisense_back.repository.interfaces;

import com.medisense.medisense_back.model.Especialidad;
import com.medisense.medisense_back.model.Especialista;
import com.medisense.medisense_back.repository.base.IGenericRepo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IEspecialidadRepo extends IGenericRepo<Especialidad, Long> {

    @Query("SELECT e FROM Especialidad e WHERE e.enabled = true")
    List<Especialidad> listar();

    @Query("SELECT e FROM Especialidad e WHERE e.idEspecialidad = :id AND e.enabled = true")
    Optional<Especialidad> buscarPorId(@Param("id") Long idEspecialidad);
}
