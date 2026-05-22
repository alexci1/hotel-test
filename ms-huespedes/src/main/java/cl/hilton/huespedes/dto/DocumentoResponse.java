package cl.hilton.huespedes.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentoResponse {

    private Long id;
    private String emailHuesped;
    private String nombreHuesped;
    private String tipo;
    private String numero;
    private String paisEmisor;
    private LocalDate vencimiento;
}