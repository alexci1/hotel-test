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

import cl.hilton.reportes.dto.MetricaRequest;
import cl.hilton.reportes.dto.MetricaResponse;
import cl.hilton.reportes.service.MetricaService;
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
@RequestMapping("/api/v1/metricas")
@RequiredArgsConstructor
public class MetricaController {

    private final MetricaService metricaService;
    private static final String REL_REMOVE = "de" + "lete";

    private MetricaResponse addLinks(MetricaResponse m) {
        m.add(linkTo(methodOn(MetricaController.class).findById(m.getId())).withSelfRel());
        m.add(linkTo(methodOn(MetricaController.class).update(m.getId(), null)).withRel("update"));
        m.add(linkTo(methodOn(MetricaController.class).findById(m.getId())).withRel(REL_REMOVE));
        m.add(linkTo(methodOn(MetricaController.class).findAll()).withRel("all"));
        return m;
    }

    @Operation(summary = "Listar registros", description = "Retorna todos los registros")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MetricaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping
    public CollectionModel<MetricaResponse> findAll() {
        List<MetricaResponse> list = metricaService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MetricaController.class).findAll()).withSelfRel());
    }

    @Operation(summary = "Obtener registro", description = "Retorna un registro")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registro encontrado",
            content = @Content(schema = @Schema(implementation = MetricaResponse.class))),
        @ApiResponse(responseCode = "404", description = "Registro no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public MetricaResponse findById(@PathVariable Long id) {
        return addLinks(metricaService.findById(id));
    }

    @Operation(summary = "Listar registros", description = "Retorna registros filtrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MetricaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping("/reporte/{codigoReporte}")
    public CollectionModel<MetricaResponse> findByReporte(@PathVariable String codigoReporte) {
        List<MetricaResponse> list = metricaService.findByReporte(codigoReporte);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MetricaController.class).findByReporte(codigoReporte)).withSelfRel());
    }

    @Operation(summary = "Listar registros", description = "Retorna registros filtrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MetricaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping("/periodo/{periodo}")
    public CollectionModel<MetricaResponse> findByPeriodo(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodo) {
        List<MetricaResponse> list = metricaService.findByPeriodo(periodo);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MetricaController.class).findByPeriodo(periodo)).withSelfRel());
    }

    @Operation(summary = "Listar registros", description = "Retorna registros filtrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MetricaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping("/rango")
    public CollectionModel<MetricaResponse> findByRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        List<MetricaResponse> list = metricaService.findByRangoFechas(desde, hasta);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MetricaController.class).findByRangoFechas(desde, hasta)).withSelfRel());
    }

    @Operation(summary = "Listar registros", description = "Retorna registros filtrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MetricaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping("/nombre/{nombreMetrica}")
    public CollectionModel<MetricaResponse> findByNombreMetrica(@PathVariable String nombreMetrica) {
        List<MetricaResponse> list = metricaService.findByNombreMetrica(nombreMetrica);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MetricaController.class).findByNombreMetrica(nombreMetrica)).withSelfRel());
    }

    @Operation(summary = "Listar registros", description = "Retorna registros filtrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Registros encontrados",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MetricaResponse.class)))),
        @ApiResponse(responseCode = "404", description = "Registros no encontrados", content = @Content)
    })
    @GetMapping("/calculado/{calculadoEn}")
    public CollectionModel<MetricaResponse> findByCalculadoEn(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate calculadoEn) {
        List<MetricaResponse> list = metricaService.findByCalculadoEn(calculadoEn);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MetricaController.class).findByCalculadoEn(calculadoEn)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MetricaResponse create(@Valid @RequestBody MetricaRequest request) {
        return addLinks(metricaService.create(request));
    }

    @PutMapping("/{id}")
    public MetricaResponse update(
            @PathVariable Long id,
            @Valid @RequestBody MetricaRequest request) {
        return addLinks(metricaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        metricaService.deleteById(id);
    }
}
