package cl.hilton.habitaciones.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProjTarifaRequest {

    @NotBlank(message = "El tipo de habitacion es obligatorio")
    @Size(max = 40, message = "El tipo de habitacion no puede superar los 40 caracteres")
    private String tipoHabitacion;

    @NotNull(message = "El precio base es obligatorio")
    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private Integer precioBaseUsd;
}
