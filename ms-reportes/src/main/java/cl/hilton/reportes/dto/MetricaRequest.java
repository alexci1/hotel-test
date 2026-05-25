package cl.hilton.reportes.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetricaRequest {

    @NotBlank
    @Size(max = 50)
    private String codigoReporte;

    @NotNull
    private LocalDate periodo;

    @NotBlank
    @Size(max = 80)
    private String nombreMetrica;

    @NotNull
    private Integer valor;

    @Size(max = 30)
    private String unidad;

    private LocalDate calculadoEn;
}