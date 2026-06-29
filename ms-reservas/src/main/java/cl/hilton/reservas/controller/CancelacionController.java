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

import cl.hilton.reservas.dto.CancelacionRequest;
import cl.hilton.reservas.dto.CancelacionResponse;
import cl.hilton.reservas.service.CancelacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/cancelaciones")
@RequiredArgsConstructor
public class CancelacionController {

    private final CancelacionService cancelacionService;

    private CancelacionResponse addLinks(CancelacionResponse c) {
        c.add(linkTo(methodOn(CancelacionController.class).findById(c.getId())).withSelfRel());
        c.add(linkTo(methodOn(CancelacionController.class).update(c.getId(), null)).withRel("update"));
        c.add(linkTo(methodOn(CancelacionController.class).findById(c.getId())).withRel("delete"));
        c.add(linkTo(methodOn(CancelacionController.class).findAll()).withRel("all"));
        return c;
    }

    @GetMapping
    public CollectionModel<CancelacionResponse> findAll() {
        List<CancelacionResponse> list = cancelacionService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(CancelacionController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public CancelacionResponse findById(@PathVariable Long id) {
        return addLinks(cancelacionService.findById(id));
    }

    @GetMapping("/reserva/{codigoReserva}")
    public CancelacionResponse findByCodigoReserva(@PathVariable String codigoReserva) {
        return addLinks(cancelacionService.findByCodigoReserva(codigoReserva));
    }

    @GetMapping("/fecha/{canceladoEn}")
    public CollectionModel<CancelacionResponse> findByCanceladoEn(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate canceladoEn) {
        List<CancelacionResponse> list = cancelacionService.findByCanceladoEn(canceladoEn);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(CancelacionController.class).findByCanceladoEn(canceladoEn)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CancelacionResponse create(@Valid @RequestBody CancelacionRequest request) {
        return addLinks(cancelacionService.create(request));
    }

    @PutMapping("/{id}")
    public CancelacionResponse update(@PathVariable Long id, @Valid @RequestBody CancelacionRequest request) {
        return addLinks(cancelacionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        cancelacionService.deleteById(id);
    }
}
