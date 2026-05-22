package cl.hilton.habitaciones.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class HabitacionRequest {

    @NotBlank(message = "El número de habitación es obligatorio")
    @Size(max = 10, message = "El número no puede superar los 10 caracteres")
    private String numeroHabitacion;

    @NotNull(message = "El piso es obligatorio")
    @Min(value = 0, message = "El piso no puede ser negativo")
    private Integer piso;

    @NotBlank(message = "El código tipo es obligatorio")
    @Size(max = 40, message = "El código tipo no puede superar los 40 caracteres")
    private String codigoTipo;

    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activa;
}