package cl.hilton.pagos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CargoRequest {

    @NotBlank(message = "El numero de factura es obligatorio")
    @Size(max = 20, message = "El numero de factura no puede superar los 20 caracteres")
    private String numeroFactura;

    @NotBlank(message = "El concepto es obligatorio")
    @Size(max = 100, message = "El concepto no puede superar los 100 caracteres")
    private String concepto;

    @NotNull(message = "El monto en USD es obligatorio")
    @Positive(message = "El monto en USD debe ser mayor que cero")
    private Integer montoUsd;

    @NotBlank(message = "El origen es obligatorio")
    @Pattern(
        regexp = "HOTEL|RESTAURANTE|MINIBAR|DANO|OTRO",
        message = "El origen debe ser: HOTEL, RESTAURANTE, MINIBAR, DANO u OTRO"
    )
    private String origen;
}
