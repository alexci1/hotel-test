package cl.hilton.reservas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProjHabitacionRequest {

    @NotBlank(message = "El número de habitación es obligatorio")
    @Size(max = 20, message = "El número de habitación no puede superar los 20 caracteres")
    private String numeroHabitacion;

    @NotBlank(message = "El tipo de habitación es obligatorio")
    @Size(max = 40, message = "El tipo de habitación no puede superar los 40 caracteres")
    private String tipoHabitacion;

    @NotNull(message = "La capacidad máxima es obligatoria")
    @Min(value = 1, message = "La capacidad mínima es 1")
    @Max(value = 10, message = "La capacidad máxima es 10")
    private Integer capacidadMax;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activa;
}