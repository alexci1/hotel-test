package cl.hilton.housekeeping.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.housekeeping.dto.ReporteRequest;
import cl.hilton.housekeeping.dto.ReporteResponse;
import cl.hilton.housekeeping.mapper.ReporteMapper;
import cl.hilton.housekeeping.model.Asignacion;
import cl.hilton.housekeeping.model.Reporte;
import cl.hilton.housekeeping.repository.AsignacionRepository;
import cl.hilton.housekeeping.repository.ReporteRepository;
import cl.hilton.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class ReporteService {

    private final ReporteRepository reporteRepository;
    private final AsignacionRepository asignacionRepository;
    private final ReporteMapper reporteMapper;

    public List<ReporteResponse> findAll() {
        return reporteMapper.toResponseList(reporteRepository.findAll());
    }

    public ReporteResponse findById(Long id) {
        Reporte reporte = getReporteById(id);
        return reporteMapper.toResponse(reporte);
    }

    public ReporteResponse findByAsignacionId(Long asignacionId) {
        Long idAsignacion = validarId(asignacionId);

        Reporte reporte = reporteRepository.findByAsignacionId(idAsignacion)
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado para asignacion: " + idAsignacion));

        return reporteMapper.toResponse(reporte);
    }

    public List<ReporteResponse> findByAprobado(Boolean aprobado) {
        Boolean estado = validarBoolean(aprobado, "aprobado");
        return reporteMapper.toResponseList(reporteRepository.findByAprobado(estado));
    }

    public List<ReporteResponse> findByInspector(String inspector) {
        String inspectorReporte = validarTexto(inspector, "inspector");
        return reporteMapper.toResponseList(reporteRepository.findByInspector(inspectorReporte));
    }

    @Transactional
    public ReporteResponse create(ReporteRequest request) {
        Long asignacionId = validarId(request.getAsignacionId());

        if (reporteRepository.existsByAsignacionId(asignacionId)) {
            throw new IllegalArgumentException("Ya existe un reporte para la asignacion: " + asignacionId);
        }

        Asignacion asignacion = getAsignacionById(asignacionId);
        Reporte reporte = reporteMapper.toEntity(request, asignacion);
        reporte.setAprobado(request.getAprobado() != null ? request.getAprobado() : Boolean.FALSE);
        reporte.setInspeccionadoEn(LocalDate.now());

        Reporte reporteGuardado = reporteRepository.save(reporte);

        return reporteMapper.toResponse(reporteGuardado);
    }

    @Transactional
    public ReporteResponse update(Long id, ReporteRequest request) {
        Long reporteId = validarId(id);
        Long asignacionId = validarId(request.getAsignacionId());

        Reporte reporte = getReporteById(reporteId);
        Asignacion asignacion = getAsignacionById(asignacionId);
        Boolean aprobadoActual = reporte.getAprobado();

        reporteRepository.findByAsignacionId(asignacionId)
                .filter(existente -> !existente.getId().equals(reporteId))
                .ifPresent(existente -> {
                    throw new IllegalArgumentException("Ya existe un reporte para la asignacion: " + asignacionId);
                });

        reporteMapper.updateEntity(request, asignacion, reporte);
        reporte.setAprobado(request.getAprobado() != null ? request.getAprobado() : aprobadoActual);
        reporte.setInspeccionadoEn(LocalDate.now());

        Reporte reporteActualizado = reporteRepository.save(reporte);

        return reporteMapper.toResponse(reporteActualizado);
    }

    @Transactional
    public void deleteById(Long id) {
        Long reporteId = validarId(id);
        getReporteById(reporteId);
        reporteRepository.deleteById(reporteId);
    }

    private Reporte getReporteById(Long id) {
        Long reporteId = validarId(id);

        return reporteRepository.findById(reporteId)
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado con id: " + reporteId));
    }

    private Asignacion getAsignacionById(Long asignacionId) {
        Long idAsignacion = validarId(asignacionId);

        return asignacionRepository.findById(idAsignacion)
                .orElseThrow(() -> new EntityNotFoundException("Asignacion no encontrada con id: " + idAsignacion));
    }

    private Long validarId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        return id;
    }

    private Boolean validarBoolean(Boolean valor, String campo) {
        if (valor == null) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser nulo");
        }
        return valor;
    }

    private String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser nulo o vacio");
        }
        return valor;
    }
}
