package cl.hilton.pagos.dto;

import java.time.LocalDate;
import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PagoResponse extends RepresentationModel<PagoResponse> {
    private Long id;
    private String numeroFactura;
    private Integer montoUsd;
    private String metodo;
    private String referencia;
    private LocalDate pagadoEn;
}