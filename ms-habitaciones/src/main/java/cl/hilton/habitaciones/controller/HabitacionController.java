package cl.hilton.habitaciones.controller;

import java.util.List;

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

import cl.hilton.habitaciones.dto.HabitacionRequest;
import cl.hilton.habitaciones.dto.HabitacionResponse;
import cl.hilton.habitaciones.service.HabitacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/habitaciones")
@RequiredArgsConstructor
public class HabitacionController {

    private final HabitacionService habitacionService;

    @GetMapping
    public List<HabitacionResponse> findAll() {
        return habitacionService.findAll();
    }

    @GetMapping("/{id}")
    public HabitacionResponse findById(@PathVariable Long id) {
        return habitacionService.findById(id);
    }

    @GetMapping("/numero/{numeroHabitacion}")
    public HabitacionResponse findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        return habitacionService.findByNumeroHabitacion(numeroHabitacion);
    }

    @GetMapping("/piso/{piso}")
    public List<HabitacionResponse> findByPiso(@PathVariable Integer piso) {
        return habitacionService.findByPiso(piso);
    }

    @GetMapping("/activas/{activa}")
    public List<HabitacionResponse> findByActiva(@PathVariable Boolean activa) {
        return habitacionService.findByActiva(activa);
    }

    @GetMapping("/tipo/{codigoTipo}")
    public List<HabitacionResponse> findByCodigoTipo(@PathVariable String codigoTipo) {
        return habitacionService.findByCodigoTipo(codigoTipo);
    }

    @GetMapping("/tipo/{codigoTipo}/activa/{activa}")
    public List<HabitacionResponse> findByCodigoTipoAndActiva(
            @PathVariable String codigoTipo,
            @PathVariable Boolean activa) {
        return habitacionService.findByCodigoTipoAndActiva(codigoTipo, activa);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HabitacionResponse create(@Valid @RequestBody HabitacionRequest request) {
        return habitacionService.create(request);
    }

    @PutMapping("/{id}")
    public HabitacionResponse update(
            @PathVariable Long id,
            @Valid @RequestBody HabitacionRequest request) {
        return habitacionService.update(id, request);
    }

    @PatchMapping("/{id}/activa")
    public HabitacionResponse cambiarActiva(
            @PathVariable Long id,
            @RequestParam Boolean activa) {
        return habitacionService.cambiarActiva(id, activa);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        habitacionService.deleteById(id);
    }
}
