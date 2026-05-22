package cl.hilton.huespedes.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreferenciaRequest {

    @NotBlank
    @Email
    @Size(max = 120)
    private String emailHuesped;

    private Long pisoPreferido;

    @Size(max = 30)
    private String tipoCama;

    @Size(max = 255)
    private String alergias;

    @Size(max = 255)
    private String observaciones;
}