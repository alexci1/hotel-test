package cl.hilton.notificaciones.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.notificaciones.dto.NotificacionRequest;
import cl.hilton.notificaciones.dto.NotificacionResponse;
import cl.hilton.notificaciones.model.Notificacion;

@Mapper(componentModel = "spring")
public interface NotificacionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "plantilla", ignore = true)
    @Mapping(target = "huesped", ignore = true)
    @Mapping(target = "creadoEn", ignore = true)
    @Mapping(target = "envio", ignore = true)
    Notificacion toEntity(NotificacionRequest request);

    @Mapping(target = "codigoPlantilla", source = "plantilla.codigo")
    @Mapping(target = "emailHuesped", source = "huesped.email")
    NotificacionResponse toResponse(Notificacion notificacion);

    List<NotificacionResponse> toResponseList(List<Notificacion> notificaciones);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "plantilla", ignore = true)
    @Mapping(target = "huesped", ignore = true)
    @Mapping(target = "creadoEn", ignore = true)
    @Mapping(target = "envio", ignore = true)
    void updateEntity(NotificacionRequest request, @MappingTarget Notificacion notificacion);
}