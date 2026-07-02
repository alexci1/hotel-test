package cl.hilton.notificaciones.controller;

import java.time.LocalDate;
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

import cl.hilton.notificaciones.dto.NotificacionRequest;
import cl.hilton.notificaciones.dto.NotificacionResponse;
import cl.hilton.notificaciones.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;

    private NotificacionResponse addLinks(NotificacionResponse n) {
        n.add(linkTo(methodOn(NotificacionController.class).findById(n.getId())).withSelfRel());
        n.add(linkTo(methodOn(NotificacionController.class).update(n.getId(), null)).withRel("update"));
        n.add(linkTo(methodOn(NotificacionController.class).findById(n.getId())).withRel("delete"));
        n.add(linkTo(methodOn(NotificacionController.class).findByEmailHuesped(n.getEmailHuesped())).withRel("notificaciones-huesped"));
        n.add(linkTo(methodOn(NotificacionController.class).findAll()).withRel("all"));
        return n;
    }

    @Operation(summary = "Listar notificaciones", description = "Retorna todos los registros")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = NotificacionResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping
    public CollectionModel<NotificacionResponse> findAll() {
        List<NotificacionResponse> list = notificacionService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(NotificacionController.class).findAll()).withSelfRel());
    }

    @Operation(summary = "Obtener notificacion por ID", description = "Retorna un registro por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registro encontrado",
            content = @Content(schema = @Schema(implementation = NotificacionResponse.class))),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public NotificacionResponse findById(@PathVariable Long id) {
        return addLinks(notificacionService.findById(id));
    }

    @Operation(summary = "Listar notificaciones por evento", description = "Retorna registros por evento")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = NotificacionResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping("/evento/{eventoOrigen}")
    public CollectionModel<NotificacionResponse> findByEventoOrigen(@PathVariable String eventoOrigen) {
        List<NotificacionResponse> list = notificacionService.findByEventoOrigen(eventoOrigen);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(NotificacionController.class).findByEventoOrigen(eventoOrigen)).withSelfRel());
    }

    @Operation(summary = "Listar notificaciones por plantilla", description = "Retorna registros por plantilla")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = NotificacionResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping("/plantilla/{codigoPlantilla}")
    public CollectionModel<NotificacionResponse> findByCodigoPlantilla(@PathVariable String codigoPlantilla) {
        List<NotificacionResponse> list = notificacionService.findByCodigoPlantilla(codigoPlantilla);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(NotificacionController.class).findByCodigoPlantilla(codigoPlantilla)).withSelfRel());
    }

    @Operation(summary = "Listar notificaciones por huesped", description = "Retorna registros por huesped")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = NotificacionResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping("/huesped/{emailHuesped}")
    public CollectionModel<NotificacionResponse> findByEmailHuesped(@PathVariable String emailHuesped) {
        List<NotificacionResponse> list = notificacionService.findByEmailHuesped(emailHuesped);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(NotificacionController.class).findByEmailHuesped(emailHuesped)).withSelfRel());
    }

    @Operation(summary = "Listar notificaciones por fecha", description = "Retorna registros por fecha")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = NotificacionResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping("/fecha/{creadoEn}")
    public CollectionModel<NotificacionResponse> findByCreadoEn(@PathVariable LocalDate creadoEn) {
        List<NotificacionResponse> list = notificacionService.findByCreadoEn(creadoEn);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(NotificacionController.class).findByCreadoEn(creadoEn)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotificacionResponse create(@Valid @RequestBody NotificacionRequest request) {
        return addLinks(notificacionService.create(request));
    }

    @PutMapping("/{id}")
    public NotificacionResponse update(
            @PathVariable Long id,
            @Valid @RequestBody NotificacionRequest request) {
        return addLinks(notificacionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        notificacionService.deleteById(id);
    }
}
