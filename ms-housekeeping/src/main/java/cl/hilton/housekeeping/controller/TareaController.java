package cl.hilton.housekeeping.controller;

import java.util.List;

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

import cl.hilton.housekeeping.dto.TareaRequest;
import cl.hilton.housekeeping.dto.TareaResponse;
import cl.hilton.housekeeping.service.TareaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tareas")
@RequiredArgsConstructor
public class TareaController {

    private final TareaService tareaService;

    @GetMapping
    public List<TareaResponse> findAll() {
        return tareaService.findAll();
    }

    @GetMapping("/{id}")
    public TareaResponse findById(@PathVariable Long id) {
        return tareaService.findById(id);
    }

    @GetMapping("/codigo/{codigo}")
    public TareaResponse findByCodigo(@PathVariable String codigo) {
        return tareaService.findByCodigo(codigo);
    }

    @GetMapping("/activa/{activa}")
    public List<TareaResponse> findByActiva(@PathVariable Boolean activa) {
        return tareaService.findByActiva(activa);
    }

    @GetMapping("/descripcion")
    public List<TareaResponse> findByDescripcion(@RequestParam String descripcion) {
        return tareaService.findByDescripcion(descripcion);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TareaResponse create(@Valid @RequestBody TareaRequest request) {
        return tareaService.create(request);
    }

    @PutMapping("/{id}")
    public TareaResponse update(@PathVariable Long id, @Valid @RequestBody TareaRequest request) {
        return tareaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        tareaService.deleteById(id);
    }
}