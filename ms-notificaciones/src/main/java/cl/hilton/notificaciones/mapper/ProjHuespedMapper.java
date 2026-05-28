package cl.hilton.notificaciones.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.notificaciones.dto.ProjHuespedRequest;
import cl.hilton.notificaciones.dto.ProjHuespedResponse;
import cl.hilton.notificaciones.model.ProjHuesped;

@Mapper(componentModel = "spring")
public interface ProjHuespedMapper {

    @Mapping(target = "actualizadoEn", ignore = true)
    @Mapping(target = "notificaciones", ignore = true)
    ProjHuesped toEntity(ProjHuespedRequest request);

    ProjHuespedResponse toResponse(ProjHuesped huesped);

    List<ProjHuespedResponse> toResponseList(List<ProjHuesped> huespedes);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    @Mapping(target = "notificaciones", ignore = true)
    void updateEntity(ProjHuespedRequest request, @MappingTarget ProjHuesped huesped);
}
