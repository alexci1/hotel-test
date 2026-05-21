package cl.hilton.inventario.controller;


import cl.triskledu.inventario.dto.request.ProductoRequest;
import cl.triskledu.inventario.dto.response.ProductoResponse;
import cl.triskledu.inventario.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {
    private final ProductoService service;

    @GetMapping
    public List<ProductoResponse> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public ProductoResponse buscar(@PathVariable Integer id) { return service.buscarPorId(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductoResponse crear(@Valid @RequestBody ProductoRequest request) { return service.crear(request); }

    @PutMapping("/{id}")
    public ProductoResponse actualizar(@PathVariable Integer id, @Valid @RequestBody ProductoRequest request) { return service.actualizar(id, request); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) { service.eliminar(id); }
}

