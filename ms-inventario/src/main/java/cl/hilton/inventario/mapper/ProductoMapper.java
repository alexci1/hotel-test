package cl.hilton.inventario.mapper;

import cl.hilton.inventario.dto.ProductoRequest;
import cl.hilton.inventario.dto.ProductoResponse;
import cl.hilton.inventario.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public Producto toEntity(ProductoRequest request) {
        return Producto.builder()
                .codigoProducto(request.getCodigoProducto())
                .nombre(request.getNombre())
                .categoria(request.getCategoria())
                .stockActual(request.getStockActual())
                .stockMinimo(request.getStockMinimo())
                .unidad(request.getUnidad())
                .build();
    }

    public ProductoResponse toResponse(Producto producto) {
        return ProductoResponse.builder()
                .id(producto.getId())
                .codigoProducto(producto.getCodigoProducto())
                .nombre(producto.getNombre())
                .categoria(producto.getCategoria())
                .stockActual(producto.getStockActual())
                .stockMinimo(producto.getStockMinimo())
                .unidad(producto.getUnidad())
                .build();
    }

    public void updateEntity(Producto producto, ProductoRequest request) {
        producto.setCodigoProducto(request.getCodigoProducto());
        producto.setNombre(request.getNombre());
        producto.setCategoria(request.getCategoria());
        producto.setStockActual(request.getStockActual());
        producto.setStockMinimo(request.getStockMinimo());
        producto.setUnidad(request.getUnidad());
    }
}
