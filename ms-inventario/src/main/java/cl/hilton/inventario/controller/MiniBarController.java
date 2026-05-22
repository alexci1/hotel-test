package cl.hilton.inventario.controller;

import cl.hilton.inventario.dto.MinibarRequest;
import cl.hilton.inventario.dto.MinibarResponse;
import cl.hilton.inventario.service.MinibarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/minibares")
@RequiredArgsConstructor
public class MiniBarController {

    private final MinibarService service;

    @GetMapping
    public List<MinibarResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public MinibarResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MinibarResponse crear(@Valid @RequestBody MinibarRequest request) {
        return service.crear(request);
    }

    @PutMapping("/{id}")
    public MinibarResponse actualizar(@PathVariable Long id, @Valid @RequestBody MinibarRequest request) {
        return service.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}