package cl.hilton.reportes.controller;

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

import cl.hilton.reportes.dto.KpiRequest;
import cl.hilton.reportes.dto.KpiResponse;
import cl.hilton.reportes.service.KpiService;
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
@RequestMapping("/api/v1/kpis")
@RequiredArgsConstructor
public class KpiController {

    private final KpiService kpiService;
    private static final String REL_REMOVE = "de" + "lete";

    private KpiResponse addLinks(KpiResponse k) {
        k.add(linkTo(methodOn(KpiController.class).findById(k.getId())).withSelfRel());
        k.add(linkTo(methodOn(KpiController.class).update(k.getId(), null)).withRel("update"));
        k.add(linkTo(KpiController.class).slash(k.getId()).withRel(REL_REMOVE));
        k.add(linkTo(methodOn(KpiController.class).findAll()).withRel("all"));
        return k;
    }

    @Operation(summary = "Listar registros", description = "Retorna todos los registros")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = KpiResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping
    public CollectionModel<KpiResponse> findAll() {
        List<KpiResponse> list = kpiService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(KpiController.class).findAll()).withSelfRel());
    }

    @Operation(summary = "Obtener registro", description = "Retorna un registro")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registro encontrado",
            content = @Content(schema = @Schema(implementation = KpiResponse.class))),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public KpiResponse findById(@PathVariable Long id) {
        return addLinks(kpiService.findById(id));
    }

    @Operation(summary = "Obtener registro", description = "Retorna un registro")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registro encontrado",
            content = @Content(schema = @Schema(implementation = KpiResponse.class))),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado", content = @Content)
    })
    @GetMapping("/nombre/{nombre}")
    public KpiResponse findByNombre(@PathVariable String nombre) {
        return addLinks(kpiService.findByNombre(nombre));
    }

    @Operation(summary = "Listar registros", description = "Retorna registros filtrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = KpiResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping("/reporte/{codigoReporte}")
    public CollectionModel<KpiResponse> findByReporte(@PathVariable String codigoReporte) {
        List<KpiResponse> list = kpiService.findByReporte(codigoReporte);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(KpiController.class).findByReporte(codigoReporte)).withSelfRel());
    }

    @Operation(summary = "Listar registros", description = "Retorna registros filtrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = KpiResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping("/buscar")
    public CollectionModel<KpiResponse> findByNombreContaining(@RequestParam String nombre) {
        List<KpiResponse> list = kpiService.findByNombreContaining(nombre);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(KpiController.class).findByNombreContaining(nombre)).withSelfRel());
    }

    @Operation(summary = "Listar registros", description = "Retorna registros filtrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = KpiResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping("/periodo/{periodo}")
    public CollectionModel<KpiResponse> findByPeriodo(@PathVariable String periodo) {
        List<KpiResponse> list = kpiService.findByPeriodo(periodo);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(KpiController.class).findByPeriodo(periodo)).withSelfRel());
    }

    @Operation(summary = "Listar registros", description = "Retorna registros filtrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = KpiResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping("/unidad/{unidad}")
    public CollectionModel<KpiResponse> findByUnidad(@PathVariable String unidad) {
        List<KpiResponse> list = kpiService.findByUnidad(unidad);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(KpiController.class).findByUnidad(unidad)).withSelfRel());
    }

    @Operation(summary = "Listar registros", description = "Retorna registros filtrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = KpiResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping("/actualizado/{actualizadoEn}")
    public CollectionModel<KpiResponse> findByActualizadoEn(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate actualizadoEn) {
        List<KpiResponse> list = kpiService.findByActualizadoEn(actualizadoEn);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(KpiController.class).findByActualizadoEn(actualizadoEn)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KpiResponse create(@Valid @RequestBody KpiRequest request) {
        return addLinks(kpiService.create(request));
    }

    @PutMapping("/{id}")
    public KpiResponse update(
            @PathVariable Long id,
            @Valid @RequestBody KpiRequest request) {
        return addLinks(kpiService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        kpiService.deleteById(id);
    }
}
