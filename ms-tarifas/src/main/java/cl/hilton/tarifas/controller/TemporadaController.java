package cl.hilton.tarifas.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
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

import cl.hilton.tarifas.dto.TemporadaRequest;
import cl.hilton.tarifas.dto.TemporadaResponse;
import cl.hilton.tarifas.service.TemporadaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/temporadas")
@RequiredArgsConstructor
public class TemporadaController {

    private final TemporadaService temporadaService;

    private TemporadaResponse addLinks(TemporadaResponse t) {
        t.add(linkTo(methodOn(TemporadaController.class).findById(t.getId())).withSelfRel());
        t.add(linkTo(methodOn(TemporadaController.class).update(t.getId(), null)).withRel("update").withTitle("PUT - Actualizar temporada"));
        t.add(linkTo(methodOn(TemporadaController.class).deleteById(t.getId())).withRel("delete").withTitle("DELETE - Eliminar temporada"));
        t.add(linkTo(methodOn(TemporadaController.class).findAll()).withRel("all").withTitle("GET - Todas las temporadas"));
        return t;
    }

    @GetMapping
    public CollectionModel<TemporadaResponse> findAll() {
        List<TemporadaResponse> list = temporadaService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(TemporadaController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public TemporadaResponse findById(@PathVariable Long id) {
        return addLinks(temporadaService.findById(id));
    }

    @GetMapping("/codigo/{codigo}")
    public TemporadaResponse findByCodigo(@PathVariable String codigo) {
        return addLinks(temporadaService.findByCodigo(codigo));
    }

    @GetMapping("/buscar")
    public CollectionModel<TemporadaResponse> findByNombre(@RequestParam String nombre) {
        List<TemporadaResponse> list = temporadaService.findByNombre(nombre);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(TemporadaController.class).findByNombre(nombre)).withSelfRel());
    }

    @GetMapping("/inicio-antes-de/{fechaInicio}")
    public CollectionModel<TemporadaResponse> findByFechaInicioBefore(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio) {
        List<TemporadaResponse> list = temporadaService.findByFechaInicioBefore(fechaInicio);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(TemporadaController.class).findByFechaInicioBefore(fechaInicio)).withSelfRel());
    }

    @GetMapping("/fin-despues-de/{fechaFin}")
    public CollectionModel<TemporadaResponse> findByFechaFinAfter(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        List<TemporadaResponse> list = temporadaService.findByFechaFinAfter(fechaFin);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(TemporadaController.class).findByFechaFinAfter(fechaFin)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TemporadaResponse create(@Valid @RequestBody TemporadaRequest request) {
        return addLinks(temporadaService.create(request));
    }

    @PutMapping("/{id}")
    public TemporadaResponse update(@PathVariable Long id, @Valid @RequestBody TemporadaRequest request) {
        return addLinks(temporadaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        temporadaService.deleteById(id);
    }
}
