package cl.hilton.inventario.controller;

import cl.hilton.inventario.dto.MovimientoRequest;
import cl.hilton.inventario.dto.MovimientoResponse;
import cl.hilton.inventario.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService service;

    @GetMapping
    public List<MovimientoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public MovimientoResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/producto/{codigoProducto}")
    public List<MovimientoResponse> buscarPorProducto(@PathVariable String codigoProducto) {
        return service.buscarPorProducto(codigoProducto);
    }

    @GetMapping("/tipo/{tipo}")
    public List<MovimientoResponse> buscarPorTipo(@PathVariable String tipo) {
        return service.buscarPorTipo(tipo);
    }

    @GetMapping("/registrado-por/{registradoPor}")
    public List<MovimientoResponse> buscarPorRegistradoPor(@PathVariable String registradoPor) {
        return service.buscarPorRegistradoPor(registradoPor);
    }

    @GetMapping("/fechas")
    public List<MovimientoResponse> buscarPorFechas(
            @RequestParam LocalDate desde,
            @RequestParam LocalDate hasta
    ) {
        return service.buscarPorFechas(desde, hasta);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimientoResponse crear(@Valid @RequestBody MovimientoRequest request) {
        return service.crear(request);
    }

    @PutMapping("/{id}")
    public MovimientoResponse actualizar(@PathVariable Long id, @Valid @RequestBody MovimientoRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}