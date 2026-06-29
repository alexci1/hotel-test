package cl.hilton.habitaciones.controller;

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

import cl.hilton.habitaciones.dto.TipoHabitacionRequest;
import cl.hilton.habitaciones.dto.TipoHabitacionResponse;
import cl.hilton.habitaciones.service.TipoHabitacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/tipos-habitacion")
@RequiredArgsConstructor
public class TipoHabitacionController {

    private final TipoHabitacionService tipoHabitacionService;

    private TipoHabitacionResponse addLinks(TipoHabitacionResponse t) {
        t.add(linkTo(methodOn(TipoHabitacionController.class).findById(t.getId())).withSelfRel());
        t.add(linkTo(methodOn(TipoHabitacionController.class).update(t.getId(), null)).withRel("update"));
        t.add(linkTo(methodOn(TipoHabitacionController.class).findById(t.getId())).withRel("delete"));
        t.add(linkTo(methodOn(TipoHabitacionController.class).findAll()).withRel("all"));
        return t;
    }

    @GetMapping
    public CollectionModel<TipoHabitacionResponse> findAll() {
        List<TipoHabitacionResponse> list = tipoHabitacionService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(TipoHabitacionController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public TipoHabitacionResponse findById(@PathVariable Long id) {
        return addLinks(tipoHabitacionService.findById(id));
    }

    @GetMapping("/codigo/{codigo}")
    public TipoHabitacionResponse findByCodigo(@PathVariable String codigo) {
        return addLinks(tipoHabitacionService.findByCodigo(codigo));
    }

    @GetMapping("/activos/{activo}")
    public CollectionModel<TipoHabitacionResponse> findByActivo(@PathVariable Boolean activo) {
        List<TipoHabitacionResponse> list = tipoHabitacionService.findByActivo(activo);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(TipoHabitacionController.class).findByActivo(activo)).withSelfRel());
    }

    @GetMapping("/capacidad/{capacidadMax}")
    public CollectionModel<TipoHabitacionResponse> findByCapacidadMax(@PathVariable Integer capacidadMax) {
        List<TipoHabitacionResponse> list = tipoHabitacionService.findByCapacidadMax(capacidadMax);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(TipoHabitacionController.class).findByCapacidadMax(capacidadMax)).withSelfRel());
    }

    @GetMapping("/capacidad-minima/{capacidadMax}")
    public CollectionModel<TipoHabitacionResponse> findByCapacidadMinima(@PathVariable Integer capacidadMax) {
        List<TipoHabitacionResponse> list = tipoHabitacionService.findByCapacidadMinima(capacidadMax);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(TipoHabitacionController.class).findByCapacidadMinima(capacidadMax)).withSelfRel());
    }

    @GetMapping("/buscar")
    public CollectionModel<TipoHabitacionResponse> findByDescripcion(@RequestParam String descripcion) {
        List<TipoHabitacionResponse> list = tipoHabitacionService.findByDescripcion(descripcion);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(TipoHabitacionController.class).findByDescripcion(descripcion)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TipoHabitacionResponse create(@Valid @RequestBody TipoHabitacionRequest request) {
        return addLinks(tipoHabitacionService.create(request));
    }

    @PutMapping("/{id}")
    public TipoHabitacionResponse update(
            @PathVariable Long id,
            @Valid @RequestBody TipoHabitacionRequest request) {
        return addLinks(tipoHabitacionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        tipoHabitacionService.deleteById(id);
    }
}
