package cl.hilton.habitaciones.controller;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.hilton.habitaciones.dto.HabitacionRequest;
import cl.hilton.habitaciones.dto.HabitacionResponse;
import cl.hilton.habitaciones.service.HabitacionService;
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

@Tag(name = "Habitaciones", description = "API para la gestión de habitaciones")
@RestController
@RequestMapping("/api/v1/habitaciones")
@RequiredArgsConstructor
public class HabitacionController {

    private final HabitacionService habitacionService;

    private HabitacionResponse addLinks(HabitacionResponse h) {
        h.add(linkTo(methodOn(HabitacionController.class).findById(h.getId())).withSelfRel());
        h.add(linkTo(methodOn(HabitacionController.class).update(h.getId(), null)).withRel("update"));
        h.add(linkTo(methodOn(HabitacionController.class).findById(h.getId())).withRel("delete"));
        h.add(linkTo(methodOn(HabitacionController.class).cambiarActiva(h.getId(), null)).withRel("cambiar-activa"));
        h.add(linkTo(methodOn(HabitacionController.class).findAll()).withRel("all"));
        return h;
    }

    @Operation(summary = "Listar habitaciones", description = "Retorna todas las habitaciones registradas en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Habitaciones encontradas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = HabitacionResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron habitaciones", content = @Content)
    })
    @GetMapping
    public CollectionModel<HabitacionResponse> findAll() {
        List<HabitacionResponse> list = habitacionService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(HabitacionController.class).findAll()).withSelfRel());
    }

    @Operation(summary = "Obtener habitación por ID", description = "Retorna una habitación según su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Habitación encontrada",
            content = @Content(schema = @Schema(implementation = HabitacionResponse.class))),
        @ApiResponse(responseCode = "404", description = "Habitación no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public HabitacionResponse findById(@PathVariable Long id) {
        return addLinks(habitacionService.findById(id));
    }

    @Operation(summary = "Obtener habitación por número", description = "Retorna una habitación según su número de habitación")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Habitación encontrada",
            content = @Content(schema = @Schema(implementation = HabitacionResponse.class))),
        @ApiResponse(responseCode = "404", description = "Habitación no encontrada", content = @Content)
    })
    @GetMapping("/numero/{numeroHabitacion}")
    public HabitacionResponse findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        return addLinks(habitacionService.findByNumeroHabitacion(numeroHabitacion));
    }

    @Operation(summary = "Listar habitaciones por piso", description = "Retorna las habitaciones asociadas a un piso")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Habitaciones encontradas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = HabitacionResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron habitaciones para el piso indicado", content = @Content)
    })
    @GetMapping("/piso/{piso}")
    public CollectionModel<HabitacionResponse> findByPiso(@PathVariable Integer piso) {
        List<HabitacionResponse> list = habitacionService.findByPiso(piso);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(HabitacionController.class).findByPiso(piso)).withSelfRel());
    }

    @Operation(summary = "Listar habitaciones por estado activo", description = "Retorna las habitaciones filtradas por su estado activo o inactivo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Habitaciones encontradas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = HabitacionResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron habitaciones con el estado indicado", content = @Content)
    })
    @GetMapping("/activas/{activa}")
    public CollectionModel<HabitacionResponse> findByActiva(@PathVariable Boolean activa) {
        List<HabitacionResponse> list = habitacionService.findByActiva(activa);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(HabitacionController.class).findByActiva(activa)).withSelfRel());
    }

    @Operation(summary = "Listar habitaciones por tipo", description = "Retorna las habitaciones asociadas a un código de tipo de habitación")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Habitaciones encontradas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = HabitacionResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron habitaciones para el tipo indicado", content = @Content)
    })
    @GetMapping("/tipo/{codigoTipo}")
    public CollectionModel<HabitacionResponse> findByCodigoTipo(@PathVariable String codigoTipo) {
        List<HabitacionResponse> list = habitacionService.findByCodigoTipo(codigoTipo);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(HabitacionController.class).findByCodigoTipo(codigoTipo)).withSelfRel());
    }

    @Operation(summary = "Listar habitaciones por tipo y estado activo", description = "Retorna las habitaciones filtradas por código de tipo y estado activo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Habitaciones encontradas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = HabitacionResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron habitaciones con los filtros indicados", content = @Content)
    })
    @GetMapping("/tipo/{codigoTipo}/activa/{activa}")
    public CollectionModel<HabitacionResponse> findByCodigoTipoAndActiva(
            @PathVariable String codigoTipo,
            @PathVariable Boolean activa) {
        List<HabitacionResponse> list = habitacionService.findByCodigoTipoAndActiva(codigoTipo, activa);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(HabitacionController.class).findByCodigoTipoAndActiva(codigoTipo, activa)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HabitacionResponse create(@Valid @RequestBody HabitacionRequest request) {
        return addLinks(habitacionService.create(request));
    }

    @PutMapping("/{id}")
    public HabitacionResponse update(
            @PathVariable Long id,
            @Valid @RequestBody HabitacionRequest request) {
        return addLinks(habitacionService.update(id, request));
    }

    @PatchMapping("/{id}/activa")
    public HabitacionResponse cambiarActiva(
            @PathVariable Long id,
            @RequestParam Boolean activa) {
        return addLinks(habitacionService.cambiarActiva(id, activa));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        habitacionService.deleteById(id);
    }
}