package cl.hilton.autenticacion.dto;

import java.time.LocalDate;
import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SesionResponse extends RepresentationModel<SesionResponse> {
    private Long id;
    private String usuarioEmail;
    private String tokenHash;
    private String ipOrigen;
    private String userAgent;
    private LocalDate expiraEn;
    private LocalDate creadaEn;
    private Boolean invalidada;
}