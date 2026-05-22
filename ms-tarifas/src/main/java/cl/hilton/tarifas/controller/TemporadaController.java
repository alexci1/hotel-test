package cl.hilton.tarifas.controller;

import cl.hilton.tarifas.model.Temporada;
import cl.hilton.tarifas.service.TemporadaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/temporadas")
@RequiredArgsConstructor
public class TemporadaController {

    private final TemporadaService temporadaService;

    @GetMapping
    public List<Temporada> obtenerTemporadas() {
        return temporadaService.obtenerTemporadas();
    }

    @GetMapping("/{id}")
    public Optional<Temporada> obtenerPorId(@PathVariable Long id) {
        return temporadaService.obtenerPorId(id);
    }

    @PostMapping
    public Temporada guardarTemporada(@RequestBody Temporada temporada) {
        return temporadaService.guardarTemporada(temporada);
    }

    @DeleteMapping("/{id}")
    public void eliminarTemporada(@PathVariable Long id) {
        temporadaService.eliminarTemporada(id);
    }
}