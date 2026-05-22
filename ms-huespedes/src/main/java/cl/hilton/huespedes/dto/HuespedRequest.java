package cl.hilton.huespedes.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HuespedRequest {

    @NotBlank
    @Email
    @Size(max = 120)
    private String email;

    @NotBlank
    @Size(max = 100)
    private String nombreCompleto;

    @Size(max = 20)
    private String telefono;

    @NotNull
    private Boolean activo;

    @NotNull
    private LocalDate creadoEn;
}