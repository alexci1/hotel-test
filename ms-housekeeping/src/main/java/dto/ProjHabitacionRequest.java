package cl.hilton.housekeeping.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjHabitacionRequest {

    @NotBlank
    @Size(max = 10)
    private String numeroHabitacion;

    @NotBlank
    @Size(max = 40)
    private String tipo;

    @NotNull
    @Min(1)
    private Long piso;

    @NotNull
    private LocalDate actualizadoEn;
}