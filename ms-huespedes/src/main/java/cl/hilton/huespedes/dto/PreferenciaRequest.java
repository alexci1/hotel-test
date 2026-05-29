package cl.hilton.huespedes.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PreferenciaRequest {

    @NotBlank(message = "El email del huesped es obligatorio")
    @Email(message = "El email del huesped debe tener formato valido")
    @Size(max = 120, message = "El email del huesped no puede superar los 120 caracteres")
    private String emailHuesped;

    private Integer pisoPreferido;

    @Pattern(
        regexp = "MATRIMONIAL|TWIN|KING|QUEEN",
        message = "El tipo de cama debe ser: MATRIMONIAL, TWIN, KING o QUEEN"
    )
    private String tipoCama;

    @Size(max = 255, message = "Las alergias no pueden superar los 255 caracteres")
    private String alergias;

    @Size(max = 255, message = "Las observaciones no pueden superar los 255 caracteres")
    private String observaciones;
}
