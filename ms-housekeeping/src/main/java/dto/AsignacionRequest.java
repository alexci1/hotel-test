package cl.hilton.housekeeping.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsignacionRequest {

    @NotBlank
    @Size(max = 10)
    private String numeroHabitacion;

    @NotBlank
    @Size(max = 30)
    private String codigoTarea;

    @NotBlank
    @Email
    @Size(max = 120)
    private String emailCamarero;

    @NotNull
    private LocalDate fechaProgramada;

    @NotBlank
    @Size(max = 20)
    private String estado;

    @NotNull
    @Min(1)
    @Max(5)
    private Long prioridad;

    private LocalDate iniciadaEn;

    private LocalDate completadaEn;
}