package cl.hilton.reportes.controller;

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

import cl.hilton.reportes.dto.ReporteRequest;
import cl.hilton.reportes.dto.ReporteResponse;
import cl.hilton.reportes.service.ReporteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reportes/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping
    public List<ReporteResponse> findAll() {
        return reporteService.findAll();
    }

    @GetMapping("/{id}")
    public ReporteResponse findById(@PathVariable Long id) {
        return reporteService.findById(id);
    }

    @GetMapping("/codigo/{codigo}")
    public ReporteResponse findByCodigo(@PathVariable String codigo) {
        return reporteService.findByCodigo(codigo);
    }

    @GetMapping("/tipo/{tipo}")
    public List<ReporteResponse> findByTipo(@PathVariable String tipo) {
        return reporteService.findByTipo(tipo);
    }

    @GetMapping("/frecuencia/{frecuencia}")
    public List<ReporteResponse> findByFrecuencia(@PathVariable String frecuencia) {
        return reporteService.findByFrecuencia(frecuencia);
    }

    @GetMapping("/activo/{activo}")
    public List<ReporteResponse> findByActivo(@PathVariable Boolean activo) {
        return reporteService.findByActivo(activo);
    }

    @GetMapping("/buscar")
    public List<ReporteResponse> findByNombre(@RequestParam String nombre) {
        return reporteService.findByNombre(nombre);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReporteResponse create(@Valid @RequestBody ReporteRequest request) {
        return reporteService.create(request);
    }

    @PutMapping("/{id}")
    public ReporteResponse update(
            @PathVariable Long id,
            @Valid @RequestBody ReporteRequest request) {
        return reporteService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        reporteService.deleteById(id);
    }
}
