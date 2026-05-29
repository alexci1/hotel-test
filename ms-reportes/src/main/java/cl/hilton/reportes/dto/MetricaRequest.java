package cl.hilton.reportes.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MetricaRequest {

    @NotBlank(message = "El codigo del reporte es obligatorio")
    @Size(max = 50, message = "El codigo del reporte no puede superar los 50 caracteres")
    private String codigoReporte;

    @NotNull(message = "El periodo es obligatorio")
    private LocalDate periodo;

    @NotBlank(message = "El nombre de la metrica es obligatorio")
    @Size(max = 80, message = "El nombre de la metrica no puede superar los 80 caracteres")
    private String nombreMetrica;

    @NotNull(message = "El valor es obligatorio")
    private Integer valor;

    @Size(max = 30, message = "La unidad no puede superar los 30 caracteres")
    private String unidad;
}
