package cl.hilton.restaurante.dto;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjHuespedRequest {

    @NotBlank
    @Email
    @Size(max = 120)
    private String email;

    @NotBlank
    @Size(max = 100)
    private String nombreCompleto;

    @Size(max = 10)
    private String numeroHabitacion;

    private OffsetDateTime actualizadoEn;
}
