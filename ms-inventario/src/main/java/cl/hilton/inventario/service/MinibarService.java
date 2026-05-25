package cl.hilton.inventario.service;

import cl.hilton.inventario.dto.MiniBarRequest;
import cl.hilton.inventario.dto.MiniBarResponse;
import cl.hilton.inventario.model.MiniBar;
import cl.hilton.inventario.model.Producto;
import cl.hilton.inventario.model.ProjHabitacion;
import cl.hilton.inventario.repository.MiniBarRepository;
import cl.hilton.inventario.repository.ProductoRepository;
import cl.hilton.inventario.repository.ProjHabitacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MiniBarService {

    private final MiniBarRepository miniBarRepository;
    private final ProductoRepository productoRepository;
    private final ProjHabitacionRepository habitacionRepository;

    public List<MiniBarResponse> listar() {
        return miniBarRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public MiniBarResponse buscarPorId(Long id) {
        return toResponse(obtenerMiniBar(id));
    }

    public List<MiniBarResponse> buscarPorHabitacion(String numeroHabitacion) {
        return miniBarRepository.findByHabitacionNumeroHabitacion(numeroHabitacion).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<MiniBarResponse> buscarPorProducto(String codigoProducto) {
        return miniBarRepository.findByProductoCodigoProducto(codigoProducto).stream()
                .map(this::toResponse)
                .toList();
    }

    public MiniBarResponse buscarPorHabitacionYProducto(String numeroHabitacion, String codigoProducto) {
        MiniBar miniBar = miniBarRepository
                .findByHabitacionNumeroHabitacionAndProductoCodigoProducto(numeroHabitacion, codigoProducto)
                .orElseThrow(() -> new RuntimeException("Registro de minibar no encontrado"));

        return toResponse(miniBar);
    }

    public MiniBarResponse crear(MiniBarRequest request) {
        if (miniBarRepository.existsByHabitacionNumeroHabitacionAndProductoCodigoProducto(
                request.getNumeroHabitacion(),
                request.getCodigoProducto()
        )) {
            throw new RuntimeException("El producto ya existe en el minibar de esa habitación");
        }

        ProjHabitacion habitacion = habitacionRepository.findById(request.getNumeroHabitacion())
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

        Producto producto = productoRepository.findByCodigoProducto(request.getCodigoProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        MiniBar miniBar = MiniBar.builder()
                .habitacion(habitacion)
                .producto(producto)
                .cantidad(request.getCantidad())
                .precioUnitUsd(request.getPrecioUnitUsd())
                .build();

        return toResponse(miniBarRepository.save(miniBar));
    }

    public MiniBarResponse actualizar(Long id, MiniBarRequest request) {
        MiniBar miniBar = obtenerMiniBar(id);

        ProjHabitacion habitacion = habitacionRepository.findById(request.getNumeroHabitacion())
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

        Producto producto = productoRepository.findByCodigoProducto(request.getCodigoProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        miniBar.setHabitacion(habitacion);
        miniBar.setProducto(producto);
        miniBar.setCantidad(request.getCantidad());
        miniBar.setPrecioUnitUsd(request.getPrecioUnitUsd());

        return toResponse(miniBarRepository.save(miniBar));
    }

    public MiniBarResponse actualizarCantidad(Long id, Integer cantidad) {
        MiniBar miniBar = obtenerMiniBar(id);
        miniBar.setCantidad(cantidad);

        return toResponse(miniBarRepository.save(miniBar));
    }

    public void eliminar(Long id) {
        MiniBar miniBar = obtenerMiniBar(id);
        miniBarRepository.delete(miniBar);
    }

    private MiniBar obtenerMiniBar(Long id) {
        return miniBarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Minibar no encontrado"));
    }

    private MiniBarResponse toResponse(MiniBar miniBar) {
        return MiniBarResponse.builder()
                .id(miniBar.getId())
                .numeroHabitacion(miniBar.getHabitacion().getNumeroHabitacion())
                .codigoProducto(miniBar.getProducto().getCodigoProducto())
                .nombreProducto(miniBar.getProducto().getNombre())
                .cantidad(miniBar.getCantidad())
                .precioUnitUsd(miniBar.getPrecioUnitUsd())
                .build();
    }
}