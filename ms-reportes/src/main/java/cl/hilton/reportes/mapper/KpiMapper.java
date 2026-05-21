package cl.hilton.reportes.mapper;


import cl.hilton.reportes.dto.KpiRequest;
import cl.hilton.reportes.dto.KpiResponse;
import cl.hilton.reportes.model.Kpi;
import org.springframework.stereotype.Component;

@Component
public class KpiMapper {

    public Kpi toEntity(KpiRequest request) {
        return Kpi.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .valorActual(request.getValorActual())
                .valorObjetivo(request.getValorObjetivo())
                .unidad(request.getUnidad())
                .periodo(request.getPeriodo())
                .actualizadoEn(request.getActualizadoEn())
                .build();
    }

    public KpiResponse toResponse(Kpi kpi) {
        return KpiResponse.builder()
                .id(kpi.getId())
                .nombre(kpi.getNombre())
                .descripcion(kpi.getDescripcion())
                .valorActual(kpi.getValorActual())
                .valorObjetivo(kpi.getValorObjetivo())
                .unidad(kpi.getUnidad())
                .periodo(kpi.getPeriodo())
                .actualizadoEn(kpi.getActualizadoEn())
                .build();
    }

    public void updateEntity(Kpi kpi, KpiRequest request) {
        kpi.setNombre(request.getNombre());
        kpi.setDescripcion(request.getDescripcion());
        kpi.setValorActual(request.getValorActual());
        kpi.setValorObjetivo(request.getValorObjetivo());
        kpi.setUnidad(request.getUnidad());
        kpi.setPeriodo(request.getPeriodo());
        kpi.setActualizadoEn(request.getActualizadoEn());
    }
}
