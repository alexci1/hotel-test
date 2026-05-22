package cl.hilton.housekeeping.controller;

import cl.hilton.housekeeping.dto.ReporteRequest;
import cl.hilton.housekeeping.dto.ReporteResponse;
import cl.hilton.housekeeping.service.ReporteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping
    public List<ReporteResponse> listar() {
        return reporteService.listar();
    }

    @GetMapping("/{id}")
    public ReporteResponse buscarPorId(@PathVariable Long id) {
        return reporteService.buscarPorId(id);
    }

    @GetMapping("/asignacion/{asignacionId}")
    public ReporteResponse buscarPorAsignacion(@PathVariable Long asignacionId) {
        return reporteService.buscarPorAsignacion(asignacionId);
    }

    @GetMapping("/aprobado/{aprobado}")
    public List<ReporteResponse> buscarPorAprobado(@PathVariable Boolean aprobado) {
        return reporteService.buscarPorAprobado(aprobado);
    }

    @GetMapping("/inspector/{inspector}")
    public List<ReporteResponse> buscarPorInspector(@PathVariable String inspector) {
        return reporteService.buscarPorInspector(inspector);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReporteResponse crear(@Valid @RequestBody ReporteRequest request) {
        return reporteService.crear(request);
    }

    @PutMapping("/{id}")
    public ReporteResponse actualizar(@PathVariable Long id, @Valid @RequestBody ReporteRequest request) {
        return reporteService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        reporteService.eliminar(id);
    }
}