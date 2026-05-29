package cl.hilton.habitaciones.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EstadoHabitacionRequest {

    @Size(max = 10, message = "El numero no puede superar los 10 caracteres")
    private String numeroHabitacion;

    @Pattern(
            regexp = "LIMPIA|SUCIA|EN_MANTENIMIENTO|OCUPADA|BLOQUEADA",
            message = "Estado invalido"
    )
    private String estado;

    @Size(max = 200, message = "La observacion no puede superar los 200 caracteres")
    private String observacion;
}
