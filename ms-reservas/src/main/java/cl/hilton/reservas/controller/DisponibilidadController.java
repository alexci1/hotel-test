package cl.hilton.reservas.controller;

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

import cl.hilton.reservas.dto.DisponibilidadRequest;
import cl.hilton.reservas.dto.DisponibilidadResponse;
import cl.hilton.reservas.service.DisponibilidadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/disponibilidades")
@RequiredArgsConstructor
public class DisponibilidadController {

    private final DisponibilidadService disponibilidadService;

    private DisponibilidadResponse addLinks(DisponibilidadResponse d) {
        d.add(linkTo(methodOn(DisponibilidadController.class).findById(d.getId())).withSelfRel());
        d.add(linkTo(methodOn(DisponibilidadController.class).update(d.getId(), null)).withRel("update").withTitle("PUT - Actualizar disponibilidad"));
        d.add(linkTo(methodOn(DisponibilidadController.class).deleteById(d.getId())).withRel("delete").withTitle("DELETE - Eliminar disponibilidad"));
        d.add(linkTo(methodOn(DisponibilidadController.class).findAll()).withRel("all").withTitle("GET - Todas las disponibilidades"));
        return d;
    }

    @GetMapping
    public CollectionModel<DisponibilidadResponse> findAll() {
        List<DisponibilidadResponse> list = disponibilidadService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(DisponibilidadController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public DisponibilidadResponse findById(@PathVariable Long id) {
        return addLinks(disponibilidadService.findById(id));
    }

    @GetMapping("/habitacion/{numeroHabitacion}/fecha/{fecha}")
    public DisponibilidadResponse findByHabitacionAndFecha(@PathVariable String numeroHabitacion, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return addLinks(disponibilidadService.findByHabitacionAndFecha(numeroHabitacion, fecha));
    }

    @GetMapping("/habitacion/{numeroHabitacion}")
    public CollectionModel<DisponibilidadResponse> findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        List<DisponibilidadResponse> list = disponibilidadService.findByNumeroHabitacion(numeroHabitacion);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(DisponibilidadController.class).findByNumeroHabitacion(numeroHabitacion)).withSelfRel());
    }

    @GetMapping("/fecha/{fecha}")
    public CollectionModel<DisponibilidadResponse> findByFecha(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<DisponibilidadResponse> list = disponibilidadService.findByFecha(fecha);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(DisponibilidadController.class).findByFecha(fecha)).withSelfRel());
    }

    @GetMapping("/rango")
    public CollectionModel<DisponibilidadResponse> findByRangoFechas(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        List<DisponibilidadResponse> list = disponibilidadService.findByRangoFechas(desde, hasta);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(DisponibilidadController.class).findByRangoFechas(desde, hasta)).withSelfRel());
    }

    @GetMapping("/disponible/{disponible}")
    public CollectionModel<DisponibilidadResponse> findByDisponible(@PathVariable Boolean disponible) {
        List<DisponibilidadResponse> list = disponibilidadService.findByDisponible(disponible);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(DisponibilidadController.class).findByDisponible(disponible)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DisponibilidadResponse create(@Valid @RequestBody DisponibilidadRequest request) {
        return addLinks(disponibilidadService.create(request));
    }

    @PutMapping("/{id}")
    public DisponibilidadResponse update(@PathVariable Long id, @Valid @RequestBody DisponibilidadRequest request) {
        return addLinks(disponibilidadService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        disponibilidadService.deleteById(id);
    }
}
