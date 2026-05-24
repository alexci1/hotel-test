package cl.hilton.housekeeping.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TareaRequest {

    @NotBlank
    @Size(max = 30)
    private String codigo;

    @Size(max = 255)
    private String descripcion;

    @NotNull
    @Min(1)
    private Long duracionMin;

    @NotNull
    private Boolean activa;
}