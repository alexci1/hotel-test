package cl.hilton.restaurante.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoResponse {

    private Integer id;
    private String numeroPedido;
    private String numeroMesa;
    private String emailHuesped;
    private String nombreHuesped;
    private String estado;
    private BigDecimal totalUsd;
    private OffsetDateTime creadoEn;
}
