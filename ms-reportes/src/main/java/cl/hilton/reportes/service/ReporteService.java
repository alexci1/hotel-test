package cl.hilton.reportes.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.reportes.dto.ReporteRequest;
import cl.hilton.reportes.dto.ReporteResponse;
import cl.hilton.reportes.mapper.ReporteMapper;
import cl.hilton.reportes.model.Reporte;
import cl.hilton.reportes.repository.ReporteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
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
        String codigoValido = validarTexto(codigo, "codigo");

        Reporte reporte = reporteRepository.findByCodigo(codigoValido)
                .orElseThrow(() -> new EntityNotFoundException("Reporte no encontrado con codigo: " + codigoValido));

        return reporteMapper.toResponse(reporte);
    }

    public List<ReporteResponse> findByTipo(String tipo) {
        String tipoValido = validarTexto(tipo, "tipo");
        return reporteMapper.toResponseList(reporteRepository.findByTipo(tipoValido));
    }

    public List<ReporteResponse> findByFrecuencia(String frecuencia) {
        String frecuenciaValida = validarTexto(frecuencia, "frecuencia");
        return reporteMapper.toResponseList(reporteRepository.findByFrecuencia(frecuenciaValida));
    }

    public List<ReporteResponse> findByActivo(Boolean activo) {
        Boolean estado = validarBoolean(activo, "activo");
        return reporteMapper.toResponseList(reporteRepository.findByActivo(estado));
    }

    public List<ReporteResponse> findByNombre(String nombre) {
        String nombreValido = validarTexto(nombre, "nombre");
        return reporteMapper.toResponseList(reporteRepository.findByNombreContainingIgnoreCase(nombreValido));
    }

    @Transactional
    public ReporteResponse create(ReporteRequest request) {
        String codigo = validarTexto(request.getCodigo(), "codigo");
        validarCodigoUnico(codigo);

        Reporte reporte = reporteMapper.toEntity(request);
        reporte.setFrecuencia(request.getFrecuencia() != null ? request.getFrecuencia() : "DIARIO");
        reporte.setActivo(request.getActivo() != null ? request.getActivo() : true);

        Reporte reporteGuardado = reporteRepository.save(reporte);

        return reporteMapper.toResponse(reporteGuardado);
    }

    @Transactional
    public ReporteResponse update(Long id, ReporteRequest request) {
        Long reporteId = validarId(id);
        String codigo = validarTexto(request.getCodigo(), "codigo");

        Reporte reporte = getReporteById(reporteId);
        Boolean activoActual = reporte.getActivo();
        String frecuenciaActual = reporte.getFrecuencia();

        if (!reporte.getCodigo().equalsIgnoreCase(codigo)) {
            validarCodigoUnico(codigo);
        }

        reporteMapper.updateEntity(request, reporte);
        reporte.setFrecuencia(request.getFrecuencia() != null ? request.getFrecuencia() : frecuenciaActual);
        reporte.setActivo(request.getActivo() != null ? request.getActivo() : activoActual);

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

    private void validarCodigoUnico(String codigo) {
        if (reporteRepository.existsByCodigo(codigo)) {
            throw new IllegalArgumentException("Ya existe un reporte con codigo: " + codigo);
        }
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
