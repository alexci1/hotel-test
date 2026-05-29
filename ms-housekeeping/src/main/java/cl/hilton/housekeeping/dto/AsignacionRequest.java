package cl.hilton.housekeeping.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

    @Size(max = 20)
    private String estado;

    @Min(1)
    @Max(5)
    private Integer prioridad;

    private LocalDate iniciadaEn;

    private LocalDate completadaEn;
}