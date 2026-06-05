package cl.hilton.restaurante.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cl.hilton.restaurante.dto.PedidoRequest;
import cl.hilton.restaurante.dto.PedidoResponse;
import cl.hilton.restaurante.mapper.PedidoMapper;
import cl.hilton.restaurante.model.Mesa;
import cl.hilton.restaurante.model.Pedido;
import cl.hilton.restaurante.model.ProjHuesped;
import cl.hilton.restaurante.repository.MesaRepository;
import cl.hilton.restaurante.repository.PedidoRepository;
import cl.hilton.restaurante.repository.ProjHuespedRepository;
import cl.hilton.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;
    private final ProjHuespedRepository huespedRepository;
    private final PedidoMapper pedidoMapper;

    public List<PedidoResponse> findAll() {
        return pedidoMapper.toResponseList(pedidoRepository.findAll());
    }

    public PedidoResponse findById(Long id) {
        Pedido pedido = getPedidoById(id);
        return pedidoMapper.toResponse(pedido);
    }

    public PedidoResponse findByNumeroPedido(String numeroPedido) {
        Pedido pedido = pedidoRepository.findByNumeroPedido(numeroPedido)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con numero: " + numeroPedido));

        return pedidoMapper.toResponse(pedido);
    }

    public List<PedidoResponse> findByEstado(String estado) {
        return pedidoMapper.toResponseList(pedidoRepository.findByEstado(estado));
    }

    public List<PedidoResponse> findByNumeroMesa(String numeroMesa) {
        return pedidoMapper.toResponseList(pedidoRepository.findByMesaNumeroMesa(numeroMesa));
    }

    public List<PedidoResponse> findByEmailHuesped(String emailHuesped) {
        return pedidoMapper.toResponseList(pedidoRepository.findByHuespedEmail(emailHuesped));
    }

    public List<PedidoResponse> findByRangoCreadoEn(LocalDate desde, LocalDate hasta) {
        return pedidoMapper.toResponseList(pedidoRepository.findByCreadoEnBetween(desde, hasta));
    }

    public List<PedidoResponse> findByNumeroMesaAndEstado(String numeroMesa, String estado) {
        return pedidoMapper.toResponseList(pedidoRepository.findByMesaNumeroMesaAndEstado(numeroMesa, estado));
    }

    public List<PedidoResponse> findByEmailHuespedAndEstado(String emailHuesped, String estado) {
        return pedidoMapper.toResponseList(pedidoRepository.findByHuespedEmailAndEstado(emailHuesped, estado));
    }

    public PedidoResponse create(PedidoRequest request) {
        validarNumeroPedidoUnico(request.getNumeroPedido());

        Mesa mesa = getMesaOpcional(request.getNumeroMesa());
        ProjHuesped huesped = getHuespedOpcional(request.getEmailHuesped());
        validarOrigen(mesa, huesped);

        Pedido pedido = pedidoMapper.toEntity(request);
        pedido.setMesa(mesa);
        pedido.setHuesped(huesped);
        pedido.setEstado(request.getEstado() != null ? request.getEstado() : "ABIERTO");
        pedido.setTotalUsd(request.getTotalUsd() != null ? request.getTotalUsd() : 0);
        pedido.setCreadoEn(LocalDate.now());

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        return pedidoMapper.toResponse(pedidoGuardado);
    }

    public PedidoResponse update(Long id, PedidoRequest request) {
        Pedido pedido = getPedidoById(id);
        String estadoActual = pedido.getEstado();
        Integer totalActual = pedido.getTotalUsd();

        if (!pedido.getNumeroPedido().equalsIgnoreCase(request.getNumeroPedido())) {
            validarNumeroPedidoUnico(request.getNumeroPedido());
        }

        Mesa mesa = getMesaOpcional(request.getNumeroMesa());
        ProjHuesped huesped = getHuespedOpcional(request.getEmailHuesped());
        validarOrigen(mesa, huesped);

        pedidoMapper.updateEntity(request, pedido);
        pedido.setMesa(mesa);
        pedido.setHuesped(huesped);
        pedido.setEstado(request.getEstado() != null ? request.getEstado() : estadoActual);
        pedido.setTotalUsd(request.getTotalUsd() != null ? request.getTotalUsd() : totalActual);

        Pedido pedidoActualizado = pedidoRepository.save(pedido);

        return pedidoMapper.toResponse(pedidoActualizado);
    }

    public PedidoResponse cambiarEstado(Long id, String estado) {
        Pedido pedido = getPedidoById(id);
        pedido.setEstado(estado);

        Pedido pedidoActualizado = pedidoRepository.save(pedido);

        return pedidoMapper.toResponse(pedidoActualizado);
    }

    public void deleteById(Long id) {
        Pedido pedido = getPedidoById(id);
        pedidoRepository.delete(pedido);
    }

    private Pedido getPedidoById(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con id: " + id));
    }

    private Mesa getMesaOpcional(String numeroMesa) {
        if (!StringUtils.hasText(numeroMesa)) {
            return null;
        }

        return mesaRepository.findByNumeroMesa(numeroMesa)
                .orElseThrow(() -> new EntityNotFoundException("Mesa no encontrada con numero: " + numeroMesa));
    }

    private ProjHuesped getHuespedOpcional(String email) {
        if (!StringUtils.hasText(email)) {
            return null;
        }

        return huespedRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Huesped proyectado no encontrado con email: " + email));
    }

    private void validarOrigen(Mesa mesa, ProjHuesped huesped) {
        if (mesa == null && huesped == null) {
            throw new IllegalArgumentException("El pedido debe tener mesa o huesped");
        }
    }

    private void validarNumeroPedidoUnico(String numeroPedido) {
        if (pedidoRepository.existsByNumeroPedido(numeroPedido)) {
            throw new IllegalArgumentException("Ya existe un pedido con numero: " + numeroPedido);
        }
    }
}
