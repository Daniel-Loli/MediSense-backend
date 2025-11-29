package com.medisense.medisense_back.controller;

import com.medisense.medisense_back.dto.base.ListResponse;
import com.medisense.medisense_back.dto.base.ObjectResponse;
import com.medisense.medisense_back.dto.material.MaterialAllResponse;
import com.medisense.medisense_back.dto.material.MaterialShortResponse;
import com.medisense.medisense_back.service.interfaces.IMaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/material")
@RequiredArgsConstructor
@Tag(name = "MaterialAcademico", description = "Endpoints para la gestión de material académico")
public class MaterialController {

    private final IMaterialService service;

    // ------------------------ REGISTRO ------------------------ //

    @Operation(summary = "Registrar material académico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Material registrado"),
            @ApiResponse(responseCode = "400", description = "Archivo inválido",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class)))
    })
    @PostMapping("/registrar")
    public ResponseEntity<ObjectResponse<MaterialAllResponse>> registrarMaterial(
            @RequestParam("file") MultipartFile file,
            @RequestParam("descripcion") String descripcion
    ) {
        var response = service.registrarMaterial(file, descripcion);
        return ResponseEntity.status(response.status()).body(response);
    }

    // ------------------------ LISTAR ------------------------ //

    @Operation(summary = "Listar todos los materiales académicos")
    @GetMapping("/listar")
    public ResponseEntity<ListResponse<MaterialShortResponse>> listarMateriales() {
        var response = service.listarMaaterial();
        return ResponseEntity.status(response.status()).body(response);
    }

    // ------------------------ BUSCAR ------------------------ //

    @Operation(summary = "Buscar material académico por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Material encontrado"),
            @ApiResponse(responseCode = "404", description = "Material no encontrado",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class)))
    })
    @GetMapping("/buscar")
    public ResponseEntity<ObjectResponse<MaterialAllResponse>> buscarMaterial(
            @RequestParam("idMaterial") Long idMaterial
    ) {
        var response = service.buscarMaterial(idMaterial);
        return ResponseEntity.status(response.status()).body(response);
    }

    // ------------------------ ELIMINAR ------------------------ //

    @Operation(summary = "Eliminar material académico (soft delete + borrar archivo en Azure)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Material eliminado"),
            @ApiResponse(responseCode = "404", description = "Material no encontrado",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class)))
    })
    @DeleteMapping("/eliminar/{idMaterial}")
    public ResponseEntity<ObjectResponse<String>> eliminarMaterial(
            @PathVariable("idMaterial") Long idMaterial
    ) {
        var response = service.eliminarMaterial(idMaterial);
        return ResponseEntity.status(response.status()).body(response);
    }
}
