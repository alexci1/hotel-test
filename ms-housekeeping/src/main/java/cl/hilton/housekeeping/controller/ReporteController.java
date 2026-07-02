package cl.hilton.housekeeping.controller;

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

import cl.hilton.housekeeping.dto.ReporteRequest;
import cl.hilton.housekeeping.dto.ReporteResponse;
import cl.hilton.housekeeping.service.ReporteService;
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
@RequestMapping("/api/v1/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    private ReporteResponse addLinks(ReporteResponse r) {
        r.add(linkTo(methodOn(ReporteController.class).findById(r.getId())).withSelfRel());
        r.add(linkTo(methodOn(ReporteController.class).update(r.getId(), null)).withRel("update"));
        r.add(linkTo(methodOn(ReporteController.class).findById(r.getId())).withRel("delete"));
        r.add(linkTo(methodOn(ReporteController.class).findAll()).withRel("all"));
        return r;
    }

    @Operation(summary = "Listar reportes", description = "Retorna todos los reportes de housekeeping registrados en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reportes encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReporteResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron reportes", content = @Content)
    })
    @GetMapping
    public CollectionModel<ReporteResponse> findAll() {
        List<ReporteResponse> list = reporteService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ReporteController.class).findAll()).withSelfRel());
    }

    @Operation(summary = "Obtener reporte por ID", description = "Retorna un reporte según su identificador único")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reporte encontrado",
            content = @Content(schema = @Schema(implementation = ReporteResponse.class))),
        @ApiResponse(responseCode = "404", description = "Reporte no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ReporteResponse findById(@PathVariable Long id) {
        return addLinks(reporteService.findById(id));
    }

    @Operation(summary = "Obtener reporte por asignación", description = "Retorna un reporte según el ID de asignación")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reporte encontrado",
            content = @Content(schema = @Schema(implementation = ReporteResponse.class))),
        @ApiResponse(responseCode = "404", description = "Reporte no encontrado para la asignación indicada", content = @Content)
    })
    @GetMapping("/asignacion/{asignacionId}")
    public ReporteResponse findByAsignacionId(@PathVariable Long asignacionId) {
        return addLinks(reporteService.findByAsignacionId(asignacionId));
    }

    @Operation(summary = "Listar reportes por estado de aprobación", description = "Retorna los reportes filtrados por si están aprobados o no")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reportes encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReporteResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron reportes con el estado indicado", content = @Content)
    })
    @GetMapping("/aprobado/{aprobado}")
    public CollectionModel<ReporteResponse> findByAprobado(@PathVariable Boolean aprobado) {
        List<ReporteResponse> list = reporteService.findByAprobado(aprobado);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ReporteController.class).findByAprobado(aprobado)).withSelfRel());
    }

    @Operation(summary = "Listar reportes por inspector", description = "Retorna los reportes asociados a un inspector")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reportes encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ReporteResponse.class)))),
        @ApiResponse(responseCode = "404", description = "No se encontraron reportes para el inspector indicado", content = @Content)
    })
    @GetMapping("/inspector/{inspector}")
    public CollectionModel<ReporteResponse> findByInspector(@PathVariable String inspector) {
        List<ReporteResponse> list = reporteService.findByInspector(inspector);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ReporteController.class).findByInspector(inspector)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReporteResponse create(@Valid @RequestBody ReporteRequest request) {
        return addLinks(reporteService.create(request));
    }

    @PutMapping("/{id}")
    public ReporteResponse update(@PathVariable Long id, @Valid @RequestBody ReporteRequest request) {
        return addLinks(reporteService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        reporteService.deleteById(id);
    }
}
