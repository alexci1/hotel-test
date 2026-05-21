package cl.hilton.inventario.service;


import cl.hilton.inventario.dto.MovimientoRequest;
import cl.hilton.inventario.dto.MovimientoResponse;
import cl.hilton.inventario.model.Movimiento;
import cl.hilton.inventario.model.Producto;
import cl.hilton.inventario.repository.MovimientoRepository;
import cl.hilton.inventario.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final ProductoRepository productoRepository;

    public List<MovimientoResponse> listar() {
        return movimientoRepository.findAll().stream().map(this::toResponse).toList();
    }

    public MovimientoResponse buscarPorId(Integer id) {
        return toResponse(obtenerMovimiento(id));
    }

    public List<MovimientoResponse> buscarPorProducto(String codigoProducto) {
        return movimientoRepository.findByProductoCodigoProductoOrderByRegistradoEnDesc(codigoProducto)
                .stream().map(this::toResponse).toList();
    }

    public List<MovimientoResponse> buscarPorTipo(String tipo) {
        return movimientoRepository.findByTipo(tipo).stream().map(this::toResponse).toList();
    }

    public List<MovimientoResponse> buscarPorRegistradoPor(String registradoPor) {
        return movimientoRepository.findByRegistradoPor(registradoPor).stream().map(this::toResponse).toList();
    }

    public List<MovimientoResponse> buscarPorFechas(OffsetDateTime desde, OffsetDateTime hasta) {
        return movimientoRepository.findByRegistradoEnBetween(desde, hasta)
                .stream().map(this::toResponse).toList();
    }

    public MovimientoResponse crear(MovimientoRequest request) {
        Producto producto = productoRepository.findByCodigoProducto(request.getCodigoProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Movimiento movimiento = Movimiento.builder()
                .producto(producto)
                .tipo(request.getTipo())
                .cantidad(request.getCantidad())
                .motivo(request.getMotivo())
                .registradoPor(request.getRegistradoPor())
                .registradoEn(request.getRegistradoEn() != null ? request.getRegistradoEn() : OffsetDateTime.now())
                .build();

        return toResponse(movimientoRepository.save(movimiento));
    }

    public MovimientoResponse actualizar(Integer id, MovimientoRequest request) {
        Movimiento movimiento = obtenerMovimiento(id);
        Producto producto = productoRepository.findByCodigoProducto(request.getCodigoProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        movimiento.setProducto(producto);
        movimiento.setTipo(request.getTipo());
        movimiento.setCantidad(request.getCantidad());
        movimiento.setMotivo(request.getMotivo());
        movimiento.setRegistradoPor(request.getRegistradoPor());
        movimiento.setRegistradoEn(request.getRegistradoEn() != null ? request.getRegistradoEn() : movimiento.getRegistradoEn());

        return toResponse(movimientoRepository.save(movimiento));
    }

    public void eliminar(Integer id) {
        Movimiento movimiento = obtenerMovimiento(id);
        movimientoRepository.delete(movimiento);
    }

    private Movimiento obtenerMovimiento(Integer id) {
        return movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
    }

    private MovimientoResponse toResponse(Movimiento movimiento) {
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
}

