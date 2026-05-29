package cl.hilton.reportes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class KpiRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 80, message = "El nombre no puede superar los 80 caracteres")
    private String nombre;

    @Size(max = 255, message = "La descripcion no puede superar los 255 caracteres")
    private String descripcion;

    private Integer valorActual;

    private Integer valorObjetivo;

    @Size(max = 30, message = "La unidad no puede superar los 30 caracteres")
    private String unidad;

    @Pattern(
        regexp = "DIARIO|SEMANAL|MENSUAL|ANUAL",
        message = "El periodo debe ser: DIARIO, SEMANAL, MENSUAL o ANUAL"
    )
    private String periodo;
}
