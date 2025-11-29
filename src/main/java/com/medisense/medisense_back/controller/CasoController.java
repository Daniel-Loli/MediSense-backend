package com.medisense.medisense_back.controller;

import com.medisense.medisense_back.dto.base.ListPageResponse;
import com.medisense.medisense_back.dto.base.ListResponse;
import com.medisense.medisense_back.dto.base.ObjectResponse;
import com.medisense.medisense_back.dto.caso.*;
import com.medisense.medisense_back.service.interfaces.ICasoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/caso")
@RequiredArgsConstructor
@Tag(name = "Caso", description = "Endpoints para la gestión de los casos clínicos")
public class CasoController {

    private final ICasoService service;

    // ------------------------ REGISTRAR ------------------------ //

    @Operation(summary = "Registrar un caso clínico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Caso registrado"),
            @ApiResponse(responseCode = "409", description = "Conflicto de duplicado",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class))),
            @ApiResponse(responseCode = "404", description = "Datos de referencia no encontrados",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class)))
    })
    @PostMapping("/registrar")
    public ResponseEntity<ObjectResponse<CasoAllResponse>> registrar(
            @Valid @RequestBody CasoCreateRequest request
    ) {
        ObjectResponse<CasoAllResponse> response = service.registrar(request);
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Registrar múltiples casos clínicos")
    @PostMapping("/registrar-all")
    public ResponseEntity<ListResponse<CasoAllResponse>> registrarAll(
            @Valid @RequestBody java.util.List<CasoCreateRequest> requests
    ) {
        ListResponse<CasoAllResponse> response = service.registrarAll(requests);
        return ResponseEntity.status(response.status()).body(response);
    }

    // ------------------------ LISTAR ------------------------ //

    @Operation(summary = "Listar todos los casos clínicos")
    @GetMapping("/listar")
    public ResponseEntity<ListResponse<CasoAllResponse>> listar() {
        ListResponse<CasoAllResponse> response = service.listar();
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Listar casos clínicos con paginación")
    @GetMapping("/listar-page")
    public ResponseEntity<ListPageResponse<CasoAllResponse>> listarPage(
            Pageable pageable
    ) {
        ListPageResponse<CasoAllResponse> response = service.listarPage(pageable);
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Listar todos los casos clínicos segun paciente")
    @GetMapping("/listar-paciente")
    public ResponseEntity<ListResponse<CasoPShortResponse>> listarPorPaciente(
            @RequestParam Long idPaciente
    ) {
        ListResponse<CasoPShortResponse> response = service.listarPorPaciente(idPaciente);
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Listar casos clínicos con paginación segun paciente")
    @GetMapping("/listar-page-paciente")
    public ResponseEntity<ListPageResponse<CasoPShortResponse>> listarPagePorPaciente(
            @RequestParam Long idPaciente,
            Pageable pageable
    ) {
        ListPageResponse<CasoPShortResponse> response = service.listarPagePorPaciente(pageable,idPaciente);
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Listar todos los casos clínicos segun paciente")
    @GetMapping("/listar-especialidad")
    public ResponseEntity<ListResponse<CasoEShortResponse>> listarPorEspecialidad(
            @RequestParam Long idEspecialidad
    ) {
        ListResponse<CasoEShortResponse> response = service.listarPorEspecialidad(idEspecialidad);
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Listar casos clínicos con paginación segun paciente")
    @GetMapping("/listar-page-especialidad")
    public ResponseEntity<ListPageResponse<CasoEShortResponse>> listarPagePorEspecialidad(
            @RequestParam Long idEspecialidad,
            Pageable pageable
    ) {
        ListPageResponse<CasoEShortResponse> response = service.listarPagePorEspecialidad(pageable,idEspecialidad);
        return ResponseEntity.status(response.status()).body(response);
    }


    // ------------------------ BUSCAR ------------------------ //

    @Operation(summary = "Buscar caso clínico por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Caso encontrado"),
            @ApiResponse(responseCode = "404", description = "Caso no encontrado",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class)))
    })
    @GetMapping("/buscar")
    public ResponseEntity<ObjectResponse<CasoAllResponse>> buscar(
            @RequestParam Long idCaso
    ) {
        ObjectResponse<CasoAllResponse> response = service.buscar(idCaso);
        return ResponseEntity.status(response.status()).body(response);
    }

    // ------------------------ ACTUALIZAR ------------------------ //

    @Operation(summary = "Actualizar datos de un caso clínico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Caso actualizado"),
            @ApiResponse(responseCode = "404", description = "Caso no encontrado",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conflicto de duplicado",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class)))
    })
    @PutMapping("/actualizar/{idCaso}")
    public ResponseEntity<ObjectResponse<CasoAllResponse>> actualizar(
            @PathVariable Long idCaso,
            @Valid @RequestBody CasoUpdateRequest request
    ) {
        ObjectResponse<CasoAllResponse> response = service.actualizar(idCaso, request);
        return ResponseEntity.status(response.status()).body(response);
    }

    // ------------------------ ELIMINAR ------------------------ //

    @Operation(summary = "Eliminar caso clínico (Soft Delete)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Caso eliminado"),
            @ApiResponse(responseCode = "404", description = "Caso no encontrado",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class)))
    })
    @DeleteMapping("/eliminar/{idCaso}")
    public ResponseEntity<ObjectResponse<String>> eliminar(
            @PathVariable Long idCaso
    ) {
        ObjectResponse<String> response = service.eliminar(idCaso);
        return ResponseEntity.status(response.status()).body(response);
    }
}
