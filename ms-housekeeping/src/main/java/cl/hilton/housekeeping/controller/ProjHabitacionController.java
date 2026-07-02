package cl.hilton.housekeeping.controller;

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

import cl.hilton.housekeeping.dto.ProjHabitacionRequest;
import cl.hilton.housekeeping.dto.ProjHabitacionResponse;
import cl.hilton.housekeeping.service.ProjHabitacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/habitaciones-proyeccion")
@RequiredArgsConstructor
public class ProjHabitacionController {

    private final ProjHabitacionService habitacionService;

    @Operation(summary = "Listar habitaciones proyectadas", description = "Retorna todas las habitaciones proyectadas registradas en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Habitaciones proyectadas encontradas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProjHabitacionResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron habitaciones proyectadas", content = @Content)
    })
    @GetMapping
    public List<ProjHabitacionResponse> findAll() {
        return habitacionService.findAll();
    }

    @Operation(summary = "Obtener habitación proyectada por número", description = "Retorna una habitación proyectada según su número de habitación")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Habitación proyectada encontrada",
            content = @Content(schema = @Schema(implementation = ProjHabitacionResponse.class))),
        @ApiResponse(responseCode = "404", description = "Habitación proyectada no encontrada", content = @Content)
    })
    @GetMapping("/{numeroHabitacion}")
    public ProjHabitacionResponse findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        return habitacionService.findByNumeroHabitacion(numeroHabitacion);
    }

    @Operation(summary = "Listar habitaciones proyectadas por tipo", description = "Retorna las habitaciones proyectadas asociadas a un tipo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Habitaciones proyectadas encontradas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProjHabitacionResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron habitaciones proyectadas para el tipo indicado", content = @Content)
    })
    @GetMapping("/tipo/{tipo}")
    public List<ProjHabitacionResponse> findByTipo(@PathVariable String tipo) {
        return habitacionService.findByTipo(tipo);
    }

    @Operation(summary = "Listar habitaciones proyectadas por piso", description = "Retorna las habitaciones proyectadas asociadas a un piso")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Habitaciones proyectadas encontradas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProjHabitacionResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron habitaciones proyectadas para el piso indicado", content = @Content)
    })
    @GetMapping("/piso/{piso}")
    public List<ProjHabitacionResponse> findByPiso(@PathVariable Integer piso) {
        return habitacionService.findByPiso(piso);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjHabitacionResponse create(@Valid @RequestBody ProjHabitacionRequest request) {
        return habitacionService.create(request);
    }

    @PutMapping("/{numeroHabitacion}")
    public ProjHabitacionResponse update(@PathVariable String numeroHabitacion, @Valid @RequestBody ProjHabitacionRequest request) {
        return habitacionService.update(numeroHabitacion, request);
    }

    @DeleteMapping("/{numeroHabitacion}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        habitacionService.deleteByNumeroHabitacion(numeroHabitacion);
    }
}
