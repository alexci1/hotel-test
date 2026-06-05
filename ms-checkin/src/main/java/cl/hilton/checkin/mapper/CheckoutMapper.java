package cl.hilton.checkin.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.checkin.dto.CheckoutRequest;
import cl.hilton.checkin.dto.CheckoutResponse;
import cl.hilton.checkin.model.Checkout;
import cl.hilton.checkin.model.ProjReserva;

@Mapper(componentModel = "spring")
public interface CheckoutMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reserva", source = "reserva")
    @Mapping(target = "fechaHora", ignore = true)
    Checkout toEntity(CheckoutRequest request, ProjReserva reserva);

    @Mapping(target = "codigoReserva", source = "reserva.codigoReserva")
    CheckoutResponse toResponse(Checkout checkout);

    List<CheckoutResponse> toResponseList(List<Checkout> checkouts);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reserva", source = "reserva")
    @Mapping(target = "fechaHora", ignore = true)
    void updateEntity(CheckoutRequest request, ProjReserva reserva, @MappingTarget Checkout checkout);
}
