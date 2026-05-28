package cl.hilton.pagos.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.pagos.dto.FacturaRequest;
import cl.hilton.pagos.dto.FacturaResponse;
import cl.hilton.pagos.model.Factura;

@Mapper(componentModel = "spring")
public interface FacturaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reserva", ignore = true)
    @Mapping(target = "huesped", ignore = true)
    @Mapping(target = "emitidaEn", ignore = true)
    @Mapping(target = "pagos", ignore = true)
    @Mapping(target = "cargos", ignore = true)
    Factura toEntity(FacturaRequest request);

    @Mapping(target = "codigoReserva", source = "reserva.codigoReserva")
    @Mapping(target = "emailHuesped", source = "huesped.email")
    FacturaResponse toResponse(Factura factura);

    List<FacturaResponse> toResponseList(List<Factura> facturas);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reserva", ignore = true)
    @Mapping(target = "huesped", ignore = true)
    @Mapping(target = "emitidaEn", ignore = true)
    @Mapping(target = "pagos", ignore = true)
    @Mapping(target = "cargos", ignore = true)
    void updateEntity(FacturaRequest request, @MappingTarget Factura factura);
}
