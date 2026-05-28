package cl.hilton.notificaciones.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.notificaciones.dto.EnvioRequest;
import cl.hilton.notificaciones.dto.EnvioResponse;
import cl.hilton.notificaciones.model.Envio;

@Mapper(componentModel = "spring")
public interface EnvioMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "notificacion", ignore = true)
    @Mapping(target = "enviadoEn", ignore = true)
    Envio toEntity(EnvioRequest request);

    @Mapping(target = "notificacionId", source = "notificacion.id")
    EnvioResponse toResponse(Envio envio);

    List<EnvioResponse> toResponseList(List<Envio> envios);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "notificacion", ignore = true)
    @Mapping(target = "enviadoEn", ignore = true)
    void updateEntity(EnvioRequest request, @MappingTarget Envio envio);
}
