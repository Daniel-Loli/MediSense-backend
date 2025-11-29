package com.medisense.medisense_back.controller;

import com.medisense.medisense_back.dto.base.ListPageResponse;
import com.medisense.medisense_back.dto.base.ListResponse;
import com.medisense.medisense_back.dto.base.ObjectResponse;
import com.medisense.medisense_back.dto.paciente.PacienteAllResponse;
import com.medisense.medisense_back.dto.paciente.PacienteCreateRequest;
import com.medisense.medisense_back.dto.paciente.PacienteShortResponse;
import com.medisense.medisense_back.dto.paciente.PacienteUpdateRequest;
import com.medisense.medisense_back.service.interfaces.IPacienteService;
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

@RestController
@RequestMapping("/paciente")
@RequiredArgsConstructor
@Tag(name = "Paciente", description = "Endpoints para la gestión de pacientes")
public class PacienteController {

    private final IPacienteService service;

    // ------------------------ REGISTRAR ------------------------ //

    @Operation(summary = "Registrar un paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Paciente registrado"),
            @ApiResponse(responseCode = "409", description = "Documento o email duplicado",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class)))
    })
    @PostMapping("/registrar")
    public ResponseEntity<ObjectResponse<PacienteAllResponse>> registrar(
            @RequestBody PacienteCreateRequest request
    ) {
        ObjectResponse<PacienteAllResponse> response = service.registrar(request);
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Registrar múltiples pacientes")
    @PostMapping("/registrar-all")
    public ResponseEntity<ListResponse<PacienteAllResponse>> registrarAll(
            @RequestBody java.util.List<PacienteCreateRequest> requests
    ) {
        ListResponse<PacienteAllResponse> response = service.registrarAll(requests);
        return ResponseEntity.status(response.status()).body(response);
    }

    // ------------------------ LISTAR ------------------------ //

    @Operation(summary = "Listar todos los pacientes")
    @GetMapping("/listar")
    public ResponseEntity<ListResponse<PacienteShortResponse>> listar() {
        ListResponse<PacienteShortResponse> response = service.listar();
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Listar pacientes con paginación")
    @GetMapping("/listar-page")
    public ResponseEntity<ListPageResponse<PacienteShortResponse>> listarPage(Pageable pageable) {
        ListPageResponse<PacienteShortResponse> response = service.listarPage(pageable);
        return ResponseEntity.status(response.status()).body(response);
    }

    // ------------------------ BUSCAR ------------------------ //

    @Operation(summary = "Buscar paciente por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class)))
    })
    @GetMapping("/buscar")
    public ResponseEntity<ObjectResponse<PacienteAllResponse>> buscar(
            @RequestParam Long idPaciente
    ) {
        ObjectResponse<PacienteAllResponse> response = service.buscar(idPaciente);
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Buscar paciente por email")
    @GetMapping("/buscar-email")
    public ResponseEntity<ObjectResponse<PacienteAllResponse>> buscarPorEmail(
            @RequestParam String email
    ) {
        ObjectResponse<PacienteAllResponse> response = service.buscarPorEmail(email);
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Buscar paciente por DNI")
    @GetMapping("/buscar-dni")
    public ResponseEntity<ObjectResponse<PacienteAllResponse>> buscarPorDni(
            @RequestParam String dni
    ) {
        ObjectResponse<PacienteAllResponse> response = service.buscarPorDni(dni);
        return ResponseEntity.status(response.status()).body(response);
    }

    // ------------------------ ACTUALIZAR ------------------------ //

    @Operation(summary = "Actualizar datos de un paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente actualizado"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class))),
            @ApiResponse(responseCode = "409", description = "Documento o email duplicado",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class)))
    })
    @PutMapping("/actualizar/{idPaciente}")
    public ResponseEntity<ObjectResponse<PacienteAllResponse>> actualizar(
            @PathVariable Long idPaciente,
            @RequestBody PacienteUpdateRequest request
    ) {
        ObjectResponse<PacienteAllResponse> response = service.actualizar(idPaciente, request);
        return ResponseEntity.status(response.status()).body(response);
    }

    // ------------------------ ELIMINAR ------------------------ //

    @Operation(summary = "Eliminar paciente (Soft Delete)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paciente eliminado"),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class)))
    })
    @DeleteMapping("/eliminar/{idPaciente}")
    public ResponseEntity<ObjectResponse<String>> eliminar(
            @PathVariable Long idPaciente
    ) {
        ObjectResponse<String> response = service.eliminar(idPaciente);
        return ResponseEntity.status(response.status()).body(response);
    }
}
