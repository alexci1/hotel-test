package cl.hilton.reportes.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KpiRequest {

    @NotBlank
    @Size(max = 80)
    private String nombre;

    @Size(max = 255)
    private String descripcion;

    private Integer valorActual;

    private Integer valorObjetivo;

    @Size(max = 30)
    private String unidad;

    @NotBlank
    @Size(max = 20)
    private String periodo;

    private LocalDate actualizadoEn;
}