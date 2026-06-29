package cl.hilton.inventario.controller;

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

import cl.hilton.inventario.dto.MiniBarRequest;
import cl.hilton.inventario.dto.MiniBarResponse;
import cl.hilton.inventario.service.MinibarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/minibares")
@RequiredArgsConstructor
public class MiniBarController {

    private final MinibarService miniBarService;

    @GetMapping
    public List<MiniBarResponse> findAll() {
        return miniBarService.findAll();
    }

    @GetMapping("/{id}")
    public MiniBarResponse findById(@PathVariable Long id) {
        return miniBarService.findById(id);
    }

    @GetMapping("/habitacion/{numeroHabitacion}")
    public List<MiniBarResponse> findByNumeroHabitacion(@PathVariable String numeroHabitacion) {
        return miniBarService.findByNumeroHabitacion(numeroHabitacion);
    }

    @GetMapping("/producto/{codigoProducto}")
    public List<MiniBarResponse> findByCodigoProducto(@PathVariable String codigoProducto) {
        return miniBarService.findByCodigoProducto(codigoProducto);
    }

    @GetMapping("/habitacion/{numeroHabitacion}/producto/{codigoProducto}")
    public MiniBarResponse findByHabitacionAndProducto(
            @PathVariable String numeroHabitacion,
            @PathVariable String codigoProducto) {
        return miniBarService.findByHabitacionAndProducto(numeroHabitacion, codigoProducto);
    }

    @GetMapping("/cantidad/{cantidad}")
    public List<MiniBarResponse> findByCantidad(@PathVariable Integer cantidad) {
        return miniBarService.findByCantidad(cantidad);
    }

    @GetMapping("/cantidad-mayor/{cantidad}")
    public List<MiniBarResponse> findByCantidadGreaterThan(@PathVariable Integer cantidad) {
        return miniBarService.findByCantidadGreaterThan(cantidad);
    }

    @GetMapping("/precio-mayor/{precioUnitUsd}")
    public List<MiniBarResponse> findByPrecioUnitUsdGreaterThan(@PathVariable Integer precioUnitUsd) {
        return miniBarService.findByPrecioUnitUsdGreaterThan(precioUnitUsd);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MiniBarResponse create(@Valid @RequestBody MiniBarRequest request) {
        return miniBarService.create(request);
    }

    @PutMapping("/{id}")
    public MiniBarResponse update(@PathVariable Long id, @Valid @RequestBody MiniBarRequest request) {
        return miniBarService.update(id, request);
    }

    @PatchMapping("/{id}/cantidad")
    public MiniBarResponse actualizarCantidad(@PathVariable Long id, @RequestParam Integer cantidad) {
        return miniBarService.actualizarCantidad(id, cantidad);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        miniBarService.deleteById(id);
    }
}
