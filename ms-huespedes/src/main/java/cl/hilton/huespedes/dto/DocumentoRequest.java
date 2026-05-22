package cl.hilton.huespedes.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentoRequest {

    @NotBlank
    @Email
    @Size(max = 120)
    private String emailHuesped;

    @NotBlank
    @Size(max = 20)
    private String tipo;

    @NotBlank
    @Size(max = 40)
    private String numero;

    @NotBlank
    @Size(min = 2, max = 2)
    private String paisEmisor;

    private LocalDate vencimiento;
}