package cl.hilton.tarifas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DescuentoRequest {

    @NotBlank(message = "El código de descuento es obligatorio")
    @Size(max = 30, message = "El código no puede superar los 30 caracteres")
    private String codigoDescuento;

    @Size(max = 100, message = "La descripción no puede superar los 100 caracteres")
    private String descripcion;

    @NotNull(message = "El porcentaje es obligatorio")
    @Min(value = 1, message = "El porcentaje debe ser mayor a 0")
    @Max(value = 100, message = "El porcentaje no puede superar 100")
    private Integer porcentaje;

    @Size(max = 40, message = "Aplica a no puede superar los 40 caracteres")
    private String aplicaA;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate validoDesde;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate validoHasta;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;
}
