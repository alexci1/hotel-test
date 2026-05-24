package cl.hilton.checkin.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckinRequest {

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
    private LocalDate fechaHora;

    @NotBlank
    @Size(max = 80)
    private String realizadoPor;
}