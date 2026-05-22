package cl.hilton.reservas.controller;

import cl.hilton.reservas.model.Disponibilidad;
import cl.hilton.reservas.service.DisponibilidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/disponibilidades")
@RequiredArgsConstructor
public class DisponibilidadController {

    private final DisponibilidadService disponibilidadService;

    @GetMapping
    public List<Disponibilidad> obtenerDisponibilidades() {
        return disponibilidadService.obtenerDisponibilidades();
    }

    @GetMapping("/{id}")
    public Optional<Disponibilidad> obtenerPorId(@PathVariable Long id) {
        return disponibilidadService.obtenerPorId(id);
    }

    @PostMapping
    public Disponibilidad guardarDisponibilidad(@RequestBody Disponibilidad disponibilidad) {
        return disponibilidadService.guardarDisponibilidad(disponibilidad);
    }

    @DeleteMapping("/{id}")
    public void eliminarDisponibilidad(@PathVariable Long id) {
        disponibilidadService.eliminarDisponibilidad(id);
    }

    @GetMapping("/fecha/{fecha}")
    public List<Disponibilidad> obtenerPorFecha(@PathVariable LocalDate fecha) {
        return disponibilidadService.obtenerPorFecha(fecha);
    }

    @GetMapping("/estado/{disponible}")
    public List<Disponibilidad> obtenerPorDisponible(@PathVariable Boolean disponible) {
        return disponibilidadService.obtenerPorDisponible(disponible);
    }
}