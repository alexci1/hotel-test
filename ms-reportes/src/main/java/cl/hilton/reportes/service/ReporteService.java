package cl.hilton.reportes.service;

import cl.hilton.reportes.dto.ReporteRequest;
import cl.hilton.reportes.dto.ReporteResponse;
import cl.hilton.reportes.model.Reporte;
import cl.hilton.reportes.repository.ReporteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final ReporteRepository reporteRepository;

    public List<ReporteResponse> listar() {
        return reporteRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ReporteResponse buscarPorId(Integer id) {
        return toResponse(obtenerReporte(id));
    }

    public ReporteResponse buscarPorCodigo(String codigo) {
        Reporte reporte = reporteRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
        return toResponse(reporte);
    }

    public List<ReporteResponse> buscarPorTipo(String tipo) {
        return reporteRepository.findByTipo(tipo).stream().map(this::toResponse).toList();
    }

    public List<ReporteResponse> buscarPorFrecuencia(String frecuencia) {
        return reporteRepository.findByFrecuencia(frecuencia).stream().map(this::toResponse).toList();
    }

    public List<ReporteResponse> buscarPorActivo(Boolean activo) {
        return reporteRepository.findByActivo(activo).stream().map(this::toResponse).toList();
    }

    public ReporteResponse crear(ReporteRequest request) {
        if (reporteRepository.existsByCodigo(request.getCodigo())) {
            throw new RuntimeException("Ya existe un reporte con ese código");
        }

        Reporte reporte = Reporte.builder()
                .codigo(request.getCodigo())
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .tipo(request.getTipo())
                .frecuencia(request.getFrecuencia())
                .activo(request.getActivo())
                .build();

        return toResponse(reporteRepository.save(reporte));
    }

    public ReporteResponse actualizar(Integer id, ReporteRequest request) {
        Reporte reporte = obtenerReporte(id);

        reporte.setCodigo(request.getCodigo());
        reporte.setNombre(request.getNombre());
        reporte.setDescripcion(request.getDescripcion());
        reporte.setTipo(request.getTipo());
        reporte.setFrecuencia(request.getFrecuencia());
        reporte.setActivo(request.getActivo());

        return toResponse(reporteRepository.save(reporte));
    }

    public void eliminar(Integer id) {
        Reporte reporte = obtenerReporte(id);
        reporteRepository.delete(reporte);
    }

    private Reporte obtenerReporte(Integer id) {
        return reporteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
    }

    private ReporteResponse toResponse(Reporte reporte) {
        return ReporteResponse.builder()
                .id(reporte.getId())
                .codigo(reporte.getCodigo())
                .nombre(reporte.getNombre())
                .descripcion(reporte.getDescripcion())
                .tipo(reporte.getTipo())
                .frecuencia(reporte.getFrecuencia())
                .activo(reporte.getActivo())
                .build();
    }
}

