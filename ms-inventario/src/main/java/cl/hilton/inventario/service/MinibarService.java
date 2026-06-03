package cl.hilton.inventario.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@SuppressWarnings("null")
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
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");
        return miniBarMapper.toResponseList(miniBarRepository.findByHabitacionNumeroHabitacion(numero));
    }

    public List<MiniBarResponse> findByCodigoProducto(String codigoProducto) {
        String codigo = validarTexto(codigoProducto, "codigoProducto");
        return miniBarMapper.toResponseList(miniBarRepository.findByProductoCodigoProducto(codigo));
    }

    public MiniBarResponse findByHabitacionAndProducto(String numeroHabitacion, String codigoProducto) {
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");
        String codigo = validarTexto(codigoProducto, "codigoProducto");

        MiniBar miniBar = miniBarRepository.findByHabitacionNumeroHabitacionAndProductoCodigoProducto(numero, codigo)
                .orElseThrow(() -> new EntityNotFoundException("Registro de minibar no encontrado para habitacion y producto indicados"));

        return miniBarMapper.toResponse(miniBar);
    }

    public List<MiniBarResponse> findByCantidad(Integer cantidad) {
        Integer cantidadValida = validarInteger(cantidad, "cantidad");
        return miniBarMapper.toResponseList(miniBarRepository.findByCantidad(cantidadValida));
    }

    public List<MiniBarResponse> findByCantidadGreaterThan(Integer cantidad) {
        Integer cantidadValida = validarInteger(cantidad, "cantidad");
        return miniBarMapper.toResponseList(miniBarRepository.findByCantidadGreaterThan(cantidadValida));
    }

    public List<MiniBarResponse> findByPrecioUnitUsdGreaterThan(Integer precioUnitUsd) {
        Integer precio = validarInteger(precioUnitUsd, "precioUnitUsd");
        return miniBarMapper.toResponseList(miniBarRepository.findByPrecioUnitUsdGreaterThan(precio));
    }

    @Transactional
    public MiniBarResponse create(MiniBarRequest request) {
        String numeroHabitacion = validarTexto(request.getNumeroHabitacion(), "numeroHabitacion");
        String codigoProducto = validarTexto(request.getCodigoProducto(), "codigoProducto");

        validarRegistroUnico(numeroHabitacion, codigoProducto);

        ProjHabitacion habitacion = getHabitacionByNumero(numeroHabitacion);
        Producto producto = getProductoByCodigo(codigoProducto);

        MiniBar miniBar = miniBarMapper.toEntity(request);
        miniBar.setHabitacion(habitacion);
        miniBar.setProducto(producto);
        miniBar.setCantidad(request.getCantidad() != null ? request.getCantidad() : 0);

        MiniBar miniBarGuardado = miniBarRepository.save(miniBar);

        return miniBarMapper.toResponse(miniBarGuardado);
    }

    @Transactional
    public MiniBarResponse update(Long id, MiniBarRequest request) {
        Long miniBarId = validarId(id);
        String numeroHabitacion = validarTexto(request.getNumeroHabitacion(), "numeroHabitacion");
        String codigoProducto = validarTexto(request.getCodigoProducto(), "codigoProducto");

        MiniBar miniBar = getMiniBarById(miniBarId);
        Integer cantidadActual = miniBar.getCantidad();

        if (!miniBar.getHabitacion().getNumeroHabitacion().equalsIgnoreCase(numeroHabitacion)
                || !miniBar.getProducto().getCodigoProducto().equalsIgnoreCase(codigoProducto)) {
            validarRegistroUnico(numeroHabitacion, codigoProducto);
        }

        ProjHabitacion habitacion = getHabitacionByNumero(numeroHabitacion);
        Producto producto = getProductoByCodigo(codigoProducto);

        miniBarMapper.updateEntity(request, miniBar);
        miniBar.setHabitacion(habitacion);
        miniBar.setProducto(producto);
        miniBar.setCantidad(request.getCantidad() != null ? request.getCantidad() : cantidadActual);

        MiniBar miniBarActualizado = miniBarRepository.save(miniBar);

        return miniBarMapper.toResponse(miniBarActualizado);
    }

    @Transactional
    public MiniBarResponse actualizarCantidad(Long id, Integer cantidad) {
        Long miniBarId = validarId(id);
        Integer cantidadValida = validarInteger(cantidad, "cantidad");

        if (cantidadValida < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }

        MiniBar miniBar = getMiniBarById(miniBarId);
        miniBar.setCantidad(cantidadValida);

        MiniBar miniBarActualizado = miniBarRepository.save(miniBar);

        return miniBarMapper.toResponse(miniBarActualizado);
    }

    @Transactional
    public void deleteById(Long id) {
        Long miniBarId = validarId(id);
        getMiniBarById(miniBarId);
        miniBarRepository.deleteById(miniBarId);
    }

    private MiniBar getMiniBarById(Long id) {
        Long miniBarId = validarId(id);

        return miniBarRepository.findById(miniBarId)
                .orElseThrow(() -> new EntityNotFoundException("Minibar no encontrado con id: " + miniBarId));
    }

    private ProjHabitacion getHabitacionByNumero(String numeroHabitacion) {
        String numero = validarTexto(numeroHabitacion, "numeroHabitacion");

        return habitacionRepository.findByNumeroHabitacion(numero)
                .orElseThrow(() -> new EntityNotFoundException("Habitacion proyectada no encontrada con numero: " + numero));
    }

    private Producto getProductoByCodigo(String codigoProducto) {
        String codigo = validarTexto(codigoProducto, "codigoProducto");

        return productoRepository.findByCodigoProducto(codigo)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con codigo: " + codigo));
    }

    private void validarRegistroUnico(String numeroHabitacion, String codigoProducto) {
        if (miniBarRepository.existsByHabitacionNumeroHabitacionAndProductoCodigoProducto(numeroHabitacion, codigoProducto)) {
            throw new IllegalArgumentException("Ya existe ese producto en el minibar de la habitacion indicada");
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
