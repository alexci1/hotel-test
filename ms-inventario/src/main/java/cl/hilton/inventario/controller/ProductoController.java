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

import cl.hilton.inventario.dto.ProductoRequest;
import cl.hilton.inventario.dto.ProductoResponse;
import cl.hilton.inventario.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public List<ProductoResponse> findAll() {
        return productoService.findAll();
    }

    @GetMapping("/{id}")
    public ProductoResponse findById(@PathVariable Long id) {
        return productoService.findById(id);
    }

    @GetMapping("/codigo/{codigoProducto}")
    public ProductoResponse findByCodigoProducto(@PathVariable String codigoProducto) {
        return productoService.findByCodigoProducto(codigoProducto);
    }

    @GetMapping("/categoria/{categoria}")
    public List<ProductoResponse> findByCategoria(@PathVariable String categoria) {
        return productoService.findByCategoria(categoria);
    }

    @GetMapping("/unidad/{unidad}")
    public List<ProductoResponse> findByUnidad(@PathVariable String unidad) {
        return productoService.findByUnidad(unidad);
    }

    @GetMapping("/buscar")
    public List<ProductoResponse> findByNombre(@RequestParam String nombre) {
        return productoService.findByNombre(nombre);
    }

    @GetMapping("/stock-menor-igual/{stockActual}")
    public List<ProductoResponse> findByStockActualLessThanEqual(@PathVariable Integer stockActual) {
        return productoService.findByStockActualLessThanEqual(stockActual);
    }

    @GetMapping("/stock-menor/{stockActual}")
    public List<ProductoResponse> findByStockActualLessThan(@PathVariable Integer stockActual) {
        return productoService.findByStockActualLessThan(stockActual);
    }

    @GetMapping("/stock-mayor/{stockActual}")
    public List<ProductoResponse> findByStockActualGreaterThan(@PathVariable Integer stockActual) {
        return productoService.findByStockActualGreaterThan(stockActual);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductoResponse create(@Valid @RequestBody ProductoRequest request) {
        return productoService.create(request);
    }

    @PutMapping("/{id}")
    public ProductoResponse update(@PathVariable Long id, @Valid @RequestBody ProductoRequest request) {
        return productoService.update(id, request);
    }

    @PatchMapping("/{id}/stock")
    public ProductoResponse ajustarStock(@PathVariable Long id, @RequestParam Integer cantidad) {
        return productoService.ajustarStock(id, cantidad);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        productoService.deleteById(id);
    }
}
