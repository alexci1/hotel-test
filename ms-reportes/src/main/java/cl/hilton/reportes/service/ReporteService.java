package cl.hilton.reportes.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.reportes.dto.ReporteRequest;
import cl.hilton.reportes.dto.ReporteResponse;
import cl.hilton.reportes.mapper.ReporteMapper;
import cl.hilton.reportes.model.Reporte;
import cl.hilton.reportes.repository.ReporteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final ReporteRepository reporteRepository;
    private final ReporteMapper reporteMapper;

    public List<ReporteResponse> findAll() {
        return reporteMapper.toResponseList(reporteRepository.findAll());
    }

    public ReporteResponse findById(Long id) {
        Reporte reporte = getReporteById(id);
        return reporteMapper.toResponse(reporte);
    }

    public ReporteResponse findByCodigo(String codigo) {
        Reporte reporte = reporteRepository.findByCodigo(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado con codigo: " + codigo));

        return reporteMapper.toResponse(reporte);
    }

    public List<ReporteResponse> findByTipo(String tipo) {
        return reporteMapper.toResponseList(reporteRepository.findByTipo(tipo));
    }

    public List<ReporteResponse> findByFrecuencia(String frecuencia) {
        return reporteMapper.toResponseList(reporteRepository.findByFrecuencia(frecuencia));
    }

    public List<ReporteResponse> findByActivo(Boolean activo) {
        return reporteMapper.toResponseList(reporteRepository.findByActivo(activo));
    }

    public List<ReporteResponse> findByNombre(String nombre) {
        return reporteMapper.toResponseList(reporteRepository.findByNombreContainingIgnoreCase(nombre));
    }

    public ReporteResponse create(ReporteRequest request) {
        validarCodigoUnico(request.getCodigo());

        Reporte reporte = reporteMapper.toEntity(request);
        reporte.setFrecuencia(request.getFrecuencia() != null ? request.getFrecuencia() : "DIARIO");
        reporte.setActivo(request.getActivo() != null ? request.getActivo() : true);

        Reporte reporteGuardado = reporteRepository.save(reporte);

        return reporteMapper.toResponse(reporteGuardado);
    }

    public ReporteResponse update(Long id, ReporteRequest request) {
        Reporte reporte = getReporteById(id);
        Boolean activoActual = reporte.getActivo();
        String frecuenciaActual = reporte.getFrecuencia();

        if (!reporte.getCodigo().equalsIgnoreCase(request.getCodigo())) {
            validarCodigoUnico(request.getCodigo());
        }

        reporteMapper.updateEntity(request, reporte);
        reporte.setFrecuencia(request.getFrecuencia() != null ? request.getFrecuencia() : frecuenciaActual);
        reporte.setActivo(request.getActivo() != null ? request.getActivo() : activoActual);

        Reporte reporteActualizado = reporteRepository.save(reporte);

        return reporteMapper.toResponse(reporteActualizado);
    }

    public void deleteById(Long id) {
        Reporte reporte = getReporteById(id);
        reporteRepository.delete(reporte);
    }

    private Reporte getReporteById(Long id) {
        return reporteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado con id: " + id));
    }

    private void validarCodigoUnico(String codigo) {
        if (reporteRepository.existsByCodigo(codigo)) {
            throw new IllegalArgumentException("Ya existe un reporte con codigo: " + codigo);
        }
    }
}
