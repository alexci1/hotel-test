package cl.hilton.pagos.dto;

import java.time.LocalDate;
import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProjHuespedResponse extends RepresentationModel<ProjHuespedResponse> {
    private String email;
    private String nombreCompleto;
    private LocalDate actualizadoEn;
}