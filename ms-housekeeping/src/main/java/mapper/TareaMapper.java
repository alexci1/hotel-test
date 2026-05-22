package cl.hilton.housekeeping.mapper;

import cl.hilton.housekeeping.dto.TareaRequest;
import cl.hilton.housekeeping.dto.TareaResponse;
import cl.hilton.housekeeping.model.Tarea;
import org.springframework.stereotype.Component;

@Component
public class TareaMapper {

    public Tarea toEntity(TareaRequest request) {
        return Tarea.builder()
                .codigo(request.getCodigo())
                .descripcion(request.getDescripcion())
                .duracionMin(request.getDuracionMin())
                .activa(request.getActiva())
                .build();
    }

    public TareaResponse toResponse(Tarea tarea) {
        return TareaResponse.builder()
                .id(tarea.getId())
                .codigo(tarea.getCodigo())
                .descripcion(tarea.getDescripcion())
                .duracionMin(tarea.getDuracionMin())
                .activa(tarea.getActiva())
                .build();
    }

    public void updateEntity(Tarea tarea, TareaRequest request) {
        tarea.setCodigo(request.getCodigo());
        tarea.setDescripcion(request.getDescripcion());
        tarea.setDuracionMin(request.getDuracionMin());
        tarea.setActiva(request.getActiva());
    }
}