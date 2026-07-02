package cl.hilton.pagos.controller;

import java.time.LocalDate;
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

import cl.hilton.pagos.dto.ProjReservaRequest;
import cl.hilton.pagos.dto.ProjReservaResponse;
import cl.hilton.pagos.service.ProjReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor
public class ProjReservaController {

    private final ProjReservaService reservaService;

    @Operation(summary = "Listar registros", description = "Retorna todos los registros")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProjReservaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping
    public List<ProjReservaResponse> findAll() {
        return reservaService.findAll();
    }

    @Operation(summary = "Obtener registro por codigo", description = "Retorna un registro por codigo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registro encontrado",
            content = @Content(schema = @Schema(implementation = ProjReservaResponse.class))),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado", content = @Content)
    })
    @GetMapping("/codigo/{codigoReserva}")
    public ProjReservaResponse findByCodigoReserva(@PathVariable String codigoReserva) {
        return reservaService.findByCodigoReserva(codigoReserva);
    }

    @Operation(summary = "Listar registros por huesped", description = "Retorna registros por huesped")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProjReservaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping("/huesped/{emailHuesped}")
    public List<ProjReservaResponse> findByEmailHuesped(@PathVariable String emailHuesped) {
        return reservaService.findByEmailHuesped(emailHuesped);
    }

    @Operation(summary = "Listar registros por habitacion", description = "Retorna registros por habitacion")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProjReservaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping("/habitacion/{numeroHabitacion}")
    public List<ProjReservaResponse> findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        return reservaService.findByNumeroHabitacion(numeroHabitacion);
    }

    @Operation(summary = "Listar registros por entrada", description = "Retorna registros por entrada")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProjReservaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping("/fecha-entrada/{fechaEntrada}")
    public List<ProjReservaResponse> findByFechaEntrada(@PathVariable LocalDate fechaEntrada) {
        return reservaService.findByFechaEntrada(fechaEntrada);
    }

    @Operation(summary = "Listar registros por salida", description = "Retorna registros por salida")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProjReservaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping("/fecha-salida/{fechaSalida}")
    public List<ProjReservaResponse> findByFechaSalida(@PathVariable LocalDate fechaSalida) {
        return reservaService.findByFechaSalida(fechaSalida);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjReservaResponse create(@Valid @RequestBody ProjReservaRequest request) {
        return reservaService.create(request);
    }

    @PostMapping("/sincronizar/codigo/{codigoReserva}")
    public ProjReservaResponse sincronizarPorCodigoReserva(@PathVariable String codigoReserva) {
        return reservaService.sincronizarPorCodigoReserva(codigoReserva);
    }

    @PutMapping("/codigo/{codigoReserva}")
    public ProjReservaResponse update(
            @PathVariable String codigoReserva,
            @Valid @RequestBody ProjReservaRequest request) {
        return reservaService.update(codigoReserva, request);
    }

    @DeleteMapping("/codigo/{codigoReserva}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByCodigoReserva(@PathVariable String codigoReserva) {
        reservaService.deleteByCodigoReserva(codigoReserva);
    }
}
