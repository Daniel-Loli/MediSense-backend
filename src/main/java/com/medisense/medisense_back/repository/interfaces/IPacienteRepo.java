package com.medisense.medisense_back.repository.interfaces;

import com.medisense.medisense_back.model.Paciente;
import com.medisense.medisense_back.model.Paciente;
import com.medisense.medisense_back.repository.base.IGenericRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IPacienteRepo extends IGenericRepo<Paciente, Long> {
    @Query("SELECT e FROM Paciente e WHERE e.enabled = true")
    List<Paciente> listar();

    @Query("SELECT e FROM Paciente e WHERE e.enabled = true")
    Page<Paciente> listarPage(Pageable pageable);

    @Query("SELECT e FROM Paciente e WHERE e.idPaciente = :id AND e.enabled = true")
    Optional<Paciente> buscarPorId(@Param("id") Long idPaciente);

    @Query("SELECT e FROM Paciente e WHERE e.email = :email AND e.enabled = true")
    Optional<Paciente> buscarPorEmail(String email);

    @Query("SELECT e FROM Paciente e WHERE e.dni = :dni AND e.enabled = true")
    Optional<Paciente> buscarPorDni(String dni);

    @Query("SELECT e FROM Paciente e WHERE e.enabled = true AND e.dni = :dni")
    List<Paciente> listarPorDni(String dni);
}
