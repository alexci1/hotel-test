package cl.hilton.reservas.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.reservas.dto.CancelacionRequest;
import cl.hilton.reservas.dto.CancelacionResponse;
import cl.hilton.reservas.model.Cancelacion;

@Mapper(componentModel = "spring")
public interface CancelacionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reserva", ignore = true)
    @Mapping(target = "canceladoEn", ignore = true)
    Cancelacion toEntity(CancelacionRequest request);

    @Mapping(target = "codigoReserva", source = "reserva.codigoReserva")
    CancelacionResponse toResponse(Cancelacion cancelacion);

    List<CancelacionResponse> toResponseList(List<Cancelacion> cancelaciones);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reserva", ignore = true)
    @Mapping(target = "canceladoEn", ignore = true)
    void updateEntity(CancelacionRequest request, @MappingTarget Cancelacion cancelacion);
}
