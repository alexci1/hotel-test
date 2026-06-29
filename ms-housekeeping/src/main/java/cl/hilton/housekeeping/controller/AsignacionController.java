package cl.hilton.housekeeping.controller;

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

import cl.hilton.housekeeping.dto.AsignacionRequest;
import cl.hilton.housekeeping.dto.AsignacionResponse;
import cl.hilton.housekeeping.service.AsignacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/asignaciones")
@RequiredArgsConstructor
public class AsignacionController {

    private final AsignacionService asignacionService;

    private AsignacionResponse addLinks(AsignacionResponse a) {
        a.add(linkTo(methodOn(AsignacionController.class).findById(a.getId())).withSelfRel());
        a.add(linkTo(methodOn(AsignacionController.class).update(a.getId(), null)).withRel("update").withTitle("PUT - Actualizar asignacion"));
        a.add(linkTo(methodOn(AsignacionController.class).deleteById(a.getId())).withRel("delete").withTitle("DELETE - Eliminar asignacion"));
        a.add(linkTo(methodOn(AsignacionController.class).updateEstado(a.getId(), null)).withRel("cambiar-estado").withTitle("PATCH - Cambiar estado"));
        a.add(linkTo(methodOn(AsignacionController.class).findAll()).withRel("all").withTitle("GET - Todas las asignaciones"));
        return a;
    }

    @GetMapping
    public CollectionModel<AsignacionResponse> findAll() {
        List<AsignacionResponse> list = asignacionService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(AsignacionController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public AsignacionResponse findById(@PathVariable Long id) {
        return addLinks(asignacionService.findById(id));
    }

    @GetMapping("/habitacion/{numeroHabitacion}")
    public CollectionModel<AsignacionResponse> findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        List<AsignacionResponse> list = asignacionService.findByNumeroHabitacion(numeroHabitacion);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(AsignacionController.class).findByNumeroHabitacion(numeroHabitacion)).withSelfRel());
    }

    @GetMapping("/tarea/{codigoTarea}")
    public CollectionModel<AsignacionResponse> findByCodigoTarea(@PathVariable String codigoTarea) {
        List<AsignacionResponse> list = asignacionService.findByCodigoTarea(codigoTarea);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(AsignacionController.class).findByCodigoTarea(codigoTarea)).withSelfRel());
    }

    @GetMapping("/camarero/{emailCamarero}")
    public CollectionModel<AsignacionResponse> findByEmailCamarero(@PathVariable String emailCamarero) {
        List<AsignacionResponse> list = asignacionService.findByEmailCamarero(emailCamarero);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(AsignacionController.class).findByEmailCamarero(emailCamarero)).withSelfRel());
    }

    @GetMapping("/fecha/{fechaProgramada}")
    public CollectionModel<AsignacionResponse> findByFechaProgramada(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaProgramada) {
        List<AsignacionResponse> list = asignacionService.findByFechaProgramada(fechaProgramada);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(AsignacionController.class).findByFechaProgramada(fechaProgramada)).withSelfRel());
    }

    @GetMapping("/estado/{estado}")
    public CollectionModel<AsignacionResponse> findByEstado(@PathVariable String estado) {
        List<AsignacionResponse> list = asignacionService.findByEstado(estado);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(AsignacionController.class).findByEstado(estado)).withSelfRel());
    }

    @GetMapping("/prioridad/{prioridad}")
    public CollectionModel<AsignacionResponse> findByPrioridad(@PathVariable Integer prioridad) {
        List<AsignacionResponse> list = asignacionService.findByPrioridad(prioridad);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(AsignacionController.class).findByPrioridad(prioridad)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AsignacionResponse create(@Valid @RequestBody AsignacionRequest request) {
        return addLinks(asignacionService.create(request));
    }

    @PutMapping("/{id}")
    public AsignacionResponse update(@PathVariable Long id, @Valid @RequestBody AsignacionRequest request) {
        return addLinks(asignacionService.update(id, request));
    }

    @PatchMapping("/{id}/estado")
    public AsignacionResponse updateEstado(@PathVariable Long id, @RequestParam String estado) {
        return addLinks(asignacionService.updateEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        asignacionService.deleteById(id);
    }
}
