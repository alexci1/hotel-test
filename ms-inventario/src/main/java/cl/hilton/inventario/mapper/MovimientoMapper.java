package cl.hilton.inventario.mapper;

import cl.hilton.inventario.dto.MovimientoRequest;
import cl.hilton.inventario.dto.MovimientoResponse;
import cl.hilton.inventario.model.Movimiento;
import cl.hilton.inventario.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class MovimientoMapper {

    public Movimiento toEntity(MovimientoRequest request, Producto producto) {
        return Movimiento.builder()
                .producto(producto)
                .tipo(request.getTipo())
                .cantidad(request.getCantidad())
                .motivo(request.getMotivo())
                .registradoPor(request.getRegistradoPor())
                .registradoEn(request.getRegistradoEn())
                .build();
    }

    public MovimientoResponse toResponse(Movimiento movimiento) {
        return MovimientoResponse.builder()
                .id(movimiento.getId())
                .codigoProducto(movimiento.getProducto().getCodigoProducto())
                .nombreProducto(movimiento.getProducto().getNombre())
                .tipo(movimiento.getTipo())
                .cantidad(movimiento.getCantidad())
                .motivo(movimiento.getMotivo())
                .registradoPor(movimiento.getRegistradoPor())
                .registradoEn(movimiento.getRegistradoEn())
                .build();
    }

    public void updateEntity(Movimiento movimiento, MovimientoRequest request, Producto producto) {
        movimiento.setProducto(producto);
        movimiento.setTipo(request.getTipo());
        movimiento.setCantidad(request.getCantidad());
        movimiento.setMotivo(request.getMotivo());
        movimiento.setRegistradoPor(request.getRegistradoPor());
        movimiento.setRegistradoEn(request.getRegistradoEn());
    }
}