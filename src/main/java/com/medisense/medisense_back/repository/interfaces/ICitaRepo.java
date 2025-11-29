package com.medisense.medisense_back.repository.interfaces;

import com.medisense.medisense_back.model.Cita;
import com.medisense.medisense_back.repository.base.IGenericRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ICitaRepo extends IGenericRepo<Cita,Long> {
    @Query("SELECT e FROM Cita e WHERE e.enabled = true")
    List<Cita> listar();

    @Query("SELECT e FROM Cita e WHERE e.enabled = true")
    Page<Cita> listarPage(Pageable pageable);

    @Query("SELECT e FROM Cita e WHERE e.enabled = true AND e.paciente.idPaciente =: id")
    List<Cita> listarPorPaciente(@Param("id") Long idPaciente);

    @Query("SELECT e FROM Cita e WHERE e.enabled = true AND e.paciente.idPaciente =: id")
    Page<Cita> listarPagePorPaciente(Pageable pageable,@Param("id") Long idPaciente);

    @Query("SELECT e FROM Cita e WHERE e.enabled = true AND e.especialista.idEspecialista =: id")
    List<Cita> listarPorEspecialista(@Param("id") Long idEspecialista);

    @Query("SELECT e FROM Cita e WHERE e.enabled = true AND e.especialista.idEspecialista =: id")
    Page<Cita> listarPagePorEspecialista(Pageable pageable,@Param("id") Long idEspecialista);


    @Query("SELECT e FROM Cita e WHERE e.enabled = true AND e.caso.idCaso =: id")
    List<Cita> listarPorCaso(@Param("id") Long idCaso);

    @Query("SELECT e FROM Cita e WHERE e.enabled = true AND e.caso.idCaso =: id")
    Page<Cita> listarPagePorCaso(Pageable pageable,@Param("id") Long idCaso);

    @Query("SELECT e FROM Cita e WHERE e.idCita = :id AND e.enabled = true")
    Optional<Cita> buscarPorId(@Param("id") Long idCita);

}
