package cl.hilton.inventario.controller;


import cl.triskledu.inventario.dto.request.MovimientoRequest;
import cl.triskledu.inventario.dto.response.MovimientoResponse;
import cl.triskledu.inventario.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoController {
    private final MovimientoService service;

    @GetMapping
    public List<MovimientoResponse> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public MovimientoResponse buscar(@PathVariable Integer id) { return service.buscarPorId(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimientoResponse crear(@Valid @RequestBody MovimientoRequest request) { return service.crear(request); }

    @PutMapping("/{id}")
    public MovimientoResponse actualizar(@PathVariable Integer id, @Valid @RequestBody MovimientoRequest request) { return service.actualizar(id, request); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) { service.eliminar(id); }
}
