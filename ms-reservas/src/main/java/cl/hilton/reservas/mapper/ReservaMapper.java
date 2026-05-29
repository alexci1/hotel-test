package cl.hilton.reservas.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.reservas.dto.ReservaRequest;
import cl.hilton.reservas.dto.ReservaResponse;
import cl.hilton.reservas.model.Reserva;

@Mapper(componentModel = "spring")
public interface ReservaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "huesped", ignore = true)
    @Mapping(target = "habitacion", ignore = true)
    @Mapping(target = "creadoEn", ignore = true)
    @Mapping(target = "cancelacion", ignore = true)
    Reserva toEntity(ReservaRequest request);

    @Mapping(target = "emailHuesped", source = "huesped.email")
    @Mapping(target = "numeroHabitacion", source = "habitacion.numeroHabitacion")
    ReservaResponse toResponse(Reserva reserva);

    List<ReservaResponse> toResponseList(List<Reserva> reservas);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "huesped", ignore = true)
    @Mapping(target = "habitacion", ignore = true)
    @Mapping(target = "creadoEn", ignore = true)
    @Mapping(target = "cancelacion", ignore = true)
    void updateEntity(ReservaRequest request, @MappingTarget Reserva reserva);
}
