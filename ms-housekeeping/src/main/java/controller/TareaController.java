package cl.hilton.housekeeping.controller;

import cl.hilton.housekeeping.dto.TareaRequest;
import cl.hilton.housekeeping.dto.TareaResponse;
import cl.hilton.housekeeping.service.TareaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @GetMapping
    public List<TareaResponse> listar() {
        return tareaService.listar();
    }

    @GetMapping("/{id}")
    public TareaResponse buscarPorId(@PathVariable Long id) {
        return tareaService.buscarPorId(id);
    }

    @GetMapping("/codigo/{codigo}")
    public TareaResponse buscarPorCodigo(@PathVariable String codigo) {
        return tareaService.buscarPorCodigo(codigo);
    }

    @GetMapping("/activa/{activa}")
    public List<TareaResponse> buscarPorActiva(@PathVariable Boolean activa) {
        return tareaService.buscarPorActiva(activa);
    }

    @GetMapping("/descripcion")
    public List<TareaResponse> buscarPorDescripcion(@RequestParam String descripcion) {
        return tareaService.buscarPorDescripcion(descripcion);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TareaResponse crear(@Valid @RequestBody TareaRequest request) {
        return tareaService.crear(request);
    }

    @PutMapping("/{id}")
    public TareaResponse actualizar(@PathVariable Long id, @Valid @RequestBody TareaRequest request) {
        return tareaService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        tareaService.eliminar(id);
    }
}