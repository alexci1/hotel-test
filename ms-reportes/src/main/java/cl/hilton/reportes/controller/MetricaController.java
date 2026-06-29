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

import cl.hilton.reportes.dto.MetricaRequest;
import cl.hilton.reportes.dto.MetricaResponse;
import cl.hilton.reportes.service.MetricaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/metricas")
@RequiredArgsConstructor
public class MetricaController {

    private final MetricaService metricaService;

    @GetMapping
    public List<MetricaResponse> findAll() {
        return metricaService.findAll();
    }

    @GetMapping("/{id}")
    public MetricaResponse findById(@PathVariable Long id) {
        return metricaService.findById(id);
    }

    @GetMapping("/reporte/{codigoReporte}")
    public List<MetricaResponse> findByReporte(@PathVariable String codigoReporte) {
        return metricaService.findByReporte(codigoReporte);
    }

    @GetMapping("/periodo/{periodo}")
    public List<MetricaResponse> findByPeriodo(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate periodo) {
        return metricaService.findByPeriodo(periodo);
    }

    @GetMapping("/rango")
    public List<MetricaResponse> findByRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return metricaService.findByRangoFechas(desde, hasta);
    }

    @GetMapping("/nombre/{nombreMetrica}")
    public List<MetricaResponse> findByNombreMetrica(@PathVariable String nombreMetrica) {
        return metricaService.findByNombreMetrica(nombreMetrica);
    }

    @GetMapping("/calculado/{calculadoEn}")
    public List<MetricaResponse> findByCalculadoEn(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate calculadoEn) {
        return metricaService.findByCalculadoEn(calculadoEn);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MetricaResponse create(@Valid @RequestBody MetricaRequest request) {
        return metricaService.create(request);
    }

    @PutMapping("/{id}")
    public MetricaResponse update(
            @PathVariable Long id,
            @Valid @RequestBody MetricaRequest request) {
        return metricaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        metricaService.deleteById(id);
    }
}
