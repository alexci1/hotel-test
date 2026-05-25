package cl.hilton.reportes.controller;

import cl.hilton.reportes.dto.KpiRequest;
import cl.hilton.reportes.dto.KpiResponse;
import cl.hilton.reportes.service.KpiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kpis")
@RequiredArgsConstructor
public class KpiController {

    private final KpiService kpiService;

    @GetMapping
    public List<KpiResponse> listar() {
        return kpiService.listar();
    }

    @GetMapping("/{id}")
    public KpiResponse buscarPorId(@PathVariable Long id) {
        return kpiService.buscarPorId(id);
    }

    @GetMapping("/nombre-exacto/{nombre}")
    public KpiResponse buscarPorNombreExacto(@PathVariable String nombre) {
        return kpiService.buscarPorNombreExacto(nombre);
    }

    @GetMapping("/buscar")
    public List<KpiResponse> buscarPorNombre(@RequestParam String nombre) {
        return kpiService.buscarPorNombre(nombre);
    }

    @GetMapping("/periodo/{periodo}")
    public List<KpiResponse> buscarPorPeriodo(@PathVariable String periodo) {
        return kpiService.buscarPorPeriodo(periodo);
    }

    @GetMapping("/unidad/{unidad}")
    public List<KpiResponse> buscarPorUnidad(@PathVariable String unidad) {
        return kpiService.buscarPorUnidad(unidad);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KpiResponse crear(@Valid @RequestBody KpiRequest request) {
        return kpiService.crear(request);
    }

    @PutMapping("/{id}")
    public KpiResponse actualizar(@PathVariable Long id, @Valid @RequestBody KpiRequest request) {
        return kpiService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        kpiService.eliminar(id);
    }
}