package cl.hilton.inventario.mapper;

import cl.hilton.inventario.dto.MiniBarRequest;
import cl.hilton.inventario.dto.MiniBarResponse;
import cl.hilton.inventario.model.MiniBar;
import cl.hilton.inventario.model.Producto;
import cl.hilton.inventario.model.ProjHabitacion;
import org.springframework.stereotype.Component;

@Component
public class MiniBarMapper {

    public MiniBar toEntity(MiniBarRequest request, ProjHabitacion habitacion, Producto producto) {
        return MiniBar.builder()
                .habitacion(habitacion)
                .producto(producto)
                .cantidad(request.getCantidad())
                .precioUnitUsd(request.getPrecioUnitUsd())
                .build();
    }

    public MiniBarResponse toResponse(MiniBar miniBar) {
        return MiniBarResponse.builder()
                .id(miniBar.getId())
                .numeroHabitacion(miniBar.getHabitacion().getNumeroHabitacion())
                .codigoProducto(miniBar.getProducto().getCodigoProducto())
                .nombreProducto(miniBar.getProducto().getNombre())
                .cantidad(miniBar.getCantidad())
                .precioUnitUsd(miniBar.getPrecioUnitUsd())
                .build();
    }

    public void updateEntity(MiniBar miniBar, MiniBarRequest request, ProjHabitacion habitacion, Producto producto) {
        miniBar.setHabitacion(habitacion);
        miniBar.setProducto(producto);
        miniBar.setCantidad(request.getCantidad());
        miniBar.setPrecioUnitUsd(request.getPrecioUnitUsd());
    }
}