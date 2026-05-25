package cl.hilton.restaurante.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPedidoResponse {

    private Long id;
    private String numeroPedido;
    private String nombreProducto;
    private Integer cantidad;
    private Integer precioUnitUsd;
    private String observacion;
}