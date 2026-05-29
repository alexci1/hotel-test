package cl.hilton.inventario.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import cl.hilton.inventario.dto.ProductoRequest;
import cl.hilton.inventario.dto.ProductoResponse;
import cl.hilton.inventario.model.Producto;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movimientos", ignore = true)
    @Mapping(target = "minibares", ignore = true)
    Producto toEntity(ProductoRequest request);

    ProductoResponse toResponse(Producto producto);

    List<ProductoResponse> toResponseList(List<Producto> productos);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movimientos", ignore = true)
    @Mapping(target = "minibares", ignore = true)
    void updateEntity(ProductoRequest request, @MappingTarget Producto producto);
}
