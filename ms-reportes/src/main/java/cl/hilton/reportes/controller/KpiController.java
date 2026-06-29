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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/kpis")
@RequiredArgsConstructor
public class KpiController {

    private final KpiService kpiService;

    private KpiResponse addLinks(KpiResponse k) {
        k.add(linkTo(methodOn(KpiController.class).findById(k.getId())).withSelfRel());
        k.add(linkTo(methodOn(KpiController.class).update(k.getId(), null)).withRel("update").withTitle("PUT - Actualizar KPI"));
        k.add(linkTo(methodOn(KpiController.class).deleteById(k.getId())).withRel("delete").withTitle("DELETE - Eliminar KPI"));
        k.add(linkTo(methodOn(KpiController.class).findAll()).withRel("all").withTitle("GET - Todos los KPIs"));
        return k;
    }

    @GetMapping
    public CollectionModel<KpiResponse> findAll() {
        List<KpiResponse> list = kpiService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(KpiController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public KpiResponse findById(@PathVariable Long id) {
        return addLinks(kpiService.findById(id));
    }

    @GetMapping("/nombre/{nombre}")
    public KpiResponse findByNombre(@PathVariable String nombre) {
        return addLinks(kpiService.findByNombre(nombre));
    }

    @GetMapping("/reporte/{codigoReporte}")
    public CollectionModel<KpiResponse> findByReporte(@PathVariable String codigoReporte) {
        List<KpiResponse> list = kpiService.findByReporte(codigoReporte);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(KpiController.class).findByReporte(codigoReporte)).withSelfRel());
    }

    @GetMapping("/buscar")
    public CollectionModel<KpiResponse> findByNombreContaining(@RequestParam String nombre) {
        List<KpiResponse> list = kpiService.findByNombreContaining(nombre);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(KpiController.class).findByNombreContaining(nombre)).withSelfRel());
    }

    @GetMapping("/periodo/{periodo}")
    public CollectionModel<KpiResponse> findByPeriodo(@PathVariable String periodo) {
        List<KpiResponse> list = kpiService.findByPeriodo(periodo);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(KpiController.class).findByPeriodo(periodo)).withSelfRel());
    }

    @GetMapping("/unidad/{unidad}")
    public CollectionModel<KpiResponse> findByUnidad(@PathVariable String unidad) {
        List<KpiResponse> list = kpiService.findByUnidad(unidad);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(KpiController.class).findByUnidad(unidad)).withSelfRel());
    }

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
