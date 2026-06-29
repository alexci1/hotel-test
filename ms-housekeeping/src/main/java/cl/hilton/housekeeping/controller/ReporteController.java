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

    @GetMapping("/asignacion/{asignacionId}")
    public ReporteResponse findByAsignacionId(@PathVariable Long asignacionId) {
        return addLinks(reporteService.findByAsignacionId(asignacionId));
    }

    @GetMapping("/aprobado/{aprobado}")
    public CollectionModel<ReporteResponse> findByAprobado(@PathVariable Boolean aprobado) {
        List<ReporteResponse> list = reporteService.findByAprobado(aprobado);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(ReporteController.class).findByAprobado(aprobado)).withSelfRel());
    }

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
