package dto;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteRequest {

    @NotBlank
    @Size(max = 50)
    private String codigo;

    @NotBlank
    @Size(max = 120)
    private String nombre;

    private String descripcion;

    @NotBlank
    @Size(max = 30)
    private String tipo;

    @NotBlank
    @Size(max = 20)
    private String frecuencia;

    @NotNull
    private Boolean activo;
}
