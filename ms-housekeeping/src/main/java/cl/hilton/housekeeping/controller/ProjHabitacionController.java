package cl.hilton.housekeeping.controller;

import cl.hilton.housekeeping.dto.ProjHabitacionRequest;
import cl.hilton.housekeeping.dto.ProjHabitacionResponse;
import cl.hilton.housekeeping.service.ProjHabitacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proj-habitaciones")
public class ProjHabitacionController {

    private final ProjHabitacionService habitacionService;

    public ProjHabitacionController(ProjHabitacionService habitacionService) {
        this.habitacionService = habitacionService;
    }

    @GetMapping
    public List<ProjHabitacionResponse> listar() {
        return habitacionService.listar();
    }

    @GetMapping("/{numeroHabitacion}")
    public ProjHabitacionResponse buscarPorNumero(@PathVariable String numeroHabitacion) {
        return habitacionService.buscarPorNumero(numeroHabitacion);
    }

    @GetMapping("/tipo/{tipo}")
    public List<ProjHabitacionResponse> buscarPorTipo(@PathVariable String tipo) {
        return habitacionService.buscarPorTipo(tipo);
    }

    @GetMapping("/piso/{piso}")
    public List<ProjHabitacionResponse> buscarPorPiso(@PathVariable Long piso) {
        return habitacionService.buscarPorPiso(piso);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjHabitacionResponse crear(@Valid @RequestBody ProjHabitacionRequest request) {
        return habitacionService.crear(request);
    }

    @PutMapping("/{numeroHabitacion}")
    public ProjHabitacionResponse actualizar(@PathVariable String numeroHabitacion, @Valid @RequestBody ProjHabitacionRequest request) {
        return habitacionService.actualizar(numeroHabitacion, request);
    }

    @DeleteMapping("/{numeroHabitacion}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable String numeroHabitacion) {
        habitacionService.eliminar(numeroHabitacion);
    }
}