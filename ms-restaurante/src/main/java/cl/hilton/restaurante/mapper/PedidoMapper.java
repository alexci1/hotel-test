package cl.hilton.restaurante.mapper;


import cl.hilton.restaurante.dto.PedidoRequest;
import cl.hilton.restaurante.dto.PedidoResponse;
import cl.hilton.restaurante.model.Mesa;
import cl.hilton.restaurante.model.Pedido;
import cl.hilton.restaurante.model.ProjHuesped;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    public Pedido toEntity(PedidoRequest request, Mesa mesa, ProjHuesped huesped) {
        return Pedido.builder()
                .numeroPedido(request.getNumeroPedido())
                .mesa(mesa)
                .huesped(huesped)
                .estado(request.getEstado())
                .totalUsd(request.getTotalUsd())
                .creadoEn(request.getCreadoEn())
                .build();
    }

    public PedidoResponse toResponse(Pedido pedido) {
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

    public void updateEntity(Pedido pedido, PedidoRequest request, Mesa mesa, ProjHuesped huesped) {
        pedido.setNumeroPedido(request.getNumeroPedido());
        pedido.setMesa(mesa);
        pedido.setHuesped(huesped);
        pedido.setEstado(request.getEstado());
        pedido.setTotalUsd(request.getTotalUsd());
        pedido.setCreadoEn(request.getCreadoEn());
    }
}

