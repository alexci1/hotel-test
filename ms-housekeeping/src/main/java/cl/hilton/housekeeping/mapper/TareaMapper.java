package cl.hilton.housekeeping.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.housekeeping.dto.TareaRequest;
import cl.hilton.housekeeping.dto.TareaResponse;
import cl.hilton.housekeeping.model.Tarea;

@Mapper(componentModel = "spring")
public interface TareaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "asignaciones", ignore = true)
    Tarea toEntity(TareaRequest request);

    TareaResponse toResponse(Tarea tarea);

    List<TareaResponse> toResponseList(List<Tarea> tareas);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "asignaciones", ignore = true)
    void updateEntity(TareaRequest request, @MappingTarget Tarea tarea);
}