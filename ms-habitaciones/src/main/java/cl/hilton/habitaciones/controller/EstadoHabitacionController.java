package cl.hilton.habitaciones.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
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

import cl.hilton.habitaciones.dto.EstadoHabitacionRequest;
import cl.hilton.habitaciones.dto.EstadoHabitacionResponse;
import cl.hilton.habitaciones.service.EstadoHabitacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/habitaciones/estados")
@RequiredArgsConstructor
public class EstadoHabitacionController {

    private final EstadoHabitacionService estadoHabitacionService;

    @GetMapping
    public List<EstadoHabitacionResponse> findAll() {
        return estadoHabitacionService.findAll();
    }

    @GetMapping("/{id}")
    public EstadoHabitacionResponse findById(@PathVariable Long id) {
        return estadoHabitacionService.findById(id);
    }

    @GetMapping("/habitacion/{numeroHabitacion}")
    public EstadoHabitacionResponse findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        return estadoHabitacionService.findByNumeroHabitacion(numeroHabitacion);
    }

    @GetMapping("/estado/{estado}")
    public List<EstadoHabitacionResponse> findByEstado(@PathVariable String estado) {
        return estadoHabitacionService.findByEstado(estado);
    }

    @GetMapping("/actualizado/{actualizadoEn}")
    public List<EstadoHabitacionResponse> findByActualizadoEn(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate actualizadoEn) {
        return estadoHabitacionService.findByActualizadoEn(actualizadoEn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoHabitacionResponse create(@Valid @RequestBody EstadoHabitacionRequest request) {
        return estadoHabitacionService.create(request);
    }

    @PutMapping("/{id}")
    public EstadoHabitacionResponse update(
            @PathVariable Long id,
            @Valid @RequestBody EstadoHabitacionRequest request) {
        return estadoHabitacionService.update(id, request);
    }

    @PatchMapping("/habitacion/{numeroHabitacion}/estado")
    public EstadoHabitacionResponse cambiarEstado(
            @PathVariable String numeroHabitacion,
            @RequestParam String estado) {
        return estadoHabitacionService.cambiarEstado(numeroHabitacion, estado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        estadoHabitacionService.deleteById(id);
    }
}
