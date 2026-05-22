package cl.hilton.habitaciones.controller;

import cl.hilton.habitaciones.model.EstadoHabitacion;
import cl.hilton.habitaciones.service.EstadoHabitacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estado-habitacion")
@RequiredArgsConstructor
public class EstadoHabitacionController {

    private final EstadoHabitacionService estadoHabitacionService;

    @GetMapping
    public List<EstadoHabitacion> obtenerEstados() {
        return estadoHabitacionService.obtenerEstados();
    }

    @GetMapping("/{id}")
    public Optional<EstadoHabitacion> obtenerPorId(@PathVariable Long id) {
        return estadoHabitacionService.obtenerPorId(id);
    }

    @PostMapping
    public EstadoHabitacion guardarEstado(@RequestBody EstadoHabitacion estadoHabitacion) {
        return estadoHabitacionService.guardarEstado(estadoHabitacion);
    }

    @DeleteMapping("/{id}")
    public void eliminarEstado(@PathVariable Long id) {
        estadoHabitacionService.eliminarEstado(id);
    }

    @GetMapping("/estado/{estado}")
    public List<EstadoHabitacion> obtenerPorEstado(@PathVariable String estado) {
        return estadoHabitacionService.obtenerPorEstado(estado);
    }
}