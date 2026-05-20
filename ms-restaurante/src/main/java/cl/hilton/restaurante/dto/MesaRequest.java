package cl.hilton.restaurante.dto;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MesaRequest {

    @NotBlank
    @Size(max = 10)
    private String numeroMesa;

    @NotNull
    @Min(1)
    @Max(20)
    private Short capacidad;

    @NotBlank
    @Size(max = 40)
    private String zona;

    @NotNull
    private Boolean disponible;
}

