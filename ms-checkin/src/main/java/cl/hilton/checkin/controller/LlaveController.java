package cl.hilton.checkin.controller;

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

import cl.hilton.checkin.dto.LlaveRequest;
import cl.hilton.checkin.dto.LlaveResponse;
import cl.hilton.checkin.service.LlaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/llaves")
@RequiredArgsConstructor
public class LlaveController {

    private final LlaveService llaveService;

    private LlaveResponse addLinks(LlaveResponse l) {
        l.add(linkTo(methodOn(LlaveController.class).findById(l.getId())).withSelfRel());
        l.add(linkTo(methodOn(LlaveController.class).update(l.getId(), null)).withRel("update").withTitle("PUT - Actualizar llave"));
        l.add(linkTo(methodOn(LlaveController.class).deleteById(l.getId())).withRel("delete").withTitle("DELETE - Eliminar llave"));
        l.add(linkTo(methodOn(LlaveController.class).updateEstado(l.getId(), null)).withRel("cambiar-estado").withTitle("PATCH - Cambiar estado llave"));
        l.add(linkTo(methodOn(LlaveController.class).findAll()).withRel("all").withTitle("GET - Todas las llaves"));
        return l;
    }

    @GetMapping
    public CollectionModel<LlaveResponse> findAll() {
        List<LlaveResponse> list = llaveService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(LlaveController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public LlaveResponse findById(@PathVariable Long id) {
        return addLinks(llaveService.findById(id));
    }

    @GetMapping("/codigo/{codigoLlave}")
    public LlaveResponse findByCodigoLlave(@PathVariable String codigoLlave) {
        return addLinks(llaveService.findByCodigoLlave(codigoLlave));
    }

    @GetMapping("/habitacion/{numeroHabitacion}")
    public CollectionModel<LlaveResponse> findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        List<LlaveResponse> list = llaveService.findByNumeroHabitacion(numeroHabitacion);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(LlaveController.class).findByNumeroHabitacion(numeroHabitacion)).withSelfRel());
    }

    @GetMapping("/activa/{activa}")
    public CollectionModel<LlaveResponse> findByActiva(@PathVariable Boolean activa) {
        List<LlaveResponse> list = llaveService.findByActiva(activa);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(LlaveController.class).findByActiva(activa)).withSelfRel());
    }

    @GetMapping("/reserva/{codigoReserva}")
    public CollectionModel<LlaveResponse> findByCodigoReserva(@PathVariable String codigoReserva) {
        List<LlaveResponse> list = llaveService.findByCodigoReserva(codigoReserva);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(LlaveController.class).findByCodigoReserva(codigoReserva)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LlaveResponse create(@Valid @RequestBody LlaveRequest request) {
        return addLinks(llaveService.create(request));
    }

    @PutMapping("/{id}")
    public LlaveResponse update(@PathVariable Long id, @Valid @RequestBody LlaveRequest request) {
        return addLinks(llaveService.update(id, request));
    }

    @PatchMapping("/{id}/estado")
    public LlaveResponse updateEstado(@PathVariable Long id, @RequestParam Boolean activa) {
        return addLinks(llaveService.updateEstado(id, activa));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        llaveService.deleteById(id);
    }
}
