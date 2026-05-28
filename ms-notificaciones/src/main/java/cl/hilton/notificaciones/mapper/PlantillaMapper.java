package cl.hilton.notificaciones.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.notificaciones.dto.PlantillaRequest;
import cl.hilton.notificaciones.dto.PlantillaResponse;
import cl.hilton.notificaciones.model.Plantilla;

@Mapper(componentModel = "spring")
public interface PlantillaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "notificaciones", ignore = true)
    Plantilla toEntity(PlantillaRequest request);

    PlantillaResponse toResponse(Plantilla plantilla);

    List<PlantillaResponse> toResponseList(List<Plantilla> plantillas);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "notificaciones", ignore = true)
    void updateEntity(PlantillaRequest request, @MappingTarget Plantilla plantilla);
}
