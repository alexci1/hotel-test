package cl.hilton.common.event;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PedidoUpdatedEvent implements PedidoEvent {

    private Long id;
    private String numeroPedido;
    private String numeroMesa;
    private String emailHuesped;
    private String nombreHuesped;
    private String estado;
    private Integer totalUsd;
    private LocalDate creadoEn;
}
