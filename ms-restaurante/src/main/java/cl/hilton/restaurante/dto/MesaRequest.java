package cl.hilton.restaurante.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MesaRequest {

    @NotBlank(message = "El numero de mesa es obligatorio")
    @Size(max = 10, message = "El numero de mesa no puede superar los 10 caracteres")
    private String numeroMesa;

    @NotNull(message = "La capacidad es obligatoria")
    @Min(value = 1, message = "La capacidad minima es 1")
    @Max(value = 20, message = "La capacidad maxima es 20")
    private Integer capacidad;

    @Pattern(
        regexp = "SALON|TERRAZA|PRIVADO|BARRA|ROOM_SERVICE",
        message = "La zona debe ser: SALON, TERRAZA, PRIVADO, BARRA o ROOM_SERVICE"
    )
    private String zona;

    private Boolean disponible;
}
