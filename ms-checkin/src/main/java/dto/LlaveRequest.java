package cl.hilton.checkin.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LlaveRequest {

    @NotBlank
    @Size(max = 10)
    private String numeroHabitacion;

    @NotBlank
    @Size(max = 40)
    private String codigoLlave;

    @NotNull
    private Boolean activa;

    @Size(max = 20)
    private String codigoReserva;

    @NotNull
    private LocalDate emitidaEn;
}