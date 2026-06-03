package cl.hilton.inventario.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.hilton.inventario.dto.ProductoRequest;
import cl.hilton.inventario.dto.ProductoResponse;
import cl.hilton.inventario.mapper.ProductoMapper;
import cl.hilton.inventario.model.Producto;
import cl.hilton.inventario.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
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
        String codigo = validarTexto(codigoProducto, "codigoProducto");

        Producto producto = productoRepository.findByCodigoProducto(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con codigo: " + codigo));

        return productoMapper.toResponse(producto);
    }

    public List<ProductoResponse> findByCategoria(String categoria) {
        String categoriaValida = validarTexto(categoria, "categoria");
        return productoMapper.toResponseList(productoRepository.findByCategoria(categoriaValida));
    }

    public List<ProductoResponse> findByUnidad(String unidad) {
        String unidadValida = validarTexto(unidad, "unidad");
        return productoMapper.toResponseList(productoRepository.findByUnidad(unidadValida));
    }

    public List<ProductoResponse> findByNombre(String nombre) {
        String nombreValido = validarTexto(nombre, "nombre");
        return productoMapper.toResponseList(productoRepository.findByNombreContainingIgnoreCase(nombreValido));
    }

    public List<ProductoResponse> findByStockActualLessThanEqual(Integer stockActual) {
        Integer stock = validarInteger(stockActual, "stockActual");
        return productoMapper.toResponseList(productoRepository.findByStockActualLessThanEqual(stock));
    }

    public List<ProductoResponse> findByStockActualLessThan(Integer stockActual) {
        Integer stock = validarInteger(stockActual, "stockActual");
        return productoMapper.toResponseList(productoRepository.findByStockActualLessThan(stock));
    }

    public List<ProductoResponse> findByStockActualGreaterThan(Integer stockActual) {
        Integer stock = validarInteger(stockActual, "stockActual");
        return productoMapper.toResponseList(productoRepository.findByStockActualGreaterThan(stock));
    }

    @Transactional
    public ProductoResponse create(ProductoRequest request) {
        String codigo = validarTexto(request.getCodigoProducto(), "codigoProducto");
        validarCodigoUnico(codigo);

        Producto producto = productoMapper.toEntity(request);
        producto.setStockActual(request.getStockActual() != null ? request.getStockActual() : 0);
        producto.setStockMinimo(request.getStockMinimo() != null ? request.getStockMinimo() : 5);
        producto.setUnidad(request.getUnidad() != null ? request.getUnidad() : "UNIDAD");

        Producto productoGuardado = productoRepository.save(producto);

        return productoMapper.toResponse(productoGuardado);
    }

    @Transactional
    public ProductoResponse update(Long id, ProductoRequest request) {
        Long productoId = validarId(id);
        String codigo = validarTexto(request.getCodigoProducto(), "codigoProducto");

        Producto producto = getProductoById(productoId);
        Integer stockActual = producto.getStockActual();
        Integer stockMinimo = producto.getStockMinimo();
        String unidadActual = producto.getUnidad();

        if (!producto.getCodigoProducto().equalsIgnoreCase(codigo)) {
            validarCodigoUnico(codigo);
        }

        productoMapper.updateEntity(request, producto);
        producto.setStockActual(request.getStockActual() != null ? request.getStockActual() : stockActual);
        producto.setStockMinimo(request.getStockMinimo() != null ? request.getStockMinimo() : stockMinimo);
        producto.setUnidad(request.getUnidad() != null ? request.getUnidad() : unidadActual);

        Producto productoActualizado = productoRepository.save(producto);

        return productoMapper.toResponse(productoActualizado);
    }

    @Transactional
    public ProductoResponse ajustarStock(Long id, Integer cantidad) {
        Long productoId = validarId(id);
        Integer cantidadValida = validarInteger(cantidad, "cantidad");

        Producto producto = getProductoById(productoId);
        Integer nuevoStock = producto.getStockActual() + cantidadValida;

        if (nuevoStock < 0) {
            throw new IllegalArgumentException("El stock no puede quedar negativo");
        }

        producto.setStockActual(nuevoStock);
        Producto productoActualizado = productoRepository.save(producto);

        return productoMapper.toResponse(productoActualizado);
    }

    @Transactional
    public void deleteById(Long id) {
        Long productoId = validarId(id);
        getProductoById(productoId);
        productoRepository.deleteById(productoId);
    }

    private Producto getProductoById(Long id) {
        Long productoId = validarId(id);

        return productoRepository.findById(productoId)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con id: " + productoId));
    }

    private void validarCodigoUnico(String codigoProducto) {
        if (productoRepository.existsByCodigoProducto(codigoProducto)) {
            throw new IllegalArgumentException("Ya existe un producto con codigo: " + codigoProducto);
        }
    }

    private Long validarId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        return id;
    }

    private Integer validarInteger(Integer valor, String campo) {
        if (valor == null) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser nulo");
        }
        return valor;
    }

    private String validarTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El campo " + campo + " no puede ser nulo o vacio");
        }
        return valor;
    }
}
