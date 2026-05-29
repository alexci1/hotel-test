package cl.hilton.housekeeping.service;

import java.time.LocalDate;
import java.util.List;

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
        Reporte reporte = reporteRepository.findByAsignacionId(asignacionId)
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado para asignacion: " + asignacionId));

        return reporteMapper.toResponse(reporte);
    }

    public List<ReporteResponse> findByAprobado(Boolean aprobado) {
        return reporteMapper.toResponseList(reporteRepository.findByAprobado(aprobado));
    }

    public List<ReporteResponse> findByInspector(String inspector) {
        return reporteMapper.toResponseList(reporteRepository.findByInspector(inspector));
    }

    public ReporteResponse create(ReporteRequest request) {
        if (reporteRepository.existsByAsignacionId(request.getAsignacionId())) {
            throw new IllegalArgumentException("Ya existe un reporte para la asignacion: " + request.getAsignacionId());
        }

        Asignacion asignacion = getAsignacion(request.getAsignacionId());
        Reporte reporte = reporteMapper.toEntity(request, asignacion);
        reporte.setAprobado(request.getAprobado() != null ? request.getAprobado() : Boolean.FALSE);
        reporte.setInspeccionadoEn(LocalDate.now());

        Reporte saved = reporteRepository.save(reporte);
        return reporteMapper.toResponse(saved);
    }

    public ReporteResponse update(Long id, ReporteRequest request) {
        Reporte reporte = getReporte(id);
        Asignacion asignacion = getAsignacion(request.getAsignacionId());

        reporteRepository.findByAsignacionId(request.getAsignacionId())
                .filter(existente -> !existente.getId().equals(id))
                .ifPresent(existente -> {
                    throw new IllegalArgumentException("Ya existe un reporte para la asignacion: " + request.getAsignacionId());
                });

        reporteMapper.updateEntity(request, asignacion, reporte);
        reporte.setAprobado(request.getAprobado() != null ? request.getAprobado() : Boolean.FALSE);
        reporte.setInspeccionadoEn(LocalDate.now());

        Reporte saved = reporteRepository.save(reporte);
        return reporteMapper.toResponse(saved);
    }

    public void deleteById(Long id) {
        Reporte reporte = getReporte(id);
        reporteRepository.delete(reporte);
    }

    private Reporte getReporte(Long id) {
        return reporteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado: " + id));
    }

    private Asignacion getAsignacion(Long asignacionId) {
        return asignacionRepository.findById(asignacionId)
                .orElseThrow(() -> new EntityNotFoundException("Asignacion no encontrada: " + asignacionId));
    }
}