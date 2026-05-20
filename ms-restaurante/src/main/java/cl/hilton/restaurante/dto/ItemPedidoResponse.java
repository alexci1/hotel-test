package cl.hilton.restaurante.dto;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPedidoResponse {

    private Integer id;
    private String numeroPedido;
    private String nombreProducto;
    private Short cantidad;
    private BigDecimal precioUnitUsd;
    private String observacion;
}
