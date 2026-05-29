package cl.hilton.inventario.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cl.hilton.inventario.dto.MiniBarRequest;
import cl.hilton.inventario.dto.MiniBarResponse;
import cl.hilton.inventario.mapper.MiniBarMapper;
import cl.hilton.inventario.model.MiniBar;
import cl.hilton.inventario.model.Producto;
import cl.hilton.inventario.model.ProjHabitacion;
import cl.hilton.inventario.repository.MiniBarRepository;
import cl.hilton.inventario.repository.ProductoRepository;
import cl.hilton.inventario.repository.ProjHabitacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MiniBarService {

    private final MiniBarRepository miniBarRepository;
    private final ProductoRepository productoRepository;
    private final ProjHabitacionRepository habitacionRepository;
    private final MiniBarMapper miniBarMapper;

    public List<MiniBarResponse> findAll() {
        return miniBarMapper.toResponseList(miniBarRepository.findAll());
    }

    public MiniBarResponse findById(Long id) {
        MiniBar miniBar = getMiniBarById(id);
        return miniBarMapper.toResponse(miniBar);
    }

    public List<MiniBarResponse> findByNumeroHabitacion(String numeroHabitacion) {
        return miniBarMapper.toResponseList(miniBarRepository.findByHabitacionNumeroHabitacion(numeroHabitacion));
    }

    public List<MiniBarResponse> findByCodigoProducto(String codigoProducto) {
        return miniBarMapper.toResponseList(miniBarRepository.findByProductoCodigoProducto(codigoProducto));
    }

    public MiniBarResponse findByHabitacionAndProducto(String numeroHabitacion, String codigoProducto) {
        MiniBar miniBar = miniBarRepository.findByHabitacionNumeroHabitacionAndProductoCodigoProducto(numeroHabitacion, codigoProducto)
                .orElseThrow(() -> new EntityNotFoundException("Registro de minibar no encontrado para habitacion y producto indicados"));

        return miniBarMapper.toResponse(miniBar);
    }

    public List<MiniBarResponse> findByCantidad(Integer cantidad) {
        return miniBarMapper.toResponseList(miniBarRepository.findByCantidad(cantidad));
    }

    public List<MiniBarResponse> findByCantidadGreaterThan(Integer cantidad) {
        return miniBarMapper.toResponseList(miniBarRepository.findByCantidadGreaterThan(cantidad));
    }

    public List<MiniBarResponse> findByPrecioUnitUsdGreaterThan(Integer precioUnitUsd) {
        return miniBarMapper.toResponseList(miniBarRepository.findByPrecioUnitUsdGreaterThan(precioUnitUsd));
    }

    public MiniBarResponse create(MiniBarRequest request) {
        validarRegistroUnico(request.getNumeroHabitacion(), request.getCodigoProducto());

        ProjHabitacion habitacion = getHabitacionByNumero(request.getNumeroHabitacion());
        Producto producto = getProductoByCodigo(request.getCodigoProducto());

        MiniBar miniBar = miniBarMapper.toEntity(request);
        miniBar.setHabitacion(habitacion);
        miniBar.setProducto(producto);
        miniBar.setCantidad(request.getCantidad() != null ? request.getCantidad() : 0);

        MiniBar miniBarGuardado = miniBarRepository.save(miniBar);

        return miniBarMapper.toResponse(miniBarGuardado);
    }

    public MiniBarResponse update(Long id, MiniBarRequest request) {
        MiniBar miniBar = getMiniBarById(id);
        Integer cantidadActual = miniBar.getCantidad();

        if (!miniBar.getHabitacion().getNumeroHabitacion().equalsIgnoreCase(request.getNumeroHabitacion())
                || !miniBar.getProducto().getCodigoProducto().equalsIgnoreCase(request.getCodigoProducto())) {
            validarRegistroUnico(request.getNumeroHabitacion(), request.getCodigoProducto());
        }

        ProjHabitacion habitacion = getHabitacionByNumero(request.getNumeroHabitacion());
        Producto producto = getProductoByCodigo(request.getCodigoProducto());

        miniBarMapper.updateEntity(request, miniBar);
        miniBar.setHabitacion(habitacion);
        miniBar.setProducto(producto);
        miniBar.setCantidad(request.getCantidad() != null ? request.getCantidad() : cantidadActual);

        MiniBar miniBarActualizado = miniBarRepository.save(miniBar);

        return miniBarMapper.toResponse(miniBarActualizado);
    }

    public MiniBarResponse actualizarCantidad(Long id, Integer cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }

        MiniBar miniBar = getMiniBarById(id);
        miniBar.setCantidad(cantidad);

        MiniBar miniBarActualizado = miniBarRepository.save(miniBar);

        return miniBarMapper.toResponse(miniBarActualizado);
    }

    public void deleteById(Long id) {
        MiniBar miniBar = getMiniBarById(id);
        miniBarRepository.delete(miniBar);
    }

    private MiniBar getMiniBarById(Long id) {
        return miniBarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Minibar no encontrado con id: " + id));
    }

    private ProjHabitacion getHabitacionByNumero(String numeroHabitacion) {
        return habitacionRepository.findByNumeroHabitacion(numeroHabitacion)
                .orElseThrow(() -> new EntityNotFoundException("Habitacion proyectada no encontrada con numero: " + numeroHabitacion));
    }

    private Producto getProductoByCodigo(String codigoProducto) {
        return productoRepository.findByCodigoProducto(codigoProducto)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con codigo: " + codigoProducto));
    }

    private void validarRegistroUnico(String numeroHabitacion, String codigoProducto) {
        if (miniBarRepository.existsByHabitacionNumeroHabitacionAndProductoCodigoProducto(numeroHabitacion, codigoProducto)) {
            throw new IllegalArgumentException("Ya existe ese producto en el minibar de la habitacion indicada");
        }
    }
}
