package cl.hilton.huespedes.dto;

import java.time.LocalDate;
import org.springframework.hateoas.RepresentationModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class DocumentoResponse extends RepresentationModel<DocumentoResponse> {
    private Long id;
    private String emailHuesped;
    private String nombreHuesped;
    private String tipo;
    private String numero;
    private String paisEmisor;
    private LocalDate vencimiento;
}