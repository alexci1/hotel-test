package cl.hilton.housekeeping.service;

import cl.hilton.housekeeping.dto.ReporteRequest;
import cl.hilton.housekeeping.dto.ReporteResponse;
import cl.hilton.housekeeping.mapper.ReporteMapper;
import cl.hilton.housekeeping.model.Asignacion;
import cl.hilton.housekeeping.model.Reporte;
import cl.hilton.housekeeping.repository.AsignacionRepository;
import cl.hilton.housekeeping.repository.ReporteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteService {

    private final ReporteRepository reporteRepository;
    private final AsignacionRepository asignacionRepository;
    private final ReporteMapper reporteMapper;

    public ReporteService(
            ReporteRepository reporteRepository,
            AsignacionRepository asignacionRepository,
            ReporteMapper reporteMapper
    ) {
        this.reporteRepository = reporteRepository;
        this.asignacionRepository = asignacionRepository;
        this.reporteMapper = reporteMapper;
    }

    public List<ReporteResponse> listar() {
        return reporteRepository.findAll().stream()
                .map(reporteMapper::toResponse)
                .toList();
    }

    public ReporteResponse buscarPorId(Long id) {
        return reporteMapper.toResponse(obtenerReporte(id));
    }

    public ReporteResponse buscarPorAsignacion(Long asignacionId) {
        Reporte reporte = reporteRepository.findByAsignacionId(asignacionId)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));

        return reporteMapper.toResponse(reporte);
    }

    public List<ReporteResponse> buscarPorAprobado(Boolean aprobado) {
        return reporteRepository.findByAprobado(aprobado).stream()
                .map(reporteMapper::toResponse)
                .toList();
    }

    public List<ReporteResponse> buscarPorInspector(String inspector) {
        return reporteRepository.findByInspector(inspector).stream()
                .map(reporteMapper::toResponse)
                .toList();
    }

    public ReporteResponse crear(ReporteRequest request) {
        if (reporteRepository.existsByAsignacionId(request.getAsignacionId())) {
            throw new RuntimeException("Ya existe un reporte para esa asignación");
        }

        Asignacion asignacion = obtenerAsignacion(request.getAsignacionId());
        Reporte reporte = reporteMapper.toEntity(request, asignacion);

        return reporteMapper.toResponse(reporteRepository.save(reporte));
    }

    public ReporteResponse actualizar(Long id, ReporteRequest request) {
        Reporte reporte = obtenerReporte(id);
        Asignacion asignacion = obtenerAsignacion(request.getAsignacionId());

        reporteMapper.updateEntity(reporte, request, asignacion);

        return reporteMapper.toResponse(reporteRepository.save(reporte));
    }

    public void eliminar(Long id) {
        Reporte reporte = obtenerReporte(id);
        reporteRepository.delete(reporte);
    }

    private Reporte obtenerReporte(Long id) {
        return reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
    }

    private Asignacion obtenerAsignacion(Long asignacionId) {
        return asignacionRepository.findById(asignacionId)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));
    }
}