package cl.hilton.reservas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DisponibilidadRequest {

    @NotBlank(message = "El número de habitación es obligatorio")
    private String numeroHabitacion;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "Debe indicar disponibilidad")
    private Boolean disponible;
}