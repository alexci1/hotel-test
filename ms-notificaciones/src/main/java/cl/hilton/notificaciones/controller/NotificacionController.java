package cl.hilton.notificaciones.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import cl.hilton.notificaciones.dto.NotificacionRequest;
import cl.hilton.notificaciones.dto.NotificacionResponse;
import cl.hilton.notificaciones.service.NotificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService notificacionService;

    @GetMapping
    public List<NotificacionResponse> findAll() {
        return notificacionService.findAll();
    }

    @GetMapping("/{id}")
    public NotificacionResponse findById(@PathVariable Long id) {
        return notificacionService.findById(id);
    }

    @GetMapping("/evento/{eventoOrigen}")
    public List<NotificacionResponse> findByEventoOrigen(@PathVariable String eventoOrigen) {
        return notificacionService.findByEventoOrigen(eventoOrigen);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotificacionResponse create(@Valid @RequestBody NotificacionRequest request) {
        return notificacionService.create(request);
    }

    @PutMapping("/{id}")
    public NotificacionResponse update(
            @PathVariable Long id,
            @Valid @RequestBody NotificacionRequest request) {
        return notificacionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        notificacionService.deleteById(id);
    }
}
