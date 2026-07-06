package cl.hilton.checkin.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
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

import cl.hilton.checkin.dto.CheckinRequest;
import cl.hilton.checkin.dto.CheckinResponse;
import cl.hilton.checkin.service.CheckinService;
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

@Tag(name = "Check-in", description = "API para la gestión de check-in y check-out")
@RestController
@RequestMapping("/api/v1/checkins")
@RequiredArgsConstructor
public class CheckinController {

    private final CheckinService checkinService;

    private CheckinResponse addLinks(CheckinResponse c) {
        c.add(linkTo(methodOn(CheckinController.class).findById(c.getId())).withSelfRel());
        c.add(linkTo(methodOn(CheckinController.class).update(c.getId(), null)).withRel("update"));
        c.add(linkTo(CheckinController.class).slash(c.getId()).withRel("delete"));
        c.add(linkTo(methodOn(CheckinController.class).findAll()).withRel("all"));
        return c;
    }

    @Operation(summary = "Listar checkins", description = "Retorna todos los checkins registrados en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Checkins encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CheckinResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron checkins", content = @Content)
    })
    @GetMapping
    public CollectionModel<CheckinResponse> findAll() {
        List<CheckinResponse> list = checkinService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(CheckinController.class).findAll()).withSelfRel());
    }

    @Operation(summary = "Obtener checkin por ID", description = "Retorna un checkin según su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Checkin encontrado",
            content = @Content(schema = @Schema(implementation = CheckinResponse.class))),
        @ApiResponse(responseCode = "404", description = "Checkin no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public CheckinResponse findById(@PathVariable Long id) {
        return addLinks(checkinService.findById(id));
    }

    @Operation(summary = "Obtener checkin por reserva", description = "Retorna un checkin según el código de reserva")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Checkin encontrado",
            content = @Content(schema = @Schema(implementation = CheckinResponse.class))),
        @ApiResponse(responseCode = "404", description = "Checkin no encontrado para la reserva indicada", content = @Content)
    })
    @GetMapping("/reserva/{codigoReserva}")
    public CheckinResponse findByCodigoReserva(@PathVariable String codigoReserva) {
        return addLinks(checkinService.findByCodigoReserva(codigoReserva));
    }

    @Operation(summary = "Listar checkins por huésped", description = "Retorna los checkins asociados al email de un huésped")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Checkins encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CheckinResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron checkins para el huésped indicado", content = @Content)
    })
    @GetMapping("/huesped/{emailHuesped}")
    public CollectionModel<CheckinResponse> findByEmailHuesped(@PathVariable String emailHuesped) {
        List<CheckinResponse> list = checkinService.findByEmailHuesped(emailHuesped);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(CheckinController.class).findByEmailHuesped(emailHuesped)).withSelfRel());
    }

    @Operation(summary = "Listar checkins por habitación", description = "Retorna los checkins asociados a un número de habitación")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Checkins encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CheckinResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron checkins para la habitación indicada", content = @Content)
    })
    @GetMapping("/habitacion/{numeroHabitacion}")
    public CollectionModel<CheckinResponse> findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        List<CheckinResponse> list = checkinService.findByNumeroHabitacion(numeroHabitacion);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(CheckinController.class).findByNumeroHabitacion(numeroHabitacion)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CheckinResponse create(@Valid @RequestBody CheckinRequest request) {
        return addLinks(checkinService.create(request));
    }

    @PutMapping("/{id}")
    public CheckinResponse update(@PathVariable Long id, @Valid @RequestBody CheckinRequest request) {
        return addLinks(checkinService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        checkinService.deleteById(id);
    }
}