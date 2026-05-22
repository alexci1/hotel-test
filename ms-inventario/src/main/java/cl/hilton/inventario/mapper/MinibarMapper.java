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
        Minibar minibar = new Minibar();
        minibar.setHabitacion(habitacion);
        minibar.setProducto(producto);
        minibar.setCantidad(request.getCantidad());
        minibar.setPrecioUnitUsd(request.getPrecioUnitUsd());
        return minibar;
    }

    public MinibarResponse toResponse(Minibar minibar) {
        MinibarResponse response = new MinibarResponse();
        response.setId(minibar.getId());
        response.setNumeroHabitacion(minibar.getHabitacion().getNumeroHabitacion());
        response.setCodigoProducto(minibar.getProducto().getCodigoProducto());
        response.setNombreProducto(minibar.getProducto().getNombre());
        response.setCantidad(minibar.getCantidad());
        response.setPrecioUnitUsd(minibar.getPrecioUnitUsd());
        return response;
    }

    public void updateEntity(Minibar minibar, MinibarRequest request, ProjHabitacion habitacion, Producto producto) {
        minibar.setHabitacion(habitacion);
        minibar.setProducto(producto);
        minibar.setCantidad(request.getCantidad());
        minibar.setPrecioUnitUsd(request.getPrecioUnitUsd());
    }
}