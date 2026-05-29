package cl.hilton.inventario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.inventario.dto.ProductoRequest;
import cl.hilton.inventario.dto.ProductoResponse;
import cl.hilton.inventario.mapper.ProductoMapper;
import cl.hilton.inventario.model.Producto;
import cl.hilton.inventario.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    public List<ProductoResponse> findAll() {
        return productoMapper.toResponseList(productoRepository.findAll());
    }

    public ProductoResponse findById(Long id) {
        Producto producto = getProductoById(id);
        return productoMapper.toResponse(producto);
    }

    public ProductoResponse findByCodigoProducto(String codigoProducto) {
        Producto producto = productoRepository.findByCodigoProducto(codigoProducto)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con codigo: " + codigoProducto));

        return productoMapper.toResponse(producto);
    }

    public List<ProductoResponse> findByCategoria(String categoria) {
        return productoMapper.toResponseList(productoRepository.findByCategoria(categoria));
    }

    public List<ProductoResponse> findByUnidad(String unidad) {
        return productoMapper.toResponseList(productoRepository.findByUnidad(unidad));
    }

    public List<ProductoResponse> findByNombre(String nombre) {
        return productoMapper.toResponseList(productoRepository.findByNombreContainingIgnoreCase(nombre));
    }

    public List<ProductoResponse> findByStockActualLessThanEqual(Integer stockActual) {
        return productoMapper.toResponseList(productoRepository.findByStockActualLessThanEqual(stockActual));
    }

    public List<ProductoResponse> findByStockActualLessThan(Integer stockActual) {
        return productoMapper.toResponseList(productoRepository.findByStockActualLessThan(stockActual));
    }

    public List<ProductoResponse> findByStockActualGreaterThan(Integer stockActual) {
        return productoMapper.toResponseList(productoRepository.findByStockActualGreaterThan(stockActual));
    }

    public ProductoResponse create(ProductoRequest request) {
        validarCodigoUnico(request.getCodigoProducto());

        Producto producto = productoMapper.toEntity(request);
        producto.setStockActual(request.getStockActual() != null ? request.getStockActual() : 0);
        producto.setStockMinimo(request.getStockMinimo() != null ? request.getStockMinimo() : 5);
        producto.setUnidad(request.getUnidad() != null ? request.getUnidad() : "UNIDAD");

        Producto productoGuardado = productoRepository.save(producto);

        return productoMapper.toResponse(productoGuardado);
    }

    public ProductoResponse update(Long id, ProductoRequest request) {
        Producto producto = getProductoById(id);
        Integer stockActual = producto.getStockActual();
        Integer stockMinimo = producto.getStockMinimo();
        String unidadActual = producto.getUnidad();

        if (!producto.getCodigoProducto().equalsIgnoreCase(request.getCodigoProducto())) {
            validarCodigoUnico(request.getCodigoProducto());
        }

        productoMapper.updateEntity(request, producto);
        producto.setStockActual(request.getStockActual() != null ? request.getStockActual() : stockActual);
        producto.setStockMinimo(request.getStockMinimo() != null ? request.getStockMinimo() : stockMinimo);
        producto.setUnidad(request.getUnidad() != null ? request.getUnidad() : unidadActual);

        Producto productoActualizado = productoRepository.save(producto);

        return productoMapper.toResponse(productoActualizado);
    }

    public ProductoResponse ajustarStock(Long id, Integer cantidad) {
        Producto producto = getProductoById(id);
        Integer nuevoStock = producto.getStockActual() + cantidad;

        if (nuevoStock < 0) {
            throw new IllegalArgumentException("El stock no puede quedar negativo");
        }

        producto.setStockActual(nuevoStock);
        Producto productoActualizado = productoRepository.save(producto);

        return productoMapper.toResponse(productoActualizado);
    }

    public void deleteById(Long id) {
        Producto producto = getProductoById(id);
        productoRepository.delete(producto);
    }

    private Producto getProductoById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con id: " + id));
    }

    private void validarCodigoUnico(String codigoProducto) {
        if (productoRepository.existsByCodigoProducto(codigoProducto)) {
            throw new IllegalArgumentException("Ya existe un producto con codigo: " + codigoProducto);
        }
    }
}
