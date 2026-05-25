package cl.hilton.inventario.controller;

import cl.hilton.inventario.dto.MiniBarRequest;
import cl.hilton.inventario.dto.MiniBarResponse;
import cl.hilton.inventario.service.MiniBarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/minibares")
@RequiredArgsConstructor
public class MiniBarController {

    private final MiniBarService service;

    @GetMapping
    public List<MiniBarResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public MiniBarResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MiniBarResponse crear(@Valid @RequestBody MiniBarRequest request) {
        return service.crear(request);
    }

    @PutMapping("/{id}")
    public MiniBarResponse actualizar(@PathVariable Long id, @Valid @RequestBody MiniBarRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}