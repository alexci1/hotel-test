package cl.hilton.reportes.controller;

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

import cl.hilton.reportes.dto.KpiRequest;
import cl.hilton.reportes.dto.KpiResponse;
import cl.hilton.reportes.service.KpiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/reportes/kpis")
@RequiredArgsConstructor
public class KpiController {

    private final KpiService kpiService;

    @GetMapping
    public List<KpiResponse> findAll() {
        return kpiService.findAll();
    }

    @GetMapping("/{id}")
    public KpiResponse findById(@PathVariable Long id) {
        return kpiService.findById(id);
    }

    @GetMapping("/nombre/{nombre}")
    public KpiResponse findByNombre(@PathVariable String nombre) {
        return kpiService.findByNombre(nombre);
    }

    @GetMapping("/reporte/{codigoReporte}")
    public List<KpiResponse> findByReporte(@PathVariable String codigoReporte) {
        return kpiService.findByReporte(codigoReporte);
    }

    @GetMapping("/buscar")
    public List<KpiResponse> findByNombreContaining(@RequestParam String nombre) {
        return kpiService.findByNombreContaining(nombre);
    }

    @GetMapping("/periodo/{periodo}")
    public List<KpiResponse> findByPeriodo(@PathVariable String periodo) {
        return kpiService.findByPeriodo(periodo);
    }

    @GetMapping("/unidad/{unidad}")
    public List<KpiResponse> findByUnidad(@PathVariable String unidad) {
        return kpiService.findByUnidad(unidad);
    }

    @GetMapping("/actualizado/{actualizadoEn}")
    public List<KpiResponse> findByActualizadoEn(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate actualizadoEn) {
        return kpiService.findByActualizadoEn(actualizadoEn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KpiResponse create(@Valid @RequestBody KpiRequest request) {
        return kpiService.create(request);
    }

    @PutMapping("/{id}")
    public KpiResponse update(
            @PathVariable Long id,
            @Valid @RequestBody KpiRequest request) {
        return kpiService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        kpiService.deleteById(id);
    }
}