package cl.hilton.habitaciones.controller;

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

import cl.hilton.habitaciones.dto.EstadoHabitacionRequest;
import cl.hilton.habitaciones.dto.EstadoHabitacionResponse;
import cl.hilton.habitaciones.service.EstadoHabitacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/estados")
@RequiredArgsConstructor
public class EstadoHabitacionController {

    private final EstadoHabitacionService estadoHabitacionService;

    private EstadoHabitacionResponse addLinks(EstadoHabitacionResponse e) {
        e.add(linkTo(methodOn(EstadoHabitacionController.class).findById(e.getId())).withSelfRel());
        e.add(linkTo(methodOn(EstadoHabitacionController.class).update(e.getId(), null)).withRel("update").withTitle("PUT - Actualizar estado"));
        e.add(linkTo(methodOn(EstadoHabitacionController.class).deleteById(e.getId())).withRel("delete").withTitle("DELETE - Eliminar estado"));
        e.add(linkTo(methodOn(EstadoHabitacionController.class).findAll()).withRel("all").withTitle("GET - Todos los estados"));
        return e;
    }

    @GetMapping
    public CollectionModel<EstadoHabitacionResponse> findAll() {
        List<EstadoHabitacionResponse> list = estadoHabitacionService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(EstadoHabitacionController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EstadoHabitacionResponse findById(@PathVariable Long id) {
        return addLinks(estadoHabitacionService.findById(id));
    }

    @GetMapping("/habitacion/{numeroHabitacion}")
    public EstadoHabitacionResponse findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        return addLinks(estadoHabitacionService.findByNumeroHabitacion(numeroHabitacion));
    }

    @GetMapping("/estado/{estado}")
    public CollectionModel<EstadoHabitacionResponse> findByEstado(@PathVariable String estado) {
        List<EstadoHabitacionResponse> list = estadoHabitacionService.findByEstado(estado);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(EstadoHabitacionController.class).findByEstado(estado)).withSelfRel());
    }

    @GetMapping("/actualizado/{actualizadoEn}")
    public CollectionModel<EstadoHabitacionResponse> findByActualizadoEn(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate actualizadoEn) {
        List<EstadoHabitacionResponse> list = estadoHabitacionService.findByActualizadoEn(actualizadoEn);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(EstadoHabitacionController.class).findByActualizadoEn(actualizadoEn)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoHabitacionResponse create(@Valid @RequestBody EstadoHabitacionRequest request) {
        return addLinks(estadoHabitacionService.create(request));
    }

    @PutMapping("/{id}")
    public EstadoHabitacionResponse update(
            @PathVariable Long id,
            @Valid @RequestBody EstadoHabitacionRequest request) {
        return addLinks(estadoHabitacionService.update(id, request));
    }

    @PatchMapping("/habitacion/{numeroHabitacion}/estado")
    public EstadoHabitacionResponse cambiarEstado(
            @PathVariable String numeroHabitacion,
            @RequestParam String estado) {
        return addLinks(estadoHabitacionService.cambiarEstado(numeroHabitacion, estado));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        estadoHabitacionService.deleteById(id);
    }
}
