package cl.hilton.checkin.controller;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.hilton.checkin.dto.CheckinRequest;
import cl.hilton.checkin.dto.CheckinResponse;
import cl.hilton.checkin.service.CheckinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/checkins")
@RequiredArgsConstructor
public class CheckinController {

    private final CheckinService checkinService;

    private CheckinResponse addLinks(CheckinResponse c) {
        c.add(linkTo(methodOn(CheckinController.class).findById(c.getId())).withSelfRel());
        c.add(linkTo(methodOn(CheckinController.class).update(c.getId(), null)).withRel("update").withTitle("PUT - Actualizar checkin"));
        c.add(linkTo(methodOn(CheckinController.class).deleteById(c.getId())).withRel("delete").withTitle("DELETE - Eliminar checkin"));
        c.add(linkTo(methodOn(CheckinController.class).findAll()).withRel("all").withTitle("GET - Todos los checkins"));
        return c;
    }

    @GetMapping
    public CollectionModel<CheckinResponse> findAll() {
        List<CheckinResponse> list = checkinService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(CheckinController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public CheckinResponse findById(@PathVariable Long id) {
        return addLinks(checkinService.findById(id));
    }

    @GetMapping("/reserva/{codigoReserva}")
    public CheckinResponse findByCodigoReserva(@PathVariable String codigoReserva) {
        return addLinks(checkinService.findByCodigoReserva(codigoReserva));
    }

    @GetMapping("/huesped/{emailHuesped}")
    public CollectionModel<CheckinResponse> findByEmailHuesped(@PathVariable String emailHuesped) {
        List<CheckinResponse> list = checkinService.findByEmailHuesped(emailHuesped);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(CheckinController.class).findByEmailHuesped(emailHuesped)).withSelfRel());
    }

    @GetMapping("/habitacion/{numeroHabitacion}")
    public CollectionModel<CheckinResponse> findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        List<CheckinResponse> list = checkinService.findByNumeroHabitacion(numeroHabitacion);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(CheckinController.class).findByNumeroHabitacion(numeroHabitacion)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CheckinResponse create(@Valid @RequestBody CheckinRequest request) {
        return addLinks(checkinService.create(request));
    }

    @PutMapping("/{id}")
    public CheckinResponse update(@PathVariable Long id, @Valid @RequestBody CheckinRequest request) {
        return addLinks(checkinService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        checkinService.deleteById(id);
    }
}
