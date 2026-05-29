package cl.hilton.reservas.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
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

import cl.hilton.reservas.dto.DisponibilidadRequest;
import cl.hilton.reservas.dto.DisponibilidadResponse;
import cl.hilton.reservas.service.DisponibilidadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reservas/disponibilidades")
@RequiredArgsConstructor
public class DisponibilidadController {

    private final DisponibilidadService disponibilidadService;

    @GetMapping
    public List<DisponibilidadResponse> findAll() {
        return disponibilidadService.findAll();
    }

    @GetMapping("/{id}")
    public DisponibilidadResponse findById(@PathVariable Long id) {
        return disponibilidadService.findById(id);
    }

    @GetMapping("/habitacion/{numeroHabitacion}/fecha/{fecha}")
    public DisponibilidadResponse findByHabitacionAndFecha(
            @PathVariable String numeroHabitacion,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return disponibilidadService.findByHabitacionAndFecha(numeroHabitacion, fecha);
    }

    @GetMapping("/habitacion/{numeroHabitacion}")
    public List<DisponibilidadResponse> findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        return disponibilidadService.findByNumeroHabitacion(numeroHabitacion);
    }

    @GetMapping("/fecha/{fecha}")
    public List<DisponibilidadResponse> findByFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return disponibilidadService.findByFecha(fecha);
    }

    @GetMapping("/rango")
    public List<DisponibilidadResponse> findByRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return disponibilidadService.findByRangoFechas(desde, hasta);
    }

    @GetMapping("/disponible/{disponible}")
    public List<DisponibilidadResponse> findByDisponible(@PathVariable Boolean disponible) {
        return disponibilidadService.findByDisponible(disponible);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DisponibilidadResponse create(@Valid @RequestBody DisponibilidadRequest request) {
        return disponibilidadService.create(request);
    }

    @PutMapping("/{id}")
    public DisponibilidadResponse update(
            @PathVariable Long id,
            @Valid @RequestBody DisponibilidadRequest request) {
        return disponibilidadService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        disponibilidadService.deleteById(id);
    }
}
