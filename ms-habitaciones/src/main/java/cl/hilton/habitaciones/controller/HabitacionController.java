package cl.hilton.habitaciones.controller;

import cl.hilton.habitaciones.model.Habitacion;
import cl.hilton.habitaciones.service.HabitacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/habitaciones")
@RequiredArgsConstructor
public class HabitacionController {

    private final HabitacionService habitacionService;

    @GetMapping
    public List<Habitacion> obtenerHabitaciones() {
        return habitacionService.obtenerHabitaciones();
    }

    @GetMapping("/{id}")
    public Optional<Habitacion> obtenerPorId(@PathVariable Long id) {
        return habitacionService.obtenerPorId(id);
    }

    @PostMapping
    public Habitacion guardarHabitacion(@RequestBody Habitacion habitacion) {
        return habitacionService.guardarHabitacion(habitacion);
    }

    @DeleteMapping("/{id}")
    public void eliminarHabitacion(@PathVariable Long id) {
        habitacionService.eliminarHabitacion(id);
    }

    @GetMapping("/piso/{piso}")
    public List<Habitacion> obtenerPorPiso(@PathVariable Integer piso) {
        return habitacionService.obtenerPorPiso(piso);
    }

    @GetMapping("/activas/{activa}")
    public List<Habitacion> obtenerActivas(@PathVariable Boolean activa) {
        return habitacionService.obtenerActivas(activa);
    }
}