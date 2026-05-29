package cl.hilton.housekeeping.dto;

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
public class TareaRequest {

    @NotBlank
    @Size(max = 30)
    private String codigo;

    @Size(max = 255)
    private String descripcion;

    @NotNull
    @Min(1)
    private Integer duracionMin;

    private Boolean activa;
}