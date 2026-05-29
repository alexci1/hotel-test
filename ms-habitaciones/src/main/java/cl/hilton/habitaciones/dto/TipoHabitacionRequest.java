package cl.hilton.habitaciones.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TipoHabitacionRequest {

    @NotBlank(message = "El codigo es obligatorio")
    @Size(max = 40, message = "El codigo no puede superar los 40 caracteres")
    private String codigo;

    @Size(max = 200, message = "La descripcion no puede superar los 200 caracteres")
    private String descripcion;

    @Min(value = 1, message = "La capacidad minima es 1")
    @Max(value = 10, message = "La capacidad maxima es 10")
    private Integer capacidadMax;

    private Boolean activo;
}
