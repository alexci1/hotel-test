package cl.hilton.reportes.mapper;


import cl.hilton.reportes.dto.ReporteRequest;
import cl.hilton.reportes.dto.ReporteResponse;
import cl.hilton.reportes.model.Reporte;
import org.springframework.stereotype.Component;

@Component
public class ReporteMapper {

    public Reporte toEntity(ReporteRequest request) {
        return Reporte.builder()
                .codigo(request.getCodigo())
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .tipo(request.getTipo())
                .frecuencia(request.getFrecuencia())
                .activo(request.getActivo())
                .build();
    }

    public ReporteResponse toResponse(Reporte reporte) {
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

    public void updateEntity(Reporte reporte, ReporteRequest request) {
        reporte.setCodigo(request.getCodigo());
        reporte.setNombre(request.getNombre());
        reporte.setDescripcion(request.getDescripcion());
        reporte.setTipo(request.getTipo());
        reporte.setFrecuencia(request.getFrecuencia());
        reporte.setActivo(request.getActivo());
    }
}