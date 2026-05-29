package cl.hilton.housekeeping.dto;

import jakarta.validation.constraints.NotNull;
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
public class ReporteRequest {

    @NotNull
    private Long asignacionId;

    private Boolean aprobado;

    @Size(max = 255)
    private String observaciones;

    @Size(max = 120)
    private String inspector;
}