package com.medisense.medisense_back.controller;

import com.medisense.medisense_back.dto.base.*;
import com.medisense.medisense_back.dto.cita.*;
import com.medisense.medisense_back.service.interfaces.ICitaService;
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

import java.util.List;

@RestController
@RequestMapping("/cita")
@RequiredArgsConstructor
@Tag(name = "Cita", description = "Endpoints para la gestión de las citas clínicas")
public class CitaController {

    private final ICitaService service;

    // ================= LISTADOS =====================

    @Operation(summary = "Listar todas las citas")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping("/listar")
    public ResponseEntity<ListResponse<CitaAllResponse>> listar() {
        ListResponse<CitaAllResponse> response = service.listar();
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Listar citas con paginación")
    @ApiResponse(responseCode = "200", description = "Listado paginado exitoso")
    @GetMapping("/listar-page")
    public ResponseEntity<ListPageResponse<CitaAllResponse>> listarPage(Pageable pageable) {
        ListPageResponse<CitaAllResponse> response = service.listarPage(pageable);
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Listar citas por especialista")
    @ApiResponse(responseCode = "200", description = "Listado por especialista generado correctamente")
    @GetMapping("/listar-especialista")
    public ResponseEntity<ListResponse<CitaEShortResponse>> listarPorEspecialista(
            @RequestParam Long idEspecialista
    ) {
        ListResponse<CitaEShortResponse> response = service.listarPorEspecialista(idEspecialista);
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Listar citas por especialista con paginación")
    @ApiResponse(responseCode = "200", description = "Listado paginado por especialista")
    @GetMapping("/listar-page-especialista")
    public ResponseEntity<ListPageResponse<CitaEShortResponse>> listarPagePorEspecialista(
            @RequestParam Long idEspecialista,
            Pageable pageable
    ) {
        ListPageResponse<CitaEShortResponse> response = service.listarPagePorEspecialista(pageable, idEspecialista);
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Listar citas por paciente")
    @ApiResponse(responseCode = "200", description = "Listado por paciente generado correctamente")
    @GetMapping("/listar-paciente")
    public ResponseEntity<ListResponse<CitaPShortResponse>> listarPorPaciente(
            @RequestParam Long idPaciente
    ) {
        ListResponse<CitaPShortResponse> response = service.listarPorPaciente(idPaciente);
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Listar citas por paciente con paginación")
    @ApiResponse(responseCode = "200", description = "Listado paginado por paciente")
    @GetMapping("/listar-page-paciente")
    public ResponseEntity<ListPageResponse<CitaPShortResponse>> listarPagePorPaciente(
            @RequestParam Long idPaciente,
            Pageable pageable
    ) {
        ListPageResponse<CitaPShortResponse> response = service.listarPagePorPaciente(pageable, idPaciente);
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Listar citas por caso")
    @ApiResponse(responseCode = "200", description = "Listado por caso generado correctamente")
    @GetMapping("/listar-caso")
    public ResponseEntity<ListResponse<CitaCShortResponse>> listarPorCaso(
            @RequestParam Long idCaso
    ) {
        ListResponse<CitaCShortResponse> response = service.listarPorCaso(idCaso);
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Listar citas por caso con paginación")
    @ApiResponse(responseCode = "200", description = "Listado paginado por caso generado correctamente")
    @GetMapping("/listar-page-caso")
    public ResponseEntity<ListPageResponse<CitaCShortResponse>> listarPagePorCaso(
            @RequestParam Long idCaso,
            Pageable pageable
    ) {
        ListPageResponse<CitaCShortResponse> response = service.listarPagePorCaso(pageable, idCaso);
        return ResponseEntity.status(response.status()).body(response);
    }

    // ================= BUSCAR =====================

    @Operation(summary = "Buscar una cita por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cita encontrada"),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class)))
    })
    @GetMapping("/buscar")
    public ResponseEntity<ObjectResponse<CitaAllResponse>> buscar(
            @RequestParam Long idCita
    ) {
        ObjectResponse<CitaAllResponse> response = service.buscar(idCita);
        return ResponseEntity.status(response.status()).body(response);
    }

    // ================= REGISTRO =====================

    @Operation(summary = "Registrar una cita")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cita registrada"),
            @ApiResponse(responseCode = "404", description = "Paciente, Especialista o Caso no encontrado",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class)))
    })
    @PostMapping("/registrar")
    public ResponseEntity<ObjectResponse<CitaAllResponse>> registrar(
            @RequestBody CitaCreateRequest request
    ) {
        ObjectResponse<CitaAllResponse> response = service.registrar(request);
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Registrar múltiples citas")
    @ApiResponse(responseCode = "201", description = "Citas registradas parcialmente o completamente")
    @PostMapping("/registrar-all")
    public ResponseEntity<ListResponse<CitaAllResponse>> registrarAll(
            @RequestBody List<CitaCreateRequest> requests
    ) {
        ListResponse<CitaAllResponse> response = service.registrarAll(requests);
        return ResponseEntity.status(response.status()).body(response);
    }

    // ================= ACTUALIZAR =====================

    @Operation(summary = "Actualizar los datos de una cita")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cita actualizada"),
            @ApiResponse(responseCode = "404", description = "Cita o Especialista no encontrado",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class)))
    })
    @PutMapping("/actualizar")
    public ResponseEntity<ObjectResponse<CitaAllResponse>> actualizar(
            @RequestParam Long idCita,
            @RequestBody CitaUpdateRequest request
    ) {
        ObjectResponse<CitaAllResponse> response = service.actualizar(idCita, request);
        return ResponseEntity.status(response.status()).body(response);
    }

    // ================= ELIMINAR =====================

    @Operation(summary = "Eliminar (deshabilitar) una cita")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cita eliminada"),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class)))
    })
    @DeleteMapping("/eliminar")
    public ResponseEntity<ObjectResponse<String>> eliminar(
            @RequestParam Long idCita
    ) {
        ObjectResponse<String> response = service.eliminar(idCita);
        return ResponseEntity.status(response.status()).body(response);
    }
}
