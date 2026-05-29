package cl.hilton.reportes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReporteRequest {

    @NotBlank(message = "El codigo es obligatorio")
    @Size(max = 50, message = "El codigo no puede superar los 50 caracteres")
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 120, message = "El nombre no puede superar los 120 caracteres")
    private String nombre;

    @Size(max = 255, message = "La descripcion no puede superar los 255 caracteres")
    private String descripcion;

    @NotBlank(message = "El tipo es obligatorio")
    @Pattern(
        regexp = "OPERACIONAL|FINANCIERO|HOUSEKEEPING|RESTAURANTE|EJECUTIVO",
        message = "El tipo debe ser: OPERACIONAL, FINANCIERO, HOUSEKEEPING, RESTAURANTE o EJECUTIVO"
    )
    private String tipo;

    @Pattern(
        regexp = "TIEMPO_REAL|DIARIO|SEMANAL|MENSUAL|ANUAL",
        message = "La frecuencia debe ser: TIEMPO_REAL, DIARIO, SEMANAL, MENSUAL o ANUAL"
    )
    private String frecuencia;

    private Boolean activo;
}
