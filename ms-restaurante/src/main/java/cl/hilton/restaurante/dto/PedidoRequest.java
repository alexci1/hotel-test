package cl.hilton.restaurante.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

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
    @DecimalMin(value = "0.00")
    private BigDecimal totalUsd;

    private OffsetDateTime creadoEn;
}
