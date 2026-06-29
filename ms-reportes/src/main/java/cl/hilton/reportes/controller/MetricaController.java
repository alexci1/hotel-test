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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/metricas")
@RequiredArgsConstructor
public class MetricaController {

    private final MetricaService metricaService;

    private MetricaResponse addLinks(MetricaResponse m) {
        m.add(linkTo(methodOn(MetricaController.class).findById(m.getId())).withSelfRel());
        m.add(linkTo(methodOn(MetricaController.class).update(m.getId(), null)).withRel("update").withTitle("PUT - Actualizar metrica"));
        m.add(linkTo(methodOn(MetricaController.class).deleteById(m.getId())).withRel("delete").withTitle("DELETE - Eliminar metrica"));
        m.add(linkTo(methodOn(MetricaController.class).findAll()).withRel("all").withTitle("GET - Todas las metricas"));
        return m;
    }

    @GetMapping
    public CollectionModel<MetricaResponse> findAll() {
        List<MetricaResponse> list = metricaService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MetricaController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public MetricaResponse findById(@PathVariable Long id) {
        return addLinks(metricaService.findById(id));
    }

    @GetMapping("/reporte/{codigoReporte}")
    public CollectionModel<MetricaResponse> findByReporte(@PathVariable String codigoReporte) {
        List<MetricaResponse> list = metricaService.findByReporte(codigoReporte);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MetricaController.class).findByReporte(codigoReporte)).withSelfRel());
    }

    @GetMapping("/periodo/{periodo}")
    public CollectionModel<MetricaResponse> findByPeriodo(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodo) {
        List<MetricaResponse> list = metricaService.findByPeriodo(periodo);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MetricaController.class).findByPeriodo(periodo)).withSelfRel());
    }

    @GetMapping("/rango")
    public CollectionModel<MetricaResponse> findByRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        List<MetricaResponse> list = metricaService.findByRangoFechas(desde, hasta);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MetricaController.class).findByRangoFechas(desde, hasta)).withSelfRel());
    }

    @GetMapping("/nombre/{nombreMetrica}")
    public CollectionModel<MetricaResponse> findByNombreMetrica(@PathVariable String nombreMetrica) {
        List<MetricaResponse> list = metricaService.findByNombreMetrica(nombreMetrica);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MetricaController.class).findByNombreMetrica(nombreMetrica)).withSelfRel());
    }

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
