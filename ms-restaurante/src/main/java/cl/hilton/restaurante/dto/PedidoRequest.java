package cl.hilton.restaurante.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PedidoRequest {

    @NotBlank(message = "El numero de pedido es obligatorio")
    @Size(max = 20, message = "El numero de pedido no puede superar los 20 caracteres")
    private String numeroPedido;

    @Size(max = 10, message = "El numero de mesa no puede superar los 10 caracteres")
    private String numeroMesa;

    @Email(message = "El email del huesped debe tener formato valido")
    @Size(max = 120, message = "El email del huesped no puede superar los 120 caracteres")
    private String emailHuesped;

    @Pattern(
        regexp = "ABIERTO|EN_COCINA|SERVIDO|PAGADO|CANCELADO",
        message = "El estado debe ser: ABIERTO, EN_COCINA, SERVIDO, PAGADO o CANCELADO"
    )
    private String estado;

    @Min(value = 0, message = "El total en USD no puede ser negativo")
    private Integer totalUsd;
}
