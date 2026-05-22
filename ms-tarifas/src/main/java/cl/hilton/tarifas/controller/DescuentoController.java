package cl.hilton.tarifas.controller;

import cl.hilton.tarifas.model.Descuento;
import cl.hilton.tarifas.service.DescuentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/descuentos")
@RequiredArgsConstructor
public class DescuentoController {

    private final DescuentoService descuentoService;

    @GetMapping
    public List<Descuento> obtenerDescuentos() {
        return descuentoService.obtenerDescuentos();
    }

    @GetMapping("/{id}")
    public Optional<Descuento> obtenerPorId(@PathVariable Long id) {
        return descuentoService.obtenerPorId(id);
    }

    @PostMapping
    public Descuento guardarDescuento(@RequestBody Descuento descuento) {
        return descuentoService.guardarDescuento(descuento);
    }

    @DeleteMapping("/{id}")
    public void eliminarDescuento(@PathVariable Long id) {
        descuentoService.eliminarDescuento(id);
    }

    @GetMapping("/activos/{activo}")
    public List<Descuento> obtenerDescuentosActivos(@PathVariable Boolean activo) {
        return descuentoService.obtenerDescuentosActivos(activo);
    }
}