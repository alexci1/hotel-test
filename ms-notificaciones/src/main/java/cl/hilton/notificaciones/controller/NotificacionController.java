package cl.hilton.notificaciones.controller;

import java.time.LocalDate;
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

import cl.hilton.notificaciones.dto.NotificacionRequest;
import cl.hilton.notificaciones.dto.NotificacionResponse;
import cl.hilton.notificaciones.service.NotificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;

    private NotificacionResponse addLinks(NotificacionResponse n) {
        n.add(linkTo(methodOn(NotificacionController.class).findById(n.getId())).withSelfRel());
        n.add(linkTo(methodOn(NotificacionController.class).update(n.getId(), null)).withRel("update").withTitle("PUT - Actualizar notificacion"));
        n.add(linkTo(methodOn(NotificacionController.class).deleteById(n.getId())).withRel("delete").withTitle("DELETE - Eliminar notificacion"));
        n.add(linkTo(methodOn(NotificacionController.class).findByEmailHuesped(n.getEmailHuesped())).withRel("notificaciones-huesped").withTitle("GET - Notificaciones del huesped"));
        n.add(linkTo(methodOn(NotificacionController.class).findAll()).withRel("all").withTitle("GET - Todas las notificaciones"));
        return n;
    }

    @GetMapping
    public CollectionModel<NotificacionResponse> findAll() {
        List<NotificacionResponse> list = notificacionService.findAll();
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(NotificacionController.class).findAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public NotificacionResponse findById(@PathVariable Long id) {
        return addLinks(notificacionService.findById(id));
    }

    @GetMapping("/evento/{eventoOrigen}")
    public CollectionModel<NotificacionResponse> findByEventoOrigen(@PathVariable String eventoOrigen) {
        List<NotificacionResponse> list = notificacionService.findByEventoOrigen(eventoOrigen);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(NotificacionController.class).findByEventoOrigen(eventoOrigen)).withSelfRel());
    }

    @GetMapping("/plantilla/{codigoPlantilla}")
    public CollectionModel<NotificacionResponse> findByCodigoPlantilla(@PathVariable String codigoPlantilla) {
        List<NotificacionResponse> list = notificacionService.findByCodigoPlantilla(codigoPlantilla);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(NotificacionController.class).findByCodigoPlantilla(codigoPlantilla)).withSelfRel());
    }

    @GetMapping("/huesped/{emailHuesped}")
    public CollectionModel<NotificacionResponse> findByEmailHuesped(@PathVariable String emailHuesped) {
        List<NotificacionResponse> list = notificacionService.findByEmailHuesped(emailHuesped);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(NotificacionController.class).findByEmailHuesped(emailHuesped)).withSelfRel());
    }

    @GetMapping("/fecha/{creadoEn}")
    public CollectionModel<NotificacionResponse> findByCreadoEn(@PathVariable LocalDate creadoEn) {
        List<NotificacionResponse> list = notificacionService.findByCreadoEn(creadoEn);
        list.forEach(this::addLinks);
        return CollectionModel.of(list, linkTo(methodOn(NotificacionController.class).findByCreadoEn(creadoEn)).withSelfRel());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotificacionResponse create(@Valid @RequestBody NotificacionRequest request) {
        return addLinks(notificacionService.create(request));
    }

    @PutMapping("/{id}")
    public NotificacionResponse update(
            @PathVariable Long id,
            @Valid @RequestBody NotificacionRequest request) {
        return addLinks(notificacionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        notificacionService.deleteById(id);
    }
}
