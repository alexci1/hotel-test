package cl.hilton.inventario.mapper;


import cl.hilton.inventario.dto.MinibarRequest;
import cl.hilton.inventario.dto.MinibarResponse;
import cl.hilton.inventario.model.Minibar;
import cl.hilton.inventario.model.Producto;
import cl.hilton.inventario.model.ProjHabitacion;
import org.springframework.stereotype.Component;

@Component
public class MinibarMapper {

    public Minibar toEntity(MinibarRequest request, ProjHabitacion habitacion, Producto producto) {
        return Minibar.builder()
                .habitacion(habitacion)
                .producto(producto)
                .cantidad(request.getCantidad())
                .precioUnitUsd(request.getPrecioUnitUsd())
                .build();
    }

    public MinibarResponse toResponse(Minibar minibar) {
        return MinibarResponse.builder()
                .id(minibar.getId())
                .numeroHabitacion(minibar.getHabitacion().getNumeroHabitacion())
                .codigoProducto(minibar.getProducto().getCodigoProducto())
                .nombreProducto(minibar.getProducto().getNombre())
                .cantidad(minibar.getCantidad())
                .precioUnitUsd(minibar.getPrecioUnitUsd())
                .build();
    }

    public void updateEntity(Minibar minibar, MinibarRequest request, ProjHabitacion habitacion, Producto producto) {
        minibar.setHabitacion(habitacion);
        minibar.setProducto(producto);
        minibar.setCantidad(request.getCantidad());
        minibar.setPrecioUnitUsd(request.getPrecioUnitUsd());
    }
}

