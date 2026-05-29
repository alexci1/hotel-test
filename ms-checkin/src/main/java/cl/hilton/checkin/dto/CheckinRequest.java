package cl.hilton.checkin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    @Size(max = 80)
    private String realizadoPor;
}