package cl.hilton.restaurante.controller;

import java.util.List;

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

import cl.hilton.restaurante.dto.MesaRequest;
import cl.hilton.restaurante.dto.MesaResponse;
import cl.hilton.restaurante.service.MesaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/mesas")
@RequiredArgsConstructor
public class MesaController {

    private final MesaService mesaService;

    private MesaResponse addLinks(MesaResponse m) {
        m.add(linkTo(methodOn(MesaController.class).findById(m.getId())).withSelfRel());
        m.add(linkTo(methodOn(MesaController.class).update(m.getId(), null)).withRel("update").withTitle("PUT - Actualizar mesa"));
        m.add(linkTo(methodOn(MesaController.class).deleteById(m.getId())).withRel("delete").withTitle("DELETE - Eliminar mesa"));
        m.add(linkTo(methodOn(MesaController.class).cambiarDisponibilidad(m.getId(), null)).withRel("cambiar-disponibilidad").withTitle("PATCH - Cambiar disponibilidad"));
        m.add(linkTo(methodOn(MesaController.class).findAll()).withRel("all").withTitle("GET - Todas las mesas"));
        return m;
    }

    @GetMapping
    public CollectionModel<MesaResponse> findAll() {
        List<MesaResponse> list = mesaService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MesaController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public MesaResponse findById(@PathVariable Long id) {
        return addLinks(mesaService.findById(id));
    }

    @GetMapping("/numero/{numeroMesa}")
    public MesaResponse findByNumeroMesa(@PathVariable String numeroMesa) {
        return addLinks(mesaService.findByNumeroMesa(numeroMesa));
    }

    @GetMapping("/zona/{zona}")
    public CollectionModel<MesaResponse> findByZona(@PathVariable String zona) {
        List<MesaResponse> list = mesaService.findByZona(zona);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MesaController.class).findByZona(zona)).withSelfRel());
    }

    @GetMapping("/disponible/{disponible}")
    public CollectionModel<MesaResponse> findByDisponible(@PathVariable Boolean disponible) {
        List<MesaResponse> list = mesaService.findByDisponible(disponible);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MesaController.class).findByDisponible(disponible)).withSelfRel());
    }

    @GetMapping("/capacidad-minima/{capacidad}")
    public CollectionModel<MesaResponse> findByCapacidadMinima(@PathVariable Integer capacidad) {
        List<MesaResponse> list = mesaService.findByCapacidadMinima(capacidad);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MesaController.class).findByCapacidadMinima(capacidad)).withSelfRel());
    }

    @GetMapping("/zona/{zona}/disponible/{disponible}")
    public CollectionModel<MesaResponse> findByZonaAndDisponible(
            @PathVariable String zona,
            @PathVariable Boolean disponible) {
        List<MesaResponse> list = mesaService.findByZonaAndDisponible(zona, disponible);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(MesaController.class).findByZonaAndDisponible(zona, disponible)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MesaResponse create(@Valid @RequestBody MesaRequest request) {
        return addLinks(mesaService.create(request));
    }

    @PutMapping("/{id}")
    public MesaResponse update(
            @PathVariable Long id,
            @Valid @RequestBody MesaRequest request) {
        return addLinks(mesaService.update(id, request));
    }

    @PatchMapping("/{id}/disponibilidad")
    public MesaResponse cambiarDisponibilidad(
            @PathVariable Long id,
            @RequestParam Boolean disponible) {
        return addLinks(mesaService.cambiarDisponibilidad(id, disponible));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        mesaService.deleteById(id);
    }
}
