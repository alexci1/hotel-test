package cl.hilton.pagos.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.pagos.dto.ProjReservaRequest;
import cl.hilton.pagos.dto.ProjReservaResponse;
import cl.hilton.pagos.model.ProjReserva;

@Mapper(componentModel = "spring")
public interface ProjReservaMapper {

    @Mapping(target = "actualizadoEn", ignore = true)
    @Mapping(target = "factura", ignore = true)
    ProjReserva toEntity(ProjReservaRequest request);

    ProjReservaResponse toResponse(ProjReserva reserva);

    List<ProjReservaResponse> toResponseList(List<ProjReserva> reservas);

    @Mapping(target = "codigoReserva", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    @Mapping(target = "factura", ignore = true)
    void updateEntity(ProjReservaRequest request, @MappingTarget ProjReserva reserva);
}
