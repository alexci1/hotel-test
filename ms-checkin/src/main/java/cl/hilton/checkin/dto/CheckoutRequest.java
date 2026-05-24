package cl.hilton.checkin.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutRequest {

    @NotBlank
    @Size(max = 20)
    private String codigoReserva;

    @NotNull
    private LocalDate fechaHora;

    @NotBlank
    @Size(max = 80)
    private String realizadoPor;

    @Size(max = 255)
    private String observaciones;
}