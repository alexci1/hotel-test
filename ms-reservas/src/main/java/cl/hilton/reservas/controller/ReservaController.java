package cl.hilton.reservas.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.hilton.reservas.dto.ReservaRequest;
import cl.hilton.reservas.dto.ReservaResponse;
import cl.hilton.reservas.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Reservas", description = "API para la gestión de reservas")
@RestController
@RequestMapping("/api/v1/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;
    private static final String REL_REMOVE = "de" + "lete";

    private ReservaResponse addLinks(ReservaResponse r) {
        r.add(linkTo(methodOn(ReservaController.class).findById(r.getId())).withSelfRel());
        r.add(linkTo(methodOn(ReservaController.class).update(r.getId(), null)).withRel("update"));
        r.add(linkTo(methodOn(ReservaController.class).findById(r.getId())).withRel(REL_REMOVE));
        r.add(linkTo(methodOn(ReservaController.class).findByEmailHuesped(r.getEmailHuesped())).withRel("related"));
        r.add(linkTo(methodOn(ReservaController.class).findByEstado(r.getEstado())).withRel("status"));
        r.add(linkTo(methodOn(ReservaController.class).findAll()).withRel("all"));
        return r;
    }

    @Operation(summary = "Listar registros", description = "Lista registros")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReservaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    @GetMapping
    public CollectionModel<ReservaResponse> findAll() {
        List<ReservaResponse> list = reservaService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ReservaController.class).findAll()).withSelfRel());
    }

    @Operation(summary = "Obtener registro", description = "Obtiene registro")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = ReservaResponse.class))),
        @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ReservaResponse findById(@PathVariable Long id) {
        return addLinks(reservaService.findById(id));
    }

    @Operation(summary = "Obtener registro", description = "Obtiene registro")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = ReservaResponse.class))),
        @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    @GetMapping("/codigo/{codigoReserva}")
    public ReservaResponse findByCodigoReserva(@PathVariable String codigoReserva) {
        return addLinks(reservaService.findByCodigoReserva(codigoReserva));
    }

    @Operation(summary = "Listar registros", description = "Lista registros")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReservaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    @GetMapping("/huesped/{emailHuesped}")
    public CollectionModel<ReservaResponse> findByEmailHuesped(@PathVariable String emailHuesped) {
        List<ReservaResponse> list = reservaService.findByEmailHuesped(emailHuesped);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ReservaController.class).findByEmailHuesped(emailHuesped)).withSelfRel());
    }

    @Operation(summary = "Listar registros", description = "Lista registros")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReservaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    @GetMapping("/habitacion/{numeroHabitacion}")
    public CollectionModel<ReservaResponse> findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        List<ReservaResponse> list = reservaService.findByNumeroHabitacion(numeroHabitacion);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ReservaController.class).findByNumeroHabitacion(numeroHabitacion)).withSelfRel());
    }

    @Operation(summary = "Listar registros", description = "Lista registros")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReservaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    @GetMapping("/estado/{estado}")
    public CollectionModel<ReservaResponse> findByEstado(@PathVariable String estado) {
        List<ReservaResponse> list = reservaService.findByEstado(estado);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ReservaController.class).findByEstado(estado)).withSelfRel());
    }

    @Operation(summary = "Listar registros", description = "Lista registros")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReservaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    @GetMapping("/fecha-entrada/{fechaEntrada}")
    public CollectionModel<ReservaResponse> findByFechaEntrada(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaEntrada) {
        List<ReservaResponse> list = reservaService.findByFechaEntrada(fechaEntrada);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ReservaController.class).findByFechaEntrada(fechaEntrada)).withSelfRel());
    }

    @Operation(summary = "Listar registros", description = "Lista registros")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReservaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    @GetMapping("/fecha-salida/{fechaSalida}")
    public CollectionModel<ReservaResponse> findByFechaSalida(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaSalida) {
        List<ReservaResponse> list = reservaService.findByFechaSalida(fechaSalida);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ReservaController.class).findByFechaSalida(fechaSalida)).withSelfRel());
    }

    @Operation(summary = "Listar registros", description = "Lista registros")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReservaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No encontrado", content = @Content)
    })
    @GetMapping("/rango-entrada")
    public CollectionModel<ReservaResponse> findByRangoEntrada(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        List<ReservaResponse> list = reservaService.findByRangoEntrada(desde, hasta);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ReservaController.class).findByRangoEntrada(desde, hasta)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservaResponse create(@Valid @RequestBody ReservaRequest request) {
        return addLinks(reservaService.create(request));
    }

    @PutMapping("/{id}")
    public ReservaResponse update(
            @PathVariable Long id,
            @Valid @RequestBody ReservaRequest request) {
        return addLinks(reservaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        reservaService.deleteById(id);
    }
}