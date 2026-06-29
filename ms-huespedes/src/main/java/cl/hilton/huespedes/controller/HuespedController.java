package cl.hilton.huespedes.controller;

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

import cl.hilton.huespedes.dto.HuespedRequest;
import cl.hilton.huespedes.dto.HuespedResponse;
import cl.hilton.huespedes.service.HuespedService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/huespedes")
@RequiredArgsConstructor
public class HuespedController {

    private final HuespedService huespedService;

    private HuespedResponse addLinks(HuespedResponse h) {
        h.add(linkTo(methodOn(HuespedController.class).findById(h.getId())).withSelfRel());
        h.add(linkTo(methodOn(HuespedController.class).update(h.getId(), null)).withRel("update").withTitle("PUT - Actualizar huesped"));
        h.add(linkTo(methodOn(HuespedController.class).deleteById(h.getId())).withRel("delete").withTitle("DELETE - Eliminar huesped"));
        h.add(linkTo(methodOn(HuespedController.class).cambiarActivo(h.getId(), null)).withRel("cambiar-activo").withTitle("PATCH - Cambiar estado activo"));
        h.add(linkTo(methodOn(HuespedController.class).findAll()).withRel("all").withTitle("GET - Todos los huespedes"));
        return h;
    }

    @GetMapping
    public CollectionModel<HuespedResponse> findAll() {
        List<HuespedResponse> list = huespedService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(HuespedController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public HuespedResponse findById(@PathVariable Long id) {
        return addLinks(huespedService.findById(id));
    }

    @GetMapping("/email/{email}")
    public HuespedResponse findByEmail(@PathVariable String email) {
        return addLinks(huespedService.findByEmail(email));
    }

    @GetMapping("/nombre/{nombreCompleto}")
    public CollectionModel<HuespedResponse> findByNombreCompleto(@PathVariable String nombreCompleto) {
        List<HuespedResponse> list = huespedService.findByNombreCompleto(nombreCompleto);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(HuespedController.class).findByNombreCompleto(nombreCompleto)).withSelfRel());
    }

    @GetMapping("/activo/{activo}")
    public CollectionModel<HuespedResponse> findByActivo(@PathVariable Boolean activo) {
        List<HuespedResponse> list = huespedService.findByActivo(activo);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(HuespedController.class).findByActivo(activo)).withSelfRel());
    }

    @GetMapping("/creado/{creadoEn}")
    public CollectionModel<HuespedResponse> findByCreadoEn(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate creadoEn) {
        List<HuespedResponse> list = huespedService.findByCreadoEn(creadoEn);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(HuespedController.class).findByCreadoEn(creadoEn)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HuespedResponse create(@Valid @RequestBody HuespedRequest request) {
        return addLinks(huespedService.create(request));
    }

    @PutMapping("/{id}")
    public HuespedResponse update(@PathVariable Long id, @Valid @RequestBody HuespedRequest request) {
        return addLinks(huespedService.update(id, request));
    }

    @PatchMapping("/{id}/activo")
    public HuespedResponse cambiarActivo(@PathVariable Long id, @RequestParam Boolean activo) {
        return addLinks(huespedService.cambiarActivo(id, activo));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        huespedService.deleteById(id);
    }
}
