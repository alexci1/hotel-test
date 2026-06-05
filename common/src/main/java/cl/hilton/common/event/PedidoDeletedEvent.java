package cl.hilton.common.event;

import lombok.Data;

@Data
public class PedidoDeletedEvent implements PedidoEvent {

    private Long id;
    private String numeroPedido;
}
