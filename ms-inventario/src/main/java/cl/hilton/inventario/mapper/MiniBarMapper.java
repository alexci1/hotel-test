package cl.hilton.inventario.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.inventario.dto.MiniBarRequest;
import cl.hilton.inventario.dto.MiniBarResponse;
import cl.hilton.inventario.model.MiniBar;

@Mapper(componentModel = "spring")
public interface MiniBarMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "habitacion", ignore = true)
    @Mapping(target = "producto", ignore = true)
    MiniBar toEntity(MiniBarRequest request);

    @Mapping(target = "numeroHabitacion", source = "habitacion.numeroHabitacion")
    @Mapping(target = "codigoProducto", source = "producto.codigoProducto")
    @Mapping(target = "nombreProducto", source = "producto.nombre")
    MiniBarResponse toResponse(MiniBar miniBar);

    List<MiniBarResponse> toResponseList(List<MiniBar> minibares);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "habitacion", ignore = true)
    @Mapping(target = "producto", ignore = true)
    void updateEntity(MiniBarRequest request, @MappingTarget MiniBar miniBar);
}
