package cl.hilton.inventario.service;

import cl.hilton.inventario.dto.ProductoRequest;
import cl.hilton.inventario.dto.ProductoResponse;
import cl.hilton.inventario.model.Producto;
import cl.hilton.inventario.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    public List<ProductoResponse> listar() {
        return productoRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductoResponse buscarPorId(Long id) {
        return toResponse(obtenerProducto(id));
    }

    public ProductoResponse buscarPorCodigo(String codigoProducto) {
        Producto producto = productoRepository.findByCodigoProducto(codigoProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        return toResponse(producto);
    }

    public List<ProductoResponse> buscarPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ProductoResponse> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ProductoResponse> buscarStockMenorOIgual(Long stock) {
        return productoRepository.findByStockActualLessThanEqual(stock).stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductoResponse crear(ProductoRequest request) {
        if (productoRepository.existsByCodigoProducto(request.getCodigoProducto())) {
            throw new RuntimeException("Ya existe un producto con ese código");
        }

        Producto producto = Producto.builder()
                .codigoProducto(request.getCodigoProducto())
                .nombre(request.getNombre())
                .categoria(request.getCategoria())
                .stockActual(request.getStockActual())
                .stockMinimo(request.getStockMinimo())
                .unidad(request.getUnidad())
                .build();

        return toResponse(productoRepository.save(producto));
    }

    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        Producto producto = obtenerProducto(id);

        producto.setCodigoProducto(request.getCodigoProducto());
        producto.setNombre(request.getNombre());
        producto.setCategoria(request.getCategoria());
        producto.setStockActual(request.getStockActual());
        producto.setStockMinimo(request.getStockMinimo());
        producto.setUnidad(request.getUnidad());

        return toResponse(productoRepository.save(producto));
    }

    public ProductoResponse ajustarStock(Long id, Long cantidad) {
        Producto producto = obtenerProducto(id);
        producto.setStockActual(producto.getStockActual() + cantidad);

        return toResponse(productoRepository.save(producto));
    }

    public void eliminar(Long id) {
        Producto producto = obtenerProducto(id);
        productoRepository.delete(producto);
    }

    private Producto obtenerProducto(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    private ProductoResponse toResponse(Producto producto) {
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
}