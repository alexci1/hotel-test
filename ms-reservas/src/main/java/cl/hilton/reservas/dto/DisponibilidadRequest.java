package cl.hilton.reservas.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DisponibilidadRequest {

    @NotBlank(message = "El numero de habitacion es obligatorio")
    @Size(max = 10, message = "El numero de habitacion no puede superar los 10 caracteres")
    private String numeroHabitacion;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    private Boolean disponible;
}
