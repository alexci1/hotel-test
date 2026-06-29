package cl.hilton.habitaciones.controller;

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

import cl.hilton.habitaciones.dto.HabitacionRequest;
import cl.hilton.habitaciones.dto.HabitacionResponse;
import cl.hilton.habitaciones.service.HabitacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/habitaciones")
@RequiredArgsConstructor
public class HabitacionController {

    private final HabitacionService habitacionService;

    private HabitacionResponse addLinks(HabitacionResponse h) {
        h.add(linkTo(methodOn(HabitacionController.class).findById(h.getId())).withSelfRel());
        h.add(linkTo(methodOn(HabitacionController.class).update(h.getId(), null)).withRel("update").withTitle("PUT - Actualizar habitacion"));
        h.add(linkTo(methodOn(HabitacionController.class).deleteById(h.getId())).withRel("delete").withTitle("DELETE - Eliminar habitacion"));
        h.add(linkTo(methodOn(HabitacionController.class).cambiarActiva(h.getId(), null)).withRel("cambiar-activa").withTitle("PATCH - Cambiar estado activa"));
        h.add(linkTo(methodOn(HabitacionController.class).findAll()).withRel("all").withTitle("GET - Todas las habitaciones"));
        return h;
    }

    @GetMapping
    public CollectionModel<HabitacionResponse> findAll() {
        List<HabitacionResponse> list = habitacionService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(HabitacionController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public HabitacionResponse findById(@PathVariable Long id) {
        return addLinks(habitacionService.findById(id));
    }

    @GetMapping("/numero/{numeroHabitacion}")
    public HabitacionResponse findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        return addLinks(habitacionService.findByNumeroHabitacion(numeroHabitacion));
    }

    @GetMapping("/piso/{piso}")
    public CollectionModel<HabitacionResponse> findByPiso(@PathVariable Integer piso) {
        List<HabitacionResponse> list = habitacionService.findByPiso(piso);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(HabitacionController.class).findByPiso(piso)).withSelfRel());
    }

    @GetMapping("/activas/{activa}")
    public CollectionModel<HabitacionResponse> findByActiva(@PathVariable Boolean activa) {
        List<HabitacionResponse> list = habitacionService.findByActiva(activa);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(HabitacionController.class).findByActiva(activa)).withSelfRel());
    }

    @GetMapping("/tipo/{codigoTipo}")
    public CollectionModel<HabitacionResponse> findByCodigoTipo(@PathVariable String codigoTipo) {
        List<HabitacionResponse> list = habitacionService.findByCodigoTipo(codigoTipo);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(HabitacionController.class).findByCodigoTipo(codigoTipo)).withSelfRel());
    }

    @GetMapping("/tipo/{codigoTipo}/activa/{activa}")
    public CollectionModel<HabitacionResponse> findByCodigoTipoAndActiva(
            @PathVariable String codigoTipo,
            @PathVariable Boolean activa) {
        List<HabitacionResponse> list = habitacionService.findByCodigoTipoAndActiva(codigoTipo, activa);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(HabitacionController.class).findByCodigoTipoAndActiva(codigoTipo, activa)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HabitacionResponse create(@Valid @RequestBody HabitacionRequest request) {
        return addLinks(habitacionService.create(request));
    }

    @PutMapping("/{id}")
    public HabitacionResponse update(
            @PathVariable Long id,
            @Valid @RequestBody HabitacionRequest request) {
        return addLinks(habitacionService.update(id, request));
    }

    @PatchMapping("/{id}/activa")
    public HabitacionResponse cambiarActiva(
            @PathVariable Long id,
            @RequestParam Boolean activa) {
        return addLinks(habitacionService.cambiarActiva(id, activa));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        habitacionService.deleteById(id);
    }
}
