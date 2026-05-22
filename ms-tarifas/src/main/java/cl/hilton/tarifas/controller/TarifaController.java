package cl.hilton.tarifas.controller;

import cl.hilton.tarifas.model.Tarifa;
import cl.hilton.tarifas.service.TarifaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tarifas")
@RequiredArgsConstructor
public class TarifaController {

    private final TarifaService tarifaService;

    @GetMapping
    public List<Tarifa> obtenerTarifas() {
        return tarifaService.obtenerTarifas();
    }

    @GetMapping("/{id}")
    public Optional<Tarifa> obtenerPorId(@PathVariable Long id) {
        return tarifaService.obtenerPorId(id);
    }

    @PostMapping
    public Tarifa guardarTarifa(@RequestBody Tarifa tarifa) {
        return tarifaService.guardarTarifa(tarifa);
    }

    @DeleteMapping("/{id}")
    public void eliminarTarifa(@PathVariable Long id) {
        tarifaService.eliminarTarifa(id);
    }

    @GetMapping("/activas/{activa}")
    public List<Tarifa> obtenerTarifasActivas(@PathVariable Boolean activa) {
        return tarifaService.obtenerTarifasActivas(activa);
    }
}