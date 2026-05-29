package cl.hilton.tarifas.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
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

import cl.hilton.tarifas.dto.TemporadaRequest;
import cl.hilton.tarifas.dto.TemporadaResponse;
import cl.hilton.tarifas.service.TemporadaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tarifas/temporadas")
@RequiredArgsConstructor
public class TemporadaController {

    private final TemporadaService temporadaService;

    @GetMapping
    public List<TemporadaResponse> findAll() {
        return temporadaService.findAll();
    }

    @GetMapping("/{id}")
    public TemporadaResponse findById(@PathVariable Long id) {
        return temporadaService.findById(id);
    }

    @GetMapping("/codigo/{codigo}")
    public TemporadaResponse findByCodigo(@PathVariable String codigo) {
        return temporadaService.findByCodigo(codigo);
    }

    @GetMapping("/buscar")
    public List<TemporadaResponse> findByNombre(@RequestParam String nombre) {
        return temporadaService.findByNombre(nombre);
    }

    @GetMapping("/inicio-antes-de/{fechaInicio}")
    public List<TemporadaResponse> findByFechaInicioBefore(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio) {
        return temporadaService.findByFechaInicioBefore(fechaInicio);
    }

    @GetMapping("/fin-despues-de/{fechaFin}")
    public List<TemporadaResponse> findByFechaFinAfter(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return temporadaService.findByFechaFinAfter(fechaFin);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TemporadaResponse create(@Valid @RequestBody TemporadaRequest request) {
        return temporadaService.create(request);
    }

    @PutMapping("/{id}")
    public TemporadaResponse update(
            @PathVariable Long id,
            @Valid @RequestBody TemporadaRequest request) {
        return temporadaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        temporadaService.deleteById(id);
    }
}
