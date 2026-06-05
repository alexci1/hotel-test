package cl.hilton.inventario.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.inventario.dto.MovimientoRequest;
import cl.hilton.inventario.dto.MovimientoResponse;
import cl.hilton.inventario.mapper.MovimientoMapper;
import cl.hilton.inventario.model.Movimiento;
import cl.hilton.inventario.model.Producto;
import cl.hilton.inventario.repository.MovimientoRepository;
import cl.hilton.inventario.repository.ProductoRepository;
import cl.hilton.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class MovimientoService {

    private final MovimientoRepository movimientoRepository;
    private final ProductoRepository productoRepository;
    private final MovimientoMapper movimientoMapper;

    public List<MovimientoResponse> findAll() {
        return movimientoMapper.toResponseList(movimientoRepository.findAll());
    }

    public MovimientoResponse findById(Long id) {
        Movimiento movimiento = getMovimientoById(id);
        return movimientoMapper.toResponse(movimiento);
    }

    public List<MovimientoResponse> findByCodigoProducto(String codigoProducto) {
        return movimientoMapper.toResponseList(movimientoRepository.findByProductoCodigoProductoOrderByRegistradoEnDesc(codigoProducto));
    }

    public List<MovimientoResponse> findByTipo(String tipo) {
        return movimientoMapper.toResponseList(movimientoRepository.findByTipo(tipo));
    }

    public List<MovimientoResponse> findByRegistradoPor(String registradoPor) {
        return movimientoMapper.toResponseList(movimientoRepository.findByRegistradoPor(registradoPor));
    }

    public List<MovimientoResponse> findByRegistradoEn(LocalDate registradoEn) {
        return movimientoMapper.toResponseList(movimientoRepository.findByRegistradoEn(registradoEn));
    }

    public List<MovimientoResponse> findByRangoFechas(LocalDate desde, LocalDate hasta) {
        return movimientoMapper.toResponseList(movimientoRepository.findByRegistradoEnBetween(desde, hasta));
    }

    public List<MovimientoResponse> findByCantidadGreaterThan(Integer cantidad) {
        return movimientoMapper.toResponseList(movimientoRepository.findByCantidadGreaterThan(cantidad));
    }

    public List<MovimientoResponse> findByCantidadLessThan(Integer cantidad) {
        return movimientoMapper.toResponseList(movimientoRepository.findByCantidadLessThan(cantidad));
    }

    @SuppressWarnings("null")
    public MovimientoResponse create(MovimientoRequest request) {
        validarCantidad(request.getCantidad());

        Producto producto = getProductoByCodigo(request.getCodigoProducto());
        aplicarMovimientoStock(producto, request.getCantidad());

        Movimiento movimiento = movimientoMapper.toEntity(request);
        movimiento.setProducto(producto);
        movimiento.setRegistradoEn(LocalDate.now());

        productoRepository.save(producto);
        Movimiento movimientoGuardado = movimientoRepository.save(movimiento);

        return movimientoMapper.toResponse(movimientoGuardado);
    }

    public MovimientoResponse update(Long id, MovimientoRequest request) {
        validarCantidad(request.getCantidad());

        Movimiento movimiento = getMovimientoById(id);
        Producto productoAnterior = movimiento.getProducto();
        revertirMovimientoStock(productoAnterior, movimiento.getCantidad());

        Producto productoNuevo = getProductoByCodigo(request.getCodigoProducto());
        aplicarMovimientoStock(productoNuevo, request.getCantidad());

        movimientoMapper.updateEntity(request, movimiento);
        movimiento.setProducto(productoNuevo);

        productoRepository.save(productoAnterior);
        if (!productoAnterior.getCodigoProducto().equalsIgnoreCase(productoNuevo.getCodigoProducto())) {
            productoRepository.save(productoNuevo);
        }

        Movimiento movimientoActualizado = movimientoRepository.save(movimiento);

        return movimientoMapper.toResponse(movimientoActualizado);
    }

    public void deleteById(Long id) {
        Movimiento movimiento = getMovimientoById(id);
        Producto producto = movimiento.getProducto();
        revertirMovimientoStock(producto, movimiento.getCantidad());
        productoRepository.save(producto);
        movimientoRepository.delete(movimiento);
    }

    private Movimiento getMovimientoById(Long id) {
        return movimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movimiento no encontrado con id: " + id));
    }

    private Producto getProductoByCodigo(String codigoProducto) {
        return productoRepository.findByCodigoProducto(codigoProducto)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con codigo: " + codigoProducto));
    }

    private void validarCantidad(Integer cantidad) {
        if (cantidad == 0) {
            throw new IllegalArgumentException("La cantidad del movimiento no puede ser cero");
        }
    }

    private void aplicarMovimientoStock(Producto producto, Integer cantidad) {
        Integer nuevoStock = producto.getStockActual() + cantidad;
        if (nuevoStock < 0) {
            throw new IllegalArgumentException("El movimiento deja el stock negativo para el producto: " + producto.getCodigoProducto());
        }
        producto.setStockActual(nuevoStock);
    }

    private void revertirMovimientoStock(Producto producto, Integer cantidad) {
        producto.setStockActual(producto.getStockActual() - cantidad);
    }
}
