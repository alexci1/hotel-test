package cl.hilton.inventario.service;


import cl.hilton.inventario.dto.MinibarRequest;
import cl.hilton.inventario.dto.MinibarResponse;
import cl.hilton.inventario.model.Minibar;
import cl.hilton.inventario.model.Producto;
import cl.hilton.inventario.model.ProjHabitacion;
import cl.hilton.inventario.repository.MinibarRepository;
import cl.hilton.inventario.repository.ProductoRepository;
import cl.hilton.inventario.repository.ProjHabitacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MinibarService {

    private final MinibarRepository minibarRepository;
    private final ProductoRepository productoRepository;
    private final ProjHabitacionRepository habitacionRepository;

    public List<MinibarResponse> listar() {
        return minibarRepository.findAll().stream().map(this::toResponse).toList();
    }

    public MinibarResponse buscarPorId(Integer id) {
        return toResponse(obtenerMinibar(id));
    }

    public List<MinibarResponse> buscarPorHabitacion(String numeroHabitacion) {
        return minibarRepository.findByHabitacionNumeroHabitacion(numeroHabitacion)
                .stream().map(this::toResponse).toList();
    }

    public List<MinibarResponse> buscarPorProducto(String codigoProducto) {
        return minibarRepository.findByProductoCodigoProducto(codigoProducto)
                .stream().map(this::toResponse).toList();
    }

    public MinibarResponse buscarPorHabitacionYProducto(String numeroHabitacion, String codigoProducto) {
        Minibar minibar = minibarRepository
                .findByHabitacionNumeroHabitacionAndProductoCodigoProducto(numeroHabitacion, codigoProducto)
                .orElseThrow(() -> new RuntimeException("Registro de minibar no encontrado"));
        return toResponse(minibar);
    }

    public MinibarResponse crear(MinibarRequest request) {
        if (minibarRepository.existsByHabitacionNumeroHabitacionAndProductoCodigoProducto(
                request.getNumeroHabitacion(),
                request.getCodigoProducto())) {
            throw new RuntimeException("El producto ya existe en el minibar de esa habitación");
        }

        ProjHabitacion habitacion = habitacionRepository.findById(request.getNumeroHabitacion())
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

        Producto producto = productoRepository.findByCodigoProducto(request.getCodigoProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Minibar minibar = Minibar.builder()
                .habitacion(habitacion)
                .producto(producto)
                .cantidad(request.getCantidad())
                .precioUnitUsd(request.getPrecioUnitUsd())
                .build();

        return toResponse(minibarRepository.save(minibar));
    }

    public MinibarResponse actualizar(Integer id, MinibarRequest request) {
        Minibar minibar = obtenerMinibar(id);

        ProjHabitacion habitacion = habitacionRepository.findById(request.getNumeroHabitacion())
                .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

        Producto producto = productoRepository.findByCodigoProducto(request.getCodigoProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        minibar.setHabitacion(habitacion);
        minibar.setProducto(producto);
        minibar.setCantidad(request.getCantidad());
        minibar.setPrecioUnitUsd(request.getPrecioUnitUsd());

        return toResponse(minibarRepository.save(minibar));
    }

    public MinibarResponse actualizarCantidad(Integer id, Short cantidad) {
        Minibar minibar = obtenerMinibar(id);
        minibar.setCantidad(cantidad);
        return toResponse(minibarRepository.save(minibar));
    }

    public void eliminar(Integer id) {
        Minibar minibar = obtenerMinibar(id);
        minibarRepository.delete(minibar);
    }

    private Minibar obtenerMinibar(Integer id) {
        return minibarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Minibar no encontrado"));
    }

    private MinibarResponse toResponse(Minibar minibar) {
        return MinibarResponse.builder()
                .id(minibar.getId())
                .numeroHabitacion(minibar.getHabitacion().getNumeroHabitacion())
                .codigoProducto(minibar.getProducto().getCodigoProducto())
                .nombreProducto(minibar.getProducto().getNombre())
                .cantidad(minibar.getCantidad())
                .precioUnitUsd(minibar.getPrecioUnitUsd())
                .build();
    }
}
