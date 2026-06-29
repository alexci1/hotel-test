package cl.hilton.restaurante.dto;

import org.springframework.hateoas.RepresentationModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class ItemPedidoResponse extends RepresentationModel<ItemPedidoResponse> {
    private Long id;
    private String numeroPedido;
    private String nombreProducto;
    private Integer cantidad;
    private Integer precioUnitUsd;
    private String observacion;
}