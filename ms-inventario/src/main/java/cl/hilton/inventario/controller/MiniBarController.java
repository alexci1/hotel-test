package cl.hilton.inventario.controller;


import cl.triskledu.inventario.dto.request.MinibarRequest;
import cl.triskledu.inventario.dto.response.MinibarResponse;
import cl.triskledu.inventario.service.MinibarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/minibares")
@RequiredArgsConstructor
public class MinibarController {
    private final MinibarService service;

    @GetMapping
    public List<MinibarResponse> listar() { return service.listar(); }

    @GetMapping("/{id}")
    public MinibarResponse buscar(@PathVariable Integer id) { return service.buscarPorId(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MinibarResponse crear(@Valid @RequestBody MinibarRequest request) { return service.crear(request); }

    @PutMapping("/{id}")
    public MinibarResponse actualizar(@PathVariable Integer id, @Valid @RequestBody MinibarRequest request) { return service.actualizar(id, request); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) { service.eliminar(id); }
}

