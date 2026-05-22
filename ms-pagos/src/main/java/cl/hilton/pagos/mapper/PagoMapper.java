package cl.hilton.pagos.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.pagos.dto.PagoRequest;
import cl.hilton.pagos.dto.PagoResponse;
import cl.hilton.pagos.model.Pago;

@Mapper(componentModel = "spring")
public interface PagoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "factura", ignore = true)
    @Mapping(target = "pagadoEn", ignore = true)
    Pago toEntity(PagoRequest request);

    @Mapping(target = "numeroFactura", source = "factura.numeroFactura")
    PagoResponse toResponse(Pago pago);

    List<PagoResponse> toResponseList(List<Pago> pagos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "factura", ignore = true)
    @Mapping(target = "pagadoEn", ignore = true)
    void updateEntity(PagoRequest request, @MappingTarget Pago pago);
}