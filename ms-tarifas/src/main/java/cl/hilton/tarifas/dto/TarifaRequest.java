package cl.hilton.tarifas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TarifaRequest {

    @NotBlank(message = "El código de temporada es obligatorio")
    @Size(max = 30, message = "El código de temporada no puede superar los 30 caracteres")
    private String codigoTemporada;

    @NotBlank(message = "El tipo de habitación es obligatorio")
    @Size(max = 40, message = "El tipo de habitación no puede superar los 40 caracteres")
    private String codigoTipoHabitacion;

    @NotNull(message = "El precio por noche es obligatorio")
    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private Integer precioNocheUsd;

    @NotNull(message = "Incluye desayuno es obligatorio")
    private Boolean incluyeDesayuno;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activa;
}
