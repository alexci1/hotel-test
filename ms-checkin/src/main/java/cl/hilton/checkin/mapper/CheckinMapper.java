package cl.hilton.checkin.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.checkin.dto.CheckinRequest;
import cl.hilton.checkin.dto.CheckinResponse;
import cl.hilton.checkin.model.Checkin;
import cl.hilton.checkin.model.ProjHuesped;
import cl.hilton.checkin.model.ProjReserva;

@Mapper(componentModel = "spring")
public interface CheckinMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reserva", source = "reserva")
    @Mapping(target = "huesped", source = "huesped")
    @Mapping(target = "numeroHabitacion", source = "request.numeroHabitacion")
    @Mapping(target = "fechaHora", ignore = true)
    Checkin toEntity(CheckinRequest request, ProjReserva reserva, ProjHuesped huesped);

    @Mapping(target = "codigoReserva", source = "reserva.codigoReserva")
    @Mapping(target = "emailHuesped", source = "huesped.email")
    @Mapping(target = "nombreHuesped", source = "huesped.nombreCompleto")
    CheckinResponse toResponse(Checkin checkin);

    List<CheckinResponse> toResponseList(List<Checkin> checkins);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reserva", source = "reserva")
    @Mapping(target = "huesped", source = "huesped")
    @Mapping(target = "numeroHabitacion", source = "request.numeroHabitacion")
    @Mapping(target = "fechaHora", ignore = true)
    void updateEntity(CheckinRequest request, ProjReserva reserva, ProjHuesped huesped, @MappingTarget Checkin checkin);
}
