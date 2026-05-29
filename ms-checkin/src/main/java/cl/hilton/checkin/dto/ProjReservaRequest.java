package cl.hilton.checkin.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

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

    private LocalDate actualizadoEn;
}
