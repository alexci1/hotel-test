package cl.hilton.reportes.controller;

import cl.hilton.reportes.dto.ReporteRequest;
import cl.hilton.reportes.dto.ReporteResponse;
import cl.hilton.reportes.service.ReporteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping
    public List<ReporteResponse> listar() {
        return reporteService.listar();
    }

    @GetMapping("/{id}")
    public ReporteResponse buscarPorId(@PathVariable Integer id) {
        return reporteService.buscarPorId(id);
    }

    @GetMapping("/codigo/{codigo}")
    public ReporteResponse buscarPorCodigo(@PathVariable String codigo) {
        return reporteService.buscarPorCodigo(codigo);
    }

    @GetMapping("/tipo/{tipo}")
    public List<ReporteResponse> buscarPorTipo(@PathVariable String tipo) {
        return reporteService.buscarPorTipo(tipo);
    }

    @GetMapping("/frecuencia/{frecuencia}")
    public List<ReporteResponse> buscarPorFrecuencia(@PathVariable String frecuencia) {
        return reporteService.buscarPorFrecuencia(frecuencia);
    }

    @GetMapping("/activo")
    public List<ReporteResponse> buscarPorActivo(@RequestParam Boolean activo) {
        return reporteService.buscarPorActivo(activo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReporteResponse crear(@Valid @RequestBody ReporteRequest request) {
        return reporteService.crear(request);
    }

    @PutMapping("/{id}")
    public ReporteResponse actualizar(@PathVariable Integer id, @Valid @RequestBody ReporteRequest request) {
        return reporteService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Integer id) {
        reporteService.eliminar(id);
    }
}

