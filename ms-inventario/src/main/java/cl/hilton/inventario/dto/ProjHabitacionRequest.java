package cl.hilton.inventario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProjHabitacionRequest {

    @NotBlank(message = "El numero de habitacion es obligatorio")
    @Size(max = 10, message = "El numero de habitacion no puede superar los 10 caracteres")
    private String numeroHabitacion;

    @NotBlank(message = "El tipo es obligatorio")
    @Size(max = 40, message = "El tipo no puede superar los 40 caracteres")
    private String tipo;
}
