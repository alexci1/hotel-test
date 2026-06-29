package cl.hilton.notificaciones.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.hilton.notificaciones.dto.PlantillaRequest;
import cl.hilton.notificaciones.dto.PlantillaResponse;
import cl.hilton.notificaciones.service.PlantillaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/plantillas")
@RequiredArgsConstructor
public class PlantillaController {

    private final PlantillaService plantillaService;

    @GetMapping
    public List<PlantillaResponse> findAll() {
        return plantillaService.findAll();
    }

    @GetMapping("/{id}")
    public PlantillaResponse findById(@PathVariable Long id) {
        return plantillaService.findById(id);
    }

    @GetMapping("/codigo/{codigo}")
    public PlantillaResponse findByCodigo(@PathVariable String codigo) {
        return plantillaService.findByCodigo(codigo);
    }

    @GetMapping("/canal/{canal}")
    public List<PlantillaResponse> findByCanal(@PathVariable String canal) {
        return plantillaService.findByCanal(canal);
    }

    @GetMapping("/activa/{activa}")
    public List<PlantillaResponse> findByActiva(@PathVariable Boolean activa) {
        return plantillaService.findByActiva(activa);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlantillaResponse create(@Valid @RequestBody PlantillaRequest request) {
        return plantillaService.create(request);
    }

    @PutMapping("/{id}")
    public PlantillaResponse update(
            @PathVariable Long id,
            @Valid @RequestBody PlantillaRequest request) {
        return plantillaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        plantillaService.deleteById(id);
    }
}
