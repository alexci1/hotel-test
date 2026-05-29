package cl.hilton.habitaciones.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class HabitacionRequest {

    @NotBlank(message = "El numero de habitacion es obligatorio")
    @Size(max = 10, message = "El numero no puede superar los 10 caracteres")
    private String numeroHabitacion;

    @NotNull(message = "El piso es obligatorio")
    @Min(value = 0, message = "El piso no puede ser negativo")
    private Integer piso;

    @NotBlank(message = "El codigo tipo es obligatorio")
    @Size(max = 40, message = "El codigo tipo no puede superar los 40 caracteres")
    private String codigoTipo;

    private Boolean activa;
}
