package cl.hilton.inventario.controller;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.hilton.inventario.dto.ProjHabitacionRequest;
import cl.hilton.inventario.dto.ProjHabitacionResponse;
import cl.hilton.inventario.service.ProjHabitacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/inventario/habitaciones-proyeccion")
@RequiredArgsConstructor
public class ProjHabitacionController {

    private final ProjHabitacionService habitacionService;

    @GetMapping
    public List<ProjHabitacionResponse> findAll() {
        return habitacionService.findAll();
    }

    @GetMapping("/numero/{numeroHabitacion}")
    public ProjHabitacionResponse findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        return habitacionService.findByNumeroHabitacion(numeroHabitacion);
    }

    @GetMapping("/tipo/{tipo}")
    public List<ProjHabitacionResponse> findByTipo(@PathVariable String tipo) {
        return habitacionService.findByTipo(tipo);
    }

    @GetMapping("/actualizado/{actualizadoEn}")
    public List<ProjHabitacionResponse> findByActualizadoEn(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate actualizadoEn) {
        return habitacionService.findByActualizadoEn(actualizadoEn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjHabitacionResponse create(@Valid @RequestBody ProjHabitacionRequest request) {
        return habitacionService.create(request);
    }

    @PostMapping("/sincronizar/numero/{numeroHabitacion}")
    public ProjHabitacionResponse sincronizarPorNumeroHabitacion(@PathVariable String numeroHabitacion) {
        return habitacionService.sincronizarPorNumeroHabitacion(numeroHabitacion);
    }

    @PutMapping("/numero/{numeroHabitacion}")
    public ProjHabitacionResponse update(
            @PathVariable String numeroHabitacion,
            @Valid @RequestBody ProjHabitacionRequest request) {
        return habitacionService.update(numeroHabitacion, request);
    }

    @DeleteMapping("/numero/{numeroHabitacion}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        habitacionService.deleteByNumeroHabitacion(numeroHabitacion);
    }
}
