package cl.hilton.reportes.controller;

import cl.hilton.reportes.dto.MetricaRequest;
import cl.hilton.reportes.dto.MetricaResponse;
import cl.hilton.reportes.service.MetricaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/metricas")
@RequiredArgsConstructor
public class MetricaController {

    private final MetricaService metricaService;

    @GetMapping
    public List<MetricaResponse> listar() {
        return metricaService.listar();
    }

    @GetMapping("/{id}")
    public MetricaResponse buscarPorId(@PathVariable Long id) {
        return metricaService.buscarPorId(id);
    }

    @GetMapping("/reporte/{codigoReporte}")
    public List<MetricaResponse> buscarPorReporte(@PathVariable String codigoReporte) {
        return metricaService.buscarPorReporte(codigoReporte);
    }

    @GetMapping("/periodo")
    public List<MetricaResponse> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodo
    ) {
        return metricaService.buscarPorPeriodo(periodo);
    }

    @GetMapping("/rango")
    public List<MetricaResponse> buscarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta
    ) {
        return metricaService.buscarPorRangoFechas(desde, hasta);
    }

    @GetMapping("/nombre/{nombreMetrica}")
    public List<MetricaResponse> buscarPorNombreMetrica(@PathVariable String nombreMetrica) {
        return metricaService.buscarPorNombreMetrica(nombreMetrica);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MetricaResponse crear(@Valid @RequestBody MetricaRequest request) {
        return metricaService.crear(request);
    }

    @PutMapping("/{id}")
    public MetricaResponse actualizar(@PathVariable Long id, @Valid @RequestBody MetricaRequest request) {
        return metricaService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        metricaService.eliminar(id);
    }
}