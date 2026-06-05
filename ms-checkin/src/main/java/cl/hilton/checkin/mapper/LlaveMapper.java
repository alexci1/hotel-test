package cl.hilton.checkin.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.checkin.dto.LlaveRequest;
import cl.hilton.checkin.dto.LlaveResponse;
import cl.hilton.checkin.model.Llave;
import cl.hilton.checkin.model.ProjReserva;
import jakarta.validation.constraints.Null;


@SuppressWarnings("null")
@Mapper(componentModel = "spring")
public interface LlaveMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reserva", source = "reserva")
    @Mapping(target = "numeroHabitacion", source = "request.numeroHabitacion")
    @Mapping(target = "codigoLlave", source = "request.codigoLlave")
    @Mapping(target = "activa", source = "request.activa")
    Llave toEntity(LlaveRequest request, ProjReserva reserva);

    @Mapping(target = "codigoReserva", source = "reserva.codigoReserva")
    LlaveResponse toResponse(Llave llave);

    List<LlaveResponse> toResponseList(List<Llave> llaves);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reserva", source = "reserva")
    @Mapping(target = "numeroHabitacion", source = "request.numeroHabitacion")
    @Mapping(target = "codigoLlave", source = "request.codigoLlave")
    @Mapping(target = "activa", source = "request.activa")
    void updateEntity(
            LlaveRequest request,
            ProjReserva reserva,
            @MappingTarget Llave llave
    );
}