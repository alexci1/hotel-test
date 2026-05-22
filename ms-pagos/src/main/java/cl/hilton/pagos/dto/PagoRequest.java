package cl.hilton.pagos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PagoRequest {

    @NotBlank(message = "El numero de factura es obligatorio")
    private String numeroFactura;

    @NotNull(message = "El monto en USD es obligatorio")
    private Integer montoUsd;

    @NotBlank(message = "El metodo de pago es obligatorio")
    private String metodo;

    private String referencia;
}