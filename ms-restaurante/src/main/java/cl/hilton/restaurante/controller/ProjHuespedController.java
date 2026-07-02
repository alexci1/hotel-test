package cl.hilton.restaurante.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.hilton.restaurante.dto.ProjHuespedRequest;
import cl.hilton.restaurante.dto.ProjHuespedResponse;
import cl.hilton.restaurante.service.ProjHuespedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/huespedes")
@RequiredArgsConstructor
public class ProjHuespedController {

    private final ProjHuespedService huespedService;

    @Operation(summary = "Listar registros", description = "Lista registros")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProjHuespedResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    @GetMapping
    public List<ProjHuespedResponse> findAll() {
        return huespedService.findAll();
    }

    @Operation(summary = "Obtener registro", description = "Obtiene registro")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = ProjHuespedResponse.class))),
        @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    @GetMapping("/email/{email}")
    public ProjHuespedResponse findByEmail(@PathVariable String email) {
        return huespedService.findByEmail(email);
    }

    @Operation(summary = "Listar registros", description = "Lista registros")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProjHuespedResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    @GetMapping("/habitacion/{numeroHabitacion}")
    public List<ProjHuespedResponse> findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        return huespedService.findByNumeroHabitacion(numeroHabitacion);
    }

    @Operation(summary = "Listar registros", description = "Lista registros")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProjHuespedResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    @GetMapping("/nombre/{nombreCompleto}")
    public List<ProjHuespedResponse> findByNombreCompleto(@PathVariable String nombreCompleto) {
        return huespedService.findByNombreCompleto(nombreCompleto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjHuespedResponse create(@Valid @RequestBody ProjHuespedRequest request) {
        return huespedService.create(request);
    }

    @PostMapping("/sincronizar/email/{email}")
    public ProjHuespedResponse sincronizarPorEmail(@PathVariable String email) {
        return huespedService.sincronizarPorEmail(email);
    }

    @PutMapping("/email/{email}")
    public ProjHuespedResponse update(
            @PathVariable String email,
            @Valid @RequestBody ProjHuespedRequest request) {
        return huespedService.update(email, request);
    }

    @DeleteMapping("/email/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByEmail(@PathVariable String email) {
        huespedService.deleteByEmail(email);
    }
}
