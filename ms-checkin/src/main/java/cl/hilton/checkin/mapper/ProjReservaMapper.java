package cl.hilton.checkin.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.checkin.dto.ProjReservaRequest;
import cl.hilton.checkin.dto.ProjReservaResponse;
import cl.hilton.checkin.model.ProjReserva;

@Mapper(componentModel = "spring")
public interface ProjReservaMapper {

    @Mapping(target = "actualizadoEn", ignore = true)
    @Mapping(target = "checkin", ignore = true)
    @Mapping(target = "checkout", ignore = true)
    @Mapping(target = "llaves", ignore = true)
    ProjReserva toEntity(ProjReservaRequest request);

    ProjReservaResponse toResponse(ProjReserva reserva);

    List<ProjReservaResponse> toResponseList(List<ProjReserva> reservas);

    @Mapping(target = "codigoReserva", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    @Mapping(target = "checkin", ignore = true)
    @Mapping(target = "checkout", ignore = true)
    @Mapping(target = "llaves", ignore = true)
    void updateEntity(ProjReservaRequest request, @MappingTarget ProjReserva reserva);
}