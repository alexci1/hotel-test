package cl.hilton.housekeeping.controller;

import cl.hilton.housekeeping.dto.ProjHabitacionRequest;
import cl.hilton.housekeeping.dto.ProjHabitacionResponse;
import cl.hilton.housekeeping.service.ProjHabitacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/housekeeping/proj-habitaciones")
@RequiredArgsConstructor
public class ProjHabitacionController {

    private final ProjHabitacionService habitacionService;

    @GetMapping
    public List<ProjHabitacionResponse> findAll() {
        return habitacionService.findAll();
    }

    @GetMapping("/{numeroHabitacion}")
    public ProjHabitacionResponse findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        return habitacionService.findByNumeroHabitacion(numeroHabitacion);
    }

    @GetMapping("/tipo/{tipo}")
    public List<ProjHabitacionResponse> findByTipo(@PathVariable String tipo) {
        return habitacionService.findByTipo(tipo);
    }

    @GetMapping("/piso/{piso}")
    public List<ProjHabitacionResponse> findByPiso(@PathVariable Integer piso) {
        return habitacionService.findByPiso(piso);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjHabitacionResponse create(@Valid @RequestBody ProjHabitacionRequest request) {
        return habitacionService.create(request);
    }

    @PutMapping("/{numeroHabitacion}")
    public ProjHabitacionResponse update(@PathVariable String numeroHabitacion, @Valid @RequestBody ProjHabitacionRequest request) {
        return habitacionService.update(numeroHabitacion, request);
    }

    @DeleteMapping("/{numeroHabitacion}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        habitacionService.deleteByNumeroHabitacion(numeroHabitacion);
    }
}