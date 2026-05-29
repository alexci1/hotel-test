package cl.hilton.housekeeping.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import cl.hilton.housekeeping.dto.ReporteRequest;
import cl.hilton.housekeeping.dto.ReporteResponse;
import cl.hilton.housekeeping.mapper.ReporteMapper;
import cl.hilton.housekeeping.model.Asignacion;
import cl.hilton.housekeeping.model.Reporte;
import cl.hilton.housekeeping.repository.AsignacionRepository;
import cl.hilton.housekeeping.repository.ReporteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final ReporteRepository reporteRepository;
    private final AsignacionRepository asignacionRepository;
    private final ReporteMapper reporteMapper;

    public List<ReporteResponse> findAll() {
        return reporteMapper.toResponseList(reporteRepository.findAll());
    }

    public ReporteResponse findById(Long id) {
        return reporteMapper.toResponse(getReporte(id));
    }

    public ReporteResponse findByAsignacionId(Long asignacionId) {
        Long idAsignacion = Objects.requireNonNull(asignacionId);
        Reporte reporte = reporteRepository.findByAsignacionId(idAsignacion)
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado para asignacion: " + idAsignacion));

        return reporteMapper.toResponse(reporte);
    }

    public List<ReporteResponse> findByAprobado(Boolean aprobado) {
        Boolean estado = Objects.requireNonNull(aprobado);
        return reporteMapper.toResponseList(reporteRepository.findByAprobado(estado));
    }

    public List<ReporteResponse> findByInspector(String inspector) {
        String inspectorReporte = Objects.requireNonNull(inspector);
        return reporteMapper.toResponseList(reporteRepository.findByInspector(inspectorReporte));
    }

    public ReporteResponse create(ReporteRequest request) {
        Long asignacionId = Objects.requireNonNull(request.getAsignacionId());

        if (reporteRepository.existsByAsignacionId(asignacionId)) {
            throw new IllegalArgumentException("Ya existe un reporte para la asignacion: " + asignacionId);
        }

        Asignacion asignacion = getAsignacion(asignacionId);
        Reporte reporte = reporteMapper.toEntity(request, asignacion);
        reporte.setAprobado(request.getAprobado() != null ? request.getAprobado() : Boolean.FALSE);
        reporte.setInspeccionadoEn(LocalDate.now());

        Reporte saved = reporteRepository.save(Objects.requireNonNull(reporte));
        return reporteMapper.toResponse(saved);
    }

    public ReporteResponse update(Long id, ReporteRequest request) {
        Long reporteId = Objects.requireNonNull(id);
        Long asignacionId = Objects.requireNonNull(request.getAsignacionId());

        Reporte reporte = getReporte(reporteId);
        Asignacion asignacion = getAsignacion(asignacionId);

        reporteRepository.findByAsignacionId(asignacionId)
                .filter(existente -> !existente.getId().equals(reporteId))
                .ifPresent(existente -> {
                    throw new IllegalArgumentException("Ya existe un reporte para la asignacion: " + asignacionId);
                });

        reporteMapper.updateEntity(request, asignacion, reporte);
        reporte.setAprobado(request.getAprobado() != null ? request.getAprobado() : Boolean.FALSE);
        reporte.setInspeccionadoEn(LocalDate.now());

        Reporte saved = reporteRepository.save(Objects.requireNonNull(reporte));
        return reporteMapper.toResponse(saved);
    }

    public void deleteById(Long id) {
        Reporte reporte = getReporte(id);
        reporteRepository.delete(Objects.requireNonNull(reporte));
    }

    private Reporte getReporte(Long id) {
        Long reporteId = Objects.requireNonNull(id);
        return reporteRepository.findById(reporteId)
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado: " + reporteId));
    }

    private Asignacion getAsignacion(Long asignacionId) {
        Long idAsignacion = Objects.requireNonNull(asignacionId);
        return asignacionRepository.findById(idAsignacion)
                .orElseThrow(() -> new EntityNotFoundException("Asignacion no encontrada: " + idAsignacion));
    }
}