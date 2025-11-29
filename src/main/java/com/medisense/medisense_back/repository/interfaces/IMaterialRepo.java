package com.medisense.medisense_back.repository.interfaces;

import com.medisense.medisense_back.model.Material;
import com.medisense.medisense_back.repository.base.IGenericRepo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IMaterialRepo
        extends IGenericRepo<Material,Long> {

    @Query("SELECT e FROM Material e WHERE e.enabled = true")
    List<Material> listar();

    @Query("SELECT e FROM Material e WHERE e.idMaterial = :id AND e.enabled = true")
    Optional<Material> buscarPorId(@Param("id") Long isMaterial);

}
