package com.medisense.medisense_back.controller;

import com.medisense.medisense_back.dto.base.ListPageResponse;
import com.medisense.medisense_back.dto.base.ListResponse;
import com.medisense.medisense_back.dto.base.ObjectResponse;
import com.medisense.medisense_back.dto.especialista.EspecialistaAllResponse;
import com.medisense.medisense_back.dto.especialista.EspecialistaCreateRequest;
import com.medisense.medisense_back.dto.especialista.EspecialistaShortResponse;
import com.medisense.medisense_back.dto.especialista.EspecialistaUpdateRequest;
import com.medisense.medisense_back.service.interfaces.IEspecialistaService;
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
@RequestMapping("/especialista")
@RequiredArgsConstructor
@Tag(name = "Especialista", description = "Endpoints para la gestión de especialista")
public class EspecialistaController {

    private final IEspecialistaService service;

    // ------------------------ REGISTRAR ------------------------ //

    @Operation(summary = "Registrar un especialista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Especialista registrado"),
            @ApiResponse(responseCode = "409", description = "Conflicto de duplicado",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class))),
            @ApiResponse(responseCode = "404", description = "Especialidad o Rol no encontrado",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class)))
    })
    @PostMapping("/registrar")
    public ResponseEntity<ObjectResponse<EspecialistaAllResponse>> registrar(
            @RequestBody EspecialistaCreateRequest request
    ) {
        var response = service.registrar(request);
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Registrar múltiples especialistas")
    @PostMapping("/registrar-all")
    public ResponseEntity<ListResponse<EspecialistaAllResponse>> registrarAll(
            @RequestBody java.util.List<EspecialistaCreateRequest> requests
    ) {
        var response = service.registrarAll(requests);
        return ResponseEntity.status(response.status()).body(response);
    }

    // ------------------------ LISTAR ------------------------ //

    @Operation(summary = "Listar todos los especialistas")
    @GetMapping("/listar")
    public ResponseEntity<ListResponse<EspecialistaAllResponse>> listar() {
        var response = service.listar();
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Listar especialistas con paginación")
    @GetMapping("/listar-page")
    public ResponseEntity<ListPageResponse<EspecialistaShortResponse>> listarPage(Pageable pageable) {
        var response = service.listarPage(pageable);
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Listar especialistas por especialidad")
    @GetMapping("/listar-especialidad")
    public ResponseEntity<ListResponse<EspecialistaShortResponse>> listarPorEspecialidad(
            @RequestParam Long idEspecialidad
    ) {
        var response = service.listarPorEspecialidad(idEspecialidad);
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Listar especialistas por especialidad con paginación")
    @GetMapping("/listar-page-especialidad")
    public ResponseEntity<ListPageResponse<EspecialistaShortResponse>> listarPagePorEspecialidad(
            Pageable pageable,
            @RequestParam Long idEspecialidad
    ) {
        var response = service.listarPagePorEspecialidad(pageable, idEspecialidad);
        return ResponseEntity.status(response.status()).body(response);
    }

    // ------------------------ BUSCAR ------------------------ //

    @Operation(summary = "Buscar especialista por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Especialista encontrado"),
            @ApiResponse(responseCode = "404", description = "Especialista no encontrado",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class)))
    })
    @GetMapping("/buscar")
    public ResponseEntity<ObjectResponse<EspecialistaAllResponse>> buscar(
            @RequestParam Long idEspecialista
    ) {
        var response = service.buscar(idEspecialista);
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Buscar especialista por email")
    @GetMapping("/buscar-email")
    public ResponseEntity<ObjectResponse<EspecialistaAllResponse>> buscarPorEmail(
            @RequestParam String email
    ) {
        var response = service.buscarPorEmail(email);
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Buscar especialista por documento de identidad")
    @GetMapping("/buscar-documento")
    public ResponseEntity<ObjectResponse<EspecialistaAllResponse>> buscarPorDocumento(
            @RequestParam String documentoIdentidad
    ) {
        var response = service.buscarPorDocumentoIdentidad(documentoIdentidad);
        return ResponseEntity.status(response.status()).body(response);
    }

    @Operation(summary = "Buscar especialista por ID de usuario")
    @GetMapping("/buscar-idusuario")
    public ResponseEntity<ObjectResponse<EspecialistaAllResponse>> buscarPorIdUsuario(
            @RequestParam Long idUsuario
    ) {
        var response = service.buscarPorIdUsuario(idUsuario);
        return ResponseEntity.status(response.status()).body(response);
    }

    // ------------------------ ACTUALIZAR ------------------------ //

    @Operation(summary = "Actualizar datos de un especialista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Especialista actualizado"),
            @ApiResponse(responseCode = "404", description = "Especialista no encontrado",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class))),
            @ApiResponse(responseCode = "409", description = "Documento duplicado",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class)))
    })
    @PutMapping("/actualizar/{idEspecialista}")
    public ResponseEntity<ObjectResponse<EspecialistaAllResponse>> actualizar(
            @PathVariable Long idEspecialista,
            @RequestBody EspecialistaUpdateRequest request
    ) {
        var response = service.actualizar(idEspecialista, request);
        return ResponseEntity.status(response.status()).body(response);
    }

    // ------------------------ ELIMINAR ------------------------ //

    @Operation(summary = "Eliminar especialista (Soft Delete)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Especialista eliminado"),
            @ApiResponse(responseCode = "404", description = "Especialista no encontrado",
                    content = @Content(schema = @Schema(implementation = ObjectResponse.class)))
    })
    @DeleteMapping("/eliminar/{idEspecialista}")
    public ResponseEntity<ObjectResponse<String>> eliminar(
            @PathVariable Long idEspecialista
    ) {
        var response = service.eliminar(idEspecialista);
        return ResponseEntity.status(response.status()).body(response);
    }
}
