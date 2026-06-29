package cl.hilton.restaurante.dto;

import java.time.LocalDate;
import java.util.List;
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
public class PedidoResponse extends RepresentationModel<PedidoResponse> {
    private Long id;
    private String numeroPedido;
    private String numeroMesa;
    private String emailHuesped;
    private String nombreHuesped;
    private String estado;
    private Integer totalUsd;
    private LocalDate creadoEn;
    private List<ItemPedidoResponse> items;
}