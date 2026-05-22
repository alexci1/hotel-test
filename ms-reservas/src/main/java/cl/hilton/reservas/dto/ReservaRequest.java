package cl.hilton.reservas.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservaRequest {

    @NotBlank(message = "El código de reserva es obligatorio")
    @Size(max = 20, message = "El código no puede superar los 20 caracteres")
    private String codigoReserva;

    @NotBlank(message = "El email del huésped es obligatorio")
    @Email(message = "Email inválido")
    private String emailHuesped;

    @NotBlank(message = "El número de habitación es obligatorio")
    @Size(max = 10, message = "Máximo 10 caracteres")
    private String numeroHabitacion;

    @NotNull(message = "La fecha de entrada es obligatoria")
    private LocalDate fechaEntrada;

    @NotNull(message = "La fecha de salida es obligatoria")
    private LocalDate fechaSalida;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;
}