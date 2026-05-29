package cl.hilton.checkin.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjReservaRequest {

    @NotBlank
    @Size(max = 20)
    private String codigoReserva;

    @NotBlank
    @Email
    @Size(max = 120)
    private String emailHuesped;

    @NotBlank
    @Size(max = 10)
    private String numeroHabitacion;

    @NotNull
    private LocalDate fechaEntrada;

    @NotNull
    private LocalDate fechaSalida;

    @NotBlank
    @Size(max = 20)
    private String estado;
}