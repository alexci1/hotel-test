package cl.hilton.checkin.dto;

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
public class CheckoutRequest {

    @NotBlank
    @Size(max = 20)
    private String codigoReserva;

    @NotBlank
    @Size(max = 80)
    private String realizadoPor;

    @Size(max = 255)
    private String observaciones;
}