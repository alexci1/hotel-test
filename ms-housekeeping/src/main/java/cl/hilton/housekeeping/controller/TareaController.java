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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.hilton.housekeeping.dto.TareaRequest;
import cl.hilton.housekeeping.dto.TareaResponse;
import cl.hilton.housekeeping.service.TareaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/tareas")
@RequiredArgsConstructor
public class TareaController {

    private final TareaService tareaService;

    private TareaResponse addLinks(TareaResponse t) {
        t.add(linkTo(methodOn(TareaController.class).findById(t.getId())).withSelfRel());
        t.add(linkTo(methodOn(TareaController.class).update(t.getId(), null)).withRel("update").withTitle("PUT - Actualizar tarea"));
        t.add(linkTo(methodOn(TareaController.class).deleteById(t.getId())).withRel("delete").withTitle("DELETE - Eliminar tarea"));
        t.add(linkTo(methodOn(TareaController.class).findAll()).withRel("all").withTitle("GET - Todas las tareas"));
        return t;
    }

    @GetMapping
    public CollectionModel<TareaResponse> findAll() {
        List<TareaResponse> list = tareaService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(TareaController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public TareaResponse findById(@PathVariable Long id) {
        return addLinks(tareaService.findById(id));
    }

    @GetMapping("/codigo/{codigo}")
    public TareaResponse findByCodigo(@PathVariable String codigo) {
        return addLinks(tareaService.findByCodigo(codigo));
    }

    @GetMapping("/activa/{activa}")
    public CollectionModel<TareaResponse> findByActiva(@PathVariable Boolean activa) {
        List<TareaResponse> list = tareaService.findByActiva(activa);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(TareaController.class).findByActiva(activa)).withSelfRel());
    }

    @GetMapping("/descripcion")
    public CollectionModel<TareaResponse> findByDescripcion(@RequestParam String descripcion) {
        List<TareaResponse> list = tareaService.findByDescripcion(descripcion);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(TareaController.class).findByDescripcion(descripcion)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TareaResponse create(@Valid @RequestBody TareaRequest request) {
        return addLinks(tareaService.create(request));
    }

    @PutMapping("/{id}")
    public TareaResponse update(@PathVariable Long id, @Valid @RequestBody TareaRequest request) {
        return addLinks(tareaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        tareaService.deleteById(id);
    }
}
