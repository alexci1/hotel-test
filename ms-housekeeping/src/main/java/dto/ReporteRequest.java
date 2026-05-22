package cl.hilton.housekeeping.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteRequest {

    @NotNull
    private Long asignacionId;

    @NotNull
    private Boolean aprobado;

    @Size(max = 255)
    private String observaciones;

    @NotBlank
    @Email
    @Size(max = 120)
    private String inspector;

    @NotNull
    private LocalDate inspeccionadoEn;
}