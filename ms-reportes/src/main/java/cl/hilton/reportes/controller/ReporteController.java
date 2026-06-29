package cl.hilton.reportes.controller;

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

import cl.hilton.reportes.dto.ReporteRequest;
import cl.hilton.reportes.dto.ReporteResponse;
import cl.hilton.reportes.service.ReporteService;
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
        r.add(linkTo(methodOn(ReporteController.class).update(r.getId(), null)).withRel("update").withTitle("PUT - Actualizar reporte"));
        r.add(linkTo(methodOn(ReporteController.class).deleteById(r.getId())).withRel("delete").withTitle("DELETE - Eliminar reporte"));
        r.add(linkTo(methodOn(ReporteController.class).findAll()).withRel("all").withTitle("GET - Todos los reportes"));
        return r;
    }

    @GetMapping
    public CollectionModel<ReporteResponse> findAll() {
        List<ReporteResponse> list = reporteService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ReporteController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ReporteResponse findById(@PathVariable Long id) {
        return addLinks(reporteService.findById(id));
    }

    @GetMapping("/codigo/{codigo}")
    public ReporteResponse findByCodigo(@PathVariable String codigo) {
        return addLinks(reporteService.findByCodigo(codigo));
    }

    @GetMapping("/tipo/{tipo}")
    public CollectionModel<ReporteResponse> findByTipo(@PathVariable String tipo) {
        List<ReporteResponse> list = reporteService.findByTipo(tipo);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ReporteController.class).findByTipo(tipo)).withSelfRel());
    }

    @GetMapping("/frecuencia/{frecuencia}")
    public CollectionModel<ReporteResponse> findByFrecuencia(@PathVariable String frecuencia) {
        List<ReporteResponse> list = reporteService.findByFrecuencia(frecuencia);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ReporteController.class).findByFrecuencia(frecuencia)).withSelfRel());
    }

    @GetMapping("/activo/{activo}")
    public CollectionModel<ReporteResponse> findByActivo(@PathVariable Boolean activo) {
        List<ReporteResponse> list = reporteService.findByActivo(activo);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ReporteController.class).findByActivo(activo)).withSelfRel());
    }

    @GetMapping("/buscar")
    public CollectionModel<ReporteResponse> findByNombre(@RequestParam String nombre) {
        List<ReporteResponse> list = reporteService.findByNombre(nombre);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ReporteController.class).findByNombre(nombre)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReporteResponse create(@Valid @RequestBody ReporteRequest request) {
        return addLinks(reporteService.create(request));
    }

    @PutMapping("/{id}")
    public ReporteResponse update(
            @PathVariable Long id,
            @Valid @RequestBody ReporteRequest request) {
        return addLinks(reporteService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        reporteService.deleteById(id);
    }
}
