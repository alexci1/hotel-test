package cl.hilton.notificaciones.controller;

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

import cl.hilton.notificaciones.dto.PlantillaRequest;
import cl.hilton.notificaciones.dto.PlantillaResponse;
import cl.hilton.notificaciones.service.PlantillaService;
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
@RequestMapping("/api/v1/plantillas")
@RequiredArgsConstructor
public class PlantillaController {

    private final PlantillaService plantillaService;

    private PlantillaResponse addLinks(PlantillaResponse p) {
        p.add(linkTo(methodOn(PlantillaController.class).findById(p.getId())).withSelfRel());
        p.add(linkTo(methodOn(PlantillaController.class).update(p.getId(), null)).withRel("update"));
        p.add(linkTo(methodOn(PlantillaController.class).findById(p.getId())).withRel("delete"));
        p.add(linkTo(methodOn(PlantillaController.class).findAll()).withRel("all"));
        return p;
    }

    @Operation(summary = "Listar plantillas", description = "Retorna todas las plantillas registradas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Plantillas encontradas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlantillaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron plantillas", content = @Content)
    })
    @GetMapping
    public CollectionModel<PlantillaResponse> findAll() {
        List<PlantillaResponse> list = plantillaService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(PlantillaController.class).findAll()).withSelfRel());
    }

    @Operation(summary = "Obtener plantilla por ID", description = "Retorna una plantilla segun su identificador unico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Plantilla encontrada",
            content = @Content(schema = @Schema(implementation = PlantillaResponse.class))),
        @ApiResponse(responseCode = "404", description = "Plantilla no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public PlantillaResponse findById(@PathVariable Long id) {
        return addLinks(plantillaService.findById(id));
    }

    @Operation(summary = "Obtener plantilla por codigo", description = "Retorna una plantilla segun su codigo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Plantilla encontrada",
            content = @Content(schema = @Schema(implementation = PlantillaResponse.class))),
        @ApiResponse(responseCode = "404", description = "Plantilla no encontrada", content = @Content)
    })
    @GetMapping("/codigo/{codigo}")
    public PlantillaResponse findByCodigo(@PathVariable String codigo) {
        return addLinks(plantillaService.findByCodigo(codigo));
    }

    @Operation(summary = "Listar plantillas por canal", description = "Retorna plantillas filtradas por canal")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Plantillas encontradas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlantillaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron plantillas", content = @Content)
    })
    @GetMapping("/canal/{canal}")
    public CollectionModel<PlantillaResponse> findByCanal(@PathVariable String canal) {
        List<PlantillaResponse> list = plantillaService.findByCanal(canal);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(PlantillaController.class).findByCanal(canal)).withSelfRel());
    }

    @Operation(summary = "Listar plantillas por estado activo", description = "Retorna plantillas filtradas por estado activo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Plantillas encontradas",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlantillaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron plantillas", content = @Content)
    })
    @GetMapping("/activa/{activa}")
    public CollectionModel<PlantillaResponse> findByActiva(@PathVariable Boolean activa) {
        List<PlantillaResponse> list = plantillaService.findByActiva(activa);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(PlantillaController.class).findByActiva(activa)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlantillaResponse create(@Valid @RequestBody PlantillaRequest request) {
        return addLinks(plantillaService.create(request));
    }

    @PutMapping("/{id}")
    public PlantillaResponse update(
            @PathVariable Long id,
            @Valid @RequestBody PlantillaRequest request) {
        return addLinks(plantillaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        plantillaService.deleteById(id);
    }
}
