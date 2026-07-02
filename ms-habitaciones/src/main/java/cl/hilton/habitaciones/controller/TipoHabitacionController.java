package cl.hilton.habitaciones.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.hilton.habitaciones.dto.TipoHabitacionRequest;
import cl.hilton.habitaciones.dto.TipoHabitacionResponse;
import cl.hilton.habitaciones.service.TipoHabitacionService;
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
@RequestMapping("/api/v1/tipos-habitacion")
@RequiredArgsConstructor
public class TipoHabitacionController {

    private final TipoHabitacionService tipoHabitacionService;

    private TipoHabitacionResponse addLinks(TipoHabitacionResponse t) {
        t.add(linkTo(methodOn(TipoHabitacionController.class).findById(t.getId())).withSelfRel());
        t.add(linkTo(methodOn(TipoHabitacionController.class).update(t.getId(), null)).withRel("update"));
        t.add(linkTo(methodOn(TipoHabitacionController.class).findById(t.getId())).withRel("delete"));
        t.add(linkTo(methodOn(TipoHabitacionController.class).findAll()).withRel("all"));
        return t;
    }

    @Operation(summary = "Listar tipos de habitación", description = "Retorna todos los tipos de habitación registrados en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipos de habitación encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TipoHabitacionResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron tipos de habitación", content = @Content)
    })
    @GetMapping
    public CollectionModel<TipoHabitacionResponse> findAll() {
        List<TipoHabitacionResponse> list = tipoHabitacionService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(TipoHabitacionController.class).findAll()).withSelfRel());
    }

    @Operation(summary = "Obtener tipo de habitación por ID", description = "Retorna un tipo de habitación según su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipo de habitación encontrado",
            content = @Content(schema = @Schema(implementation = TipoHabitacionResponse.class))),
        @ApiResponse(responseCode = "404", description = "Tipo de habitación no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public TipoHabitacionResponse findById(@PathVariable Long id) {
        return addLinks(tipoHabitacionService.findById(id));
    }

    @Operation(summary = "Obtener tipo de habitación por código", description = "Retorna un tipo de habitación según su código único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipo de habitación encontrado",
            content = @Content(schema = @Schema(implementation = TipoHabitacionResponse.class))),
        @ApiResponse(responseCode = "404", description = "Tipo de habitación no encontrado", content = @Content)
    })
    @GetMapping("/codigo/{codigo}")
    public TipoHabitacionResponse findByCodigo(@PathVariable String codigo) {
        return addLinks(tipoHabitacionService.findByCodigo(codigo));
    }

    @Operation(summary = "Listar tipos de habitación por estado activo", description = "Retorna los tipos de habitación filtrados por su estado activo o inactivo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipos de habitación encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TipoHabitacionResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron tipos de habitación con el estado indicado", content = @Content)
    })
    @GetMapping("/activos/{activo}")
    public CollectionModel<TipoHabitacionResponse> findByActivo(@PathVariable Boolean activo) {
        List<TipoHabitacionResponse> list = tipoHabitacionService.findByActivo(activo);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(TipoHabitacionController.class).findByActivo(activo)).withSelfRel());
    }

    @Operation(summary = "Listar tipos de habitación por capacidad máxima", description = "Retorna los tipos de habitación asociados a una capacidad máxima exacta")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipos de habitación encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TipoHabitacionResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron tipos de habitación con la capacidad indicada", content = @Content)
    })
    @GetMapping("/capacidad/{capacidadMax}")
    public CollectionModel<TipoHabitacionResponse> findByCapacidadMax(@PathVariable Integer capacidadMax) {
        List<TipoHabitacionResponse> list = tipoHabitacionService.findByCapacidadMax(capacidadMax);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(TipoHabitacionController.class).findByCapacidadMax(capacidadMax)).withSelfRel());
    }

    @Operation(summary = "Listar tipos de habitación por capacidad mínima", description = "Retorna los tipos de habitación con capacidad mayor o igual a la indicada")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipos de habitación encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TipoHabitacionResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron tipos de habitación con la capacidad mínima indicada", content = @Content)
    })
    @GetMapping("/capacidad-minima/{capacidadMax}")
    public CollectionModel<TipoHabitacionResponse> findByCapacidadMinima(@PathVariable Integer capacidadMax) {
        List<TipoHabitacionResponse> list = tipoHabitacionService.findByCapacidadMinima(capacidadMax);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(TipoHabitacionController.class).findByCapacidadMinima(capacidadMax)).withSelfRel());
    }

    @Operation(summary = "Buscar tipos de habitación por descripción", description = "Retorna los tipos de habitación que coinciden con la descripción indicada")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipos de habitación encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = TipoHabitacionResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron tipos de habitación con la descripción indicada", content = @Content)
    })
    @GetMapping("/buscar")
    public CollectionModel<TipoHabitacionResponse> findByDescripcion(@RequestParam String descripcion) {
        List<TipoHabitacionResponse> list = tipoHabitacionService.findByDescripcion(descripcion);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(TipoHabitacionController.class).findByDescripcion(descripcion)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TipoHabitacionResponse create(@Valid @RequestBody TipoHabitacionRequest request) {
        return addLinks(tipoHabitacionService.create(request));
    }

    @PutMapping("/{id}")
    public TipoHabitacionResponse update(
            @PathVariable Long id,
            @Valid @RequestBody TipoHabitacionRequest request) {
        return addLinks(tipoHabitacionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        tipoHabitacionService.deleteById(id);
    }
}
