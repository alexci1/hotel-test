package cl.hilton.pagos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PagoRequest {

    @NotBlank(message = "El numero de factura es obligatorio")
    @Size(max = 20, message = "El numero de factura no puede superar los 20 caracteres")
    private String numeroFactura;

    @NotNull(message = "El monto en USD es obligatorio")
    @Positive(message = "El monto en USD debe ser mayor que cero")
    private Integer montoUsd;

    @NotBlank(message = "El metodo de pago es obligatorio")
    @Pattern(
        regexp = "EFECTIVO|TARJETA_CREDITO|TARJETA_DEBITO|TRANSFERENCIA|OTRO",
        message = "El metodo de pago debe ser: EFECTIVO, TARJETA_CREDITO, TARJETA_DEBITO, TRANSFERENCIA u OTRO"
    )
    private String metodo;

    @Size(max = 80, message = "La referencia no puede superar los 80 caracteres")
    private String referencia;
}
