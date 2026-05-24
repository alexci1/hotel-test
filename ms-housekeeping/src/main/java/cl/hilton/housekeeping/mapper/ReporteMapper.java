package cl.hilton.housekeeping.mapper;

import cl.hilton.housekeeping.dto.ReporteRequest;
import cl.hilton.housekeeping.dto.ReporteResponse;
import cl.hilton.housekeeping.model.Asignacion;
import cl.hilton.housekeeping.model.Reporte;
import org.springframework.stereotype.Component;

@Component
public class ReporteMapper {

    public Reporte toEntity(ReporteRequest request, Asignacion asignacion) {
        return Reporte.builder()
                .asignacion(asignacion)
                .aprobado(request.getAprobado())
                .observaciones(request.getObservaciones())
                .inspector(request.getInspector())
                .inspeccionadoEn(request.getInspeccionadoEn())
                .build();
    }

    public ReporteResponse toResponse(Reporte reporte) {
        return ReporteResponse.builder()
                .id(reporte.getId())
                .asignacionId(reporte.getAsignacion().getId())
                .numeroHabitacion(reporte.getAsignacion().getHabitacion().getNumeroHabitacion())
                .codigoTarea(reporte.getAsignacion().getTarea().getCodigo())
                .aprobado(reporte.getAprobado())
                .observaciones(reporte.getObservaciones())
                .inspector(reporte.getInspector())
                .inspeccionadoEn(reporte.getInspeccionadoEn())
                .build();
    }

    public void updateEntity(Reporte reporte, ReporteRequest request, Asignacion asignacion) {
        reporte.setAsignacion(asignacion);
        reporte.setAprobado(request.getAprobado());
        reporte.setObservaciones(request.getObservaciones());
        reporte.setInspector(request.getInspector());
        reporte.setInspeccionadoEn(request.getInspeccionadoEn());
    }
}