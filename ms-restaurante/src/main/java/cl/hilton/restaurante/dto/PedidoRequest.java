package cl.hilton.restaurante.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PedidoRequest {

    @NotBlank
    @Size(max = 20)
    private String numeroPedido;

    @Size(max = 10)
    private String numeroMesa;

    @Email
    @Size(max = 120)
    private String emailHuesped;

    @NotBlank
    @Size(max = 20)
    private String estado;

    @NotNull
    @Min(0)
    private Integer totalUsd;

    private LocalDate creadoEn;
}