package cl.hilton.reservas.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReservaRequest {

    @NotBlank(message = "El codigo de reserva es obligatorio")
    @Size(max = 20, message = "El codigo no puede superar los 20 caracteres")
    private String codigoReserva;

    @NotBlank(message = "El email del huesped es obligatorio")
    @Email(message = "El email del huesped debe tener formato valido")
    @Size(max = 120, message = "El email del huesped no puede superar los 120 caracteres")
    private String emailHuesped;

    @NotBlank(message = "El numero de habitacion es obligatorio")
    @Size(max = 10, message = "El numero de habitacion no puede superar los 10 caracteres")
    private String numeroHabitacion;

    @NotNull(message = "La fecha de entrada es obligatoria")
    private LocalDate fechaEntrada;

    @NotNull(message = "La fecha de salida es obligatoria")
    private LocalDate fechaSalida;

    @Pattern(
        regexp = "PENDIENTE|CONFIRMADA|CANCELADA|COMPLETADA",
        message = "El estado debe ser: PENDIENTE, CONFIRMADA, CANCELADA o COMPLETADA"
    )
    private String estado;
}
