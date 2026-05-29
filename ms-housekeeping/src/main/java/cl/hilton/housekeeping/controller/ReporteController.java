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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.hilton.housekeeping.dto.ReporteRequest;
import cl.hilton.housekeeping.dto.ReporteResponse;
import cl.hilton.housekeeping.service.ReporteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/housekeeping/reportes")
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

    @GetMapping("/asignacion/{asignacionId}")
    public ReporteResponse findByAsignacionId(@PathVariable Long asignacionId) {
        return reporteService.findByAsignacionId(asignacionId);
    }

    @GetMapping("/aprobado/{aprobado}")
    public List<ReporteResponse> findByAprobado(@PathVariable Boolean aprobado) {
        return reporteService.findByAprobado(aprobado);
    }

    @GetMapping("/inspector/{inspector}")
    public List<ReporteResponse> findByInspector(@PathVariable String inspector) {
        return reporteService.findByInspector(inspector);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReporteResponse create(@Valid @RequestBody ReporteRequest request) {
        return reporteService.create(request);
    }

    @PutMapping("/{id}")
    public ReporteResponse update(@PathVariable Long id, @Valid @RequestBody ReporteRequest request) {
        return reporteService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        reporteService.deleteById(id);
    }
}