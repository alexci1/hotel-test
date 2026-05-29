package cl.hilton.tarifas.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProjTipoHabitacionRequest {

    @NotBlank(message = "El codigo es obligatorio")
    @Size(max = 40, message = "El codigo no puede superar los 40 caracteres")
    private String codigo;

    @Size(max = 100, message = "La descripcion no puede superar los 100 caracteres")
    private String descripcion;

    @NotNull(message = "La capacidad maxima es obligatoria")
    @Min(value = 1, message = "La capacidad minima es 1")
    @Max(value = 20, message = "La capacidad maxima es 20")
    private Integer capacidadMax;
}
