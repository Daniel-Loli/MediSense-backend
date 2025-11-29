package com.medisense.medisense_back.controller;

import com.medisense.medisense_back.dto.base.ListResponse;
import com.medisense.medisense_back.dto.base.ObjectResponse;
import com.medisense.medisense_back.dto.especialidad.EspecialidadAllResponse;
import com.medisense.medisense_back.dto.especialidad.EspecialidadCreateRequest;
import com.medisense.medisense_back.dto.especialidad.EspecialidadShortResponse;
import com.medisense.medisense_back.dto.especialidad.EspecialidadUpdateRequest;
import com.medisense.medisense_back.service.interfaces.IEspecialidadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/especialidad")
@RequiredArgsConstructor
@Tag(name = "Especialidad", description = "Endpoints para la gestión de especialidades clínicas")
public class EspecialidadController {

    private final IEspecialidadService service;

    // ------------------------ REGISTRO ------------------------ //

    @Operation(summary = "Registrar una nueva especialidad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Especialidad registrada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class)))
    })
    @PostMapping("/registrar")
    public ResponseEntity<ObjectResponse<EspecialidadAllResponse>> registrarEspecialidad(
            @RequestBody EspecialidadCreateRequest request
    ) {
        ObjectResponse<EspecialidadAllResponse> response = service.registrar(request);
        return ResponseEntity.status(response.status()).body(response);
    }


    @Operation(summary = "Registrar múltiples especialidades")
    @PostMapping("/registrar-all")
    public ResponseEntity<ListResponse<EspecialidadAllResponse>> registrarAll(
            @RequestBody java.util.List<EspecialidadCreateRequest> requests
    ) {
        ListResponse<EspecialidadAllResponse> response = service.registrarAll(requests);
        return ResponseEntity.status(response.status()).body(response);
    }

    // ------------------------ LISTAR ------------------------ //

    @Operation(summary = "Listar todas las especialidades")
    @GetMapping("/listar")
    public ResponseEntity<ListResponse<EspecialidadShortResponse>> listarEspecialidades() {
        ListResponse<EspecialidadShortResponse> response = service.listar();
        return ResponseEntity.status(response.status()).body(response);
    }

    // ------------------------ BUSCAR ------------------------ //

    @Operation(summary = "Buscar especialidad por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Especialidad encontrada"),
            @ApiResponse(responseCode = "404", description = "Especialidad no encontrada",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class)))
    })
    @GetMapping("/buscar")
    public ResponseEntity<ObjectResponse<EspecialidadAllResponse>> buscarEspecialidad(
            @RequestParam("idEspecialidad") Long idEspecialidad
    ) {
        ObjectResponse<EspecialidadAllResponse> response = service.buscar(idEspecialidad);
        return ResponseEntity.status(response.status()).body(response);
    }

    // ------------------------ ACTUALIZAR ------------------------ //

    @Operation(summary = "Actualizar especialidad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Especialidad actualizada"),
            @ApiResponse(responseCode = "404", description = "Especialidad no encontrada",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class)))
    })
    @PutMapping("/actualizar/{idEspecialidad}")
    public ResponseEntity<ObjectResponse<EspecialidadAllResponse>> actualizarEspecialidad(
            @PathVariable("idEspecialidad") Long idEspecialidad,
            @RequestBody EspecialidadUpdateRequest request
    ) {
        ObjectResponse<EspecialidadAllResponse> response = service.actualizar(idEspecialidad, request);
        return ResponseEntity.status(response.status()).body(response);
    }

    // ------------------------ ELIMINAR ------------------------ //

    @Operation(summary = "Deshabilitar especialidad (soft delete)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Especialidad eliminada"),
            @ApiResponse(responseCode = "404", description = "Especialidad no encontrada",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class)))
    })
    @DeleteMapping("/eliminar/{idEspecialidad}")
    public ResponseEntity<ObjectResponse<String>> eliminarEspecialidad(
            @PathVariable("idEspecialidad") Long idEspecialidad
    ) {
        ObjectResponse<String> response = service.eliminar(idEspecialidad);
        return ResponseEntity.status(response.status()).body(response);
    }
}
