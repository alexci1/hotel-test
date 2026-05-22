package cl.hilton.habitaciones.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TipoHabitacionRequest {

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 40, message = "El código no puede superar los 40 caracteres")
    private String codigo;

    @Size(max = 200, message = "La descripción no puede superar los 200 caracteres")
    private String descripcion;

    @NotNull(message = "La capacidad máxima es obligatoria")
    @Min(value = 1, message = "La capacidad mínima es 1")
    @Max(value = 10, message = "La capacidad máxima es 10")
    private Integer capacidadMax;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;
}