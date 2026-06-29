package cl.hilton.inventario.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.hilton.inventario.dto.MovimientoRequest;
import cl.hilton.inventario.dto.MovimientoResponse;
import cl.hilton.inventario.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @GetMapping
    public List<MovimientoResponse> findAll() {
        return movimientoService.findAll();
    }

    @GetMapping("/{id}")
    public MovimientoResponse findById(@PathVariable Long id) {
        return movimientoService.findById(id);
    }

    @GetMapping("/producto/{codigoProducto}")
    public List<MovimientoResponse> findByCodigoProducto(@PathVariable String codigoProducto) {
        return movimientoService.findByCodigoProducto(codigoProducto);
    }

    @GetMapping("/tipo/{tipo}")
    public List<MovimientoResponse> findByTipo(@PathVariable String tipo) {
        return movimientoService.findByTipo(tipo);
    }

    @GetMapping("/registrado-por/{registradoPor}")
    public List<MovimientoResponse> findByRegistradoPor(@PathVariable String registradoPor) {
        return movimientoService.findByRegistradoPor(registradoPor);
    }

    @GetMapping("/fecha/{registradoEn}")
    public List<MovimientoResponse> findByRegistradoEn(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate registradoEn) {
        return movimientoService.findByRegistradoEn(registradoEn);
    }

    @GetMapping("/rango")
    public List<MovimientoResponse> findByRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return movimientoService.findByRangoFechas(desde, hasta);
    }

    @GetMapping("/cantidad-mayor/{cantidad}")
    public List<MovimientoResponse> findByCantidadGreaterThan(@PathVariable Integer cantidad) {
        return movimientoService.findByCantidadGreaterThan(cantidad);
    }

    @GetMapping("/cantidad-menor/{cantidad}")
    public List<MovimientoResponse> findByCantidadLessThan(@PathVariable Integer cantidad) {
        return movimientoService.findByCantidadLessThan(cantidad);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimientoResponse create(@Valid @RequestBody MovimientoRequest request) {
        return movimientoService.create(request);
    }

    @PutMapping("/{id}")
    public MovimientoResponse update(@PathVariable Long id, @Valid @RequestBody MovimientoRequest request) {
        return movimientoService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        movimientoService.deleteById(id);
    }
}
