package cl.hilton.tarifas.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DescuentoRequest {

    @NotBlank(message = "El codigo de descuento es obligatorio")
    @Size(max = 30, message = "El codigo no puede superar los 30 caracteres")
    private String codigoDescuento;

    @Size(max = 100, message = "La descripcion no puede superar los 100 caracteres")
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

    private Boolean activo;
}
