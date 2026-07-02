package cl.hilton.habitaciones.controller;

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

import cl.hilton.habitaciones.dto.ProjTarifaRequest;
import cl.hilton.habitaciones.dto.ProjTarifaResponse;
import cl.hilton.habitaciones.service.ProjTarifaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tarifas")
@RequiredArgsConstructor
public class ProjTarifaController {

    private final ProjTarifaService projTarifaService;

    @Operation(summary = "Listar tarifas proyectadas", description = "Retorna todas las tarifas proyectadas registradas en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tarifas proyectadas encontradas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProjTarifaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron tarifas proyectadas", content = @Content)
    })
    @GetMapping
    public List<ProjTarifaResponse> findAll() {
        return projTarifaService.findAll();
    }

    @Operation(summary = "Obtener tarifa proyectada por tipo de habitación", description = "Retorna una tarifa proyectada según el tipo de habitación")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tarifa proyectada encontrada",
            content = @Content(schema = @Schema(implementation = ProjTarifaResponse.class))),
        @ApiResponse(responseCode = "404", description = "Tarifa proyectada no encontrada para el tipo de habitación indicado", content = @Content)
    })
    @GetMapping("/tipo/{tipoHabitacion}")
    public ProjTarifaResponse findByTipoHabitacion(@PathVariable String tipoHabitacion) {
        return projTarifaService.findByTipoHabitacion(tipoHabitacion);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjTarifaResponse create(@Valid @RequestBody ProjTarifaRequest request) {
        return projTarifaService.create(request);
    }

    @PostMapping("/sincronizar/tipo/{tipoHabitacion}")
    public ProjTarifaResponse sincronizarPorTipoHabitacion(@PathVariable String tipoHabitacion) {
        return projTarifaService.sincronizarPorTipoHabitacion(tipoHabitacion);
    }

    @PutMapping("/tipo/{tipoHabitacion}")
    public ProjTarifaResponse update(
            @PathVariable String tipoHabitacion,
            @Valid @RequestBody ProjTarifaRequest request) {
        return projTarifaService.update(tipoHabitacion, request);
    }

    @DeleteMapping("/tipo/{tipoHabitacion}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByTipoHabitacion(@PathVariable String tipoHabitacion) {
        projTarifaService.deleteByTipoHabitacion(tipoHabitacion);
    }
}
