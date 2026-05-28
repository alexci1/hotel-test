package cl.hilton.pagos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FacturaRequest {

    @NotBlank(message = "El numero de factura es obligatorio")
    @Size(max = 20, message = "El numero de factura no puede superar los 20 caracteres")
    private String numeroFactura;

    @NotBlank(message = "El codigo de reserva es obligatorio")
    @Size(max = 20, message = "El codigo de reserva no puede superar los 20 caracteres")
    private String codigoReserva;

    @NotBlank(message = "El email del huesped es obligatorio")
    @Email(message = "El email del huesped debe tener formato valido")
    @Size(max = 120, message = "El email del huesped no puede superar los 120 caracteres")
    private String emailHuesped;

    @NotNull(message = "El total en USD es obligatorio")
    @PositiveOrZero(message = "El total en USD no puede ser negativo")
    private Integer totalUsd;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(
        regexp = "PENDIENTE|PARCIAL|PAGADA|ANULADA",
        message = "El estado debe ser: PENDIENTE, PARCIAL, PAGADA o ANULADA"
    )
    private String estado;
}
