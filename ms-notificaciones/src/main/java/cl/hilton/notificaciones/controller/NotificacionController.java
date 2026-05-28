package cl.hilton.notificaciones.controller;

import java.time.LocalDate;
import java.util.List;

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

@RestController
@RequestMapping("/api/v1/notificaciones/notificaciones")
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

    @GetMapping("/plantilla/{codigoPlantilla}")
    public List<NotificacionResponse> findByCodigoPlantilla(@PathVariable String codigoPlantilla) {
        return notificacionService.findByCodigoPlantilla(codigoPlantilla);
    }

    @GetMapping("/huesped/{emailHuesped}")
    public List<NotificacionResponse> findByEmailHuesped(@PathVariable String emailHuesped) {
        return notificacionService.findByEmailHuesped(emailHuesped);
    }

    @GetMapping("/fecha/{creadoEn}")
    public List<NotificacionResponse> findByCreadoEn(@PathVariable LocalDate creadoEn) {
        return notificacionService.findByCreadoEn(creadoEn);
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
