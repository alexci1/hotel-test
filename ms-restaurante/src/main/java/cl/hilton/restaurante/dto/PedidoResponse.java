package cl.hilton.restaurante.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoResponse {

    private Long id;
    private String numeroPedido;
    private String numeroMesa;
    private String emailHuesped;
    private String nombreHuesped;
    private String estado;
    private Integer totalUsd;
    private LocalDate creadoEn;
}