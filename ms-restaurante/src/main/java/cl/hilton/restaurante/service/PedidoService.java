package cl.hilton.restaurante.service;

import cl.hilton.restaurante.dto.PedidoRequest;
import cl.hilton.restaurante.dto.PedidoResponse;
import cl.hilton.restaurante.model.Mesa;
import cl.hilton.restaurante.model.Pedido;
import cl.hilton.restaurante.model.ProjHuesped;
import cl.hilton.restaurante.repository.MesaRepository;
import cl.hilton.restaurante.repository.PedidoRepository;
import cl.hilton.restaurante.repository.ProjHuespedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;
    private final ProjHuespedRepository huespedRepository;

    public List<PedidoResponse> listar() {
        return pedidoRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public PedidoResponse buscarPorId(Long id) {
        return toResponse(obtenerPedido(id));
    }

    public PedidoResponse buscarPorNumeroPedido(String numeroPedido) {
        Pedido pedido = pedidoRepository.findByNumeroPedido(numeroPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        return toResponse(pedido);
    }

    public List<PedidoResponse> buscarPorEstado(String estado) {
        return pedidoRepository.findByEstado(estado).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<PedidoResponse> buscarPorMesa(String numeroMesa) {
        return pedidoRepository.findByMesaNumeroMesa(numeroMesa).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<PedidoResponse> buscarPorHuesped(String email) {
        return pedidoRepository.findByHuespedEmail(email).stream()
                .map(this::toResponse)
                .toList();
    }

    public PedidoResponse crear(PedidoRequest request) {
        if (pedidoRepository.existsByNumeroPedido(request.getNumeroPedido())) {
            throw new RuntimeException("Ya existe un pedido con ese número");
        }

        Mesa mesa = obtenerMesaOpcional(request.getNumeroMesa());
        ProjHuesped huesped = obtenerHuespedOpcional(request.getEmailHuesped());

        if (mesa == null && huesped == null) {
            throw new RuntimeException("El pedido debe tener mesa o huésped");
        }

        Pedido pedido = Pedido.builder()
                .numeroPedido(request.getNumeroPedido())
                .mesa(mesa)
                .huesped(huesped)
                .estado(request.getEstado())
                .totalUsd(request.getTotalUsd())
                .creadoEn(request.getCreadoEn() != null ? request.getCreadoEn() : LocalDate.now())
                .build();

        return toResponse(pedidoRepository.save(pedido));
    }

    public PedidoResponse actualizar(Long id, PedidoRequest request) {
        Pedido pedido = obtenerPedido(id);

        Mesa mesa = obtenerMesaOpcional(request.getNumeroMesa());
        ProjHuesped huesped = obtenerHuespedOpcional(request.getEmailHuesped());

        if (mesa == null && huesped == null) {
            throw new RuntimeException("El pedido debe tener mesa o huésped");
        }

        pedido.setNumeroPedido(request.getNumeroPedido());
        pedido.setMesa(mesa);
        pedido.setHuesped(huesped);
        pedido.setEstado(request.getEstado());
        pedido.setTotalUsd(request.getTotalUsd());
        pedido.setCreadoEn(request.getCreadoEn() != null ? request.getCreadoEn() : pedido.getCreadoEn());

        return toResponse(pedidoRepository.save(pedido));
    }

    public PedidoResponse cambiarEstado(Long id, String estado) {
        Pedido pedido = obtenerPedido(id);
        pedido.setEstado(estado);

        return toResponse(pedidoRepository.save(pedido));
    }

    public void eliminar(Long id) {
        Pedido pedido = obtenerPedido(id);
        pedidoRepository.delete(pedido);
    }

    private Mesa obtenerMesaOpcional(String numeroMesa) {
        if (!StringUtils.hasText(numeroMesa)) {
            return null;
        }

        return mesaRepository.findByNumeroMesa(numeroMesa)
                .orElseThrow(() -> new RuntimeException("Mesa no encontrada"));
    }

    private ProjHuesped obtenerHuespedOpcional(String email) {
        if (!StringUtils.hasText(email)) {
            return null;
        }

        return huespedRepository.findById(email)
                .orElseThrow(() -> new RuntimeException("Huésped no encontrado"));
    }

    private Pedido obtenerPedido(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    private PedidoResponse toResponse(Pedido pedido) {
        return PedidoResponse.builder()
                .id(pedido.getId())
                .numeroPedido(pedido.getNumeroPedido())
                .numeroMesa(pedido.getMesa() != null ? pedido.getMesa().getNumeroMesa() : null)
                .emailHuesped(pedido.getHuesped() != null ? pedido.getHuesped().getEmail() : null)
                .nombreHuesped(pedido.getHuesped() != null ? pedido.getHuesped().getNombreCompleto() : null)
                .estado(pedido.getEstado())
                .totalUsd(pedido.getTotalUsd())
                .creadoEn(pedido.getCreadoEn())
                .build();
    }
}