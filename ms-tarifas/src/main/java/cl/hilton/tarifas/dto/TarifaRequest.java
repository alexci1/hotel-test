package cl.hilton.tarifas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TarifaRequest {

    @NotBlank(message = "El codigo de temporada es obligatorio")
    @Size(max = 30, message = "El codigo de temporada no puede superar los 30 caracteres")
    private String codigoTemporada;

    @NotBlank(message = "El tipo de habitacion es obligatorio")
    @Size(max = 40, message = "El tipo de habitacion no puede superar los 40 caracteres")
    private String codigoTipoHabitacion;

    @NotNull(message = "El precio por noche es obligatorio")
    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private Integer precioNocheUsd;

    private Boolean incluyeDesayuno;

    private Boolean activa;
}
