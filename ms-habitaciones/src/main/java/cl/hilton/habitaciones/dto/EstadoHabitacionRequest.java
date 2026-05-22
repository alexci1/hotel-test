package cl.hilton.habitaciones.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EstadoHabitacionRequest {

    @NotBlank(message = "El número de habitación es obligatorio")
    @Size(max = 10, message = "El número no puede superar los 10 caracteres")
    private String numeroHabitacion;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(
            regexp = "LIMPIA|SUCIA|EN_MANTENIMIENTO|OCUPADA|BLOQUEADA",
            message = "Estado inválido"
    )
    private String estado;

    @Size(max = 200, message = "La observación no puede superar los 200 caracteres")
    private String observacion;
}