package cl.hilton.inventario.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.inventario.dto.MovimientoRequest;
import cl.hilton.inventario.dto.MovimientoResponse;
import cl.hilton.inventario.model.Movimiento;

@Mapper(componentModel = "spring")
public interface MovimientoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "producto", ignore = true)
    @Mapping(target = "registradoEn", ignore = true)
    Movimiento toEntity(MovimientoRequest request);

    @Mapping(target = "codigoProducto", source = "producto.codigoProducto")
    @Mapping(target = "nombreProducto", source = "producto.nombre")
    MovimientoResponse toResponse(Movimiento movimiento);

    List<MovimientoResponse> toResponseList(List<Movimiento> movimientos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "producto", ignore = true)
    @Mapping(target = "registradoEn", ignore = true)
    void updateEntity(MovimientoRequest request, @MappingTarget Movimiento movimiento);
}
