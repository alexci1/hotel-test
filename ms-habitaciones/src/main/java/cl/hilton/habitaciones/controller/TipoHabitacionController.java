package cl.hilton.habitaciones.controller;

import cl.hilton.habitaciones.model.TipoHabitacion;
import cl.hilton.habitaciones.service.TipoHabitacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipos-habitacion")
@RequiredArgsConstructor
public class TipoHabitacionController {

    private final TipoHabitacionService tipoHabitacionService;

    @GetMapping
    public List<TipoHabitacion> obtenerTiposHabitacion() {
        return tipoHabitacionService.obtenerTiposHabitacion();
    }

    @GetMapping("/{id}")
    public Optional<TipoHabitacion> obtenerPorId(@PathVariable Long id) {
        return tipoHabitacionService.obtenerPorId(id);
    }

    @PostMapping
    public TipoHabitacion guardarTipoHabitacion(@RequestBody TipoHabitacion tipoHabitacion) {
        return tipoHabitacionService.guardarTipoHabitacion(tipoHabitacion);
    }

    @DeleteMapping("/{id}")
    public void eliminarTipoHabitacion(@PathVariable Long id) {
        tipoHabitacionService.eliminarTipoHabitacion(id);
    }

    @GetMapping("/activos/{activo}")
    public List<TipoHabitacion> obtenerActivos(@PathVariable Boolean activo) {
        return tipoHabitacionService.obtenerActivos(activo);
    }
}