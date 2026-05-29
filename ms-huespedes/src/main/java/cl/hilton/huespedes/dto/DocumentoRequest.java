package cl.hilton.huespedes.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DocumentoRequest {

    @NotBlank(message = "El email del huesped es obligatorio")
    @Email(message = "El email del huesped debe tener formato valido")
    @Size(max = 120, message = "El email del huesped no puede superar los 120 caracteres")
    private String emailHuesped;

    @NotBlank(message = "El tipo de documento es obligatorio")
    @Pattern(
        regexp = "PASAPORTE|DNI|RUT|CEDULA|OTRO",
        message = "El tipo debe ser: PASAPORTE, DNI, RUT, CEDULA u OTRO"
    )
    private String tipo;

    @NotBlank(message = "El numero de documento es obligatorio")
    @Size(max = 40, message = "El numero no puede superar los 40 caracteres")
    private String numero;

    @NotBlank(message = "El pais emisor es obligatorio")
    @Size(min = 2, max = 2, message = "El pais emisor debe tener 2 caracteres")
    private String paisEmisor;

    private LocalDate vencimiento;
}
