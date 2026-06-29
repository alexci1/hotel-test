package cl.hilton.pagos.dto;

import java.time.LocalDate;
import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CargoResponse extends RepresentationModel<CargoResponse> {
    private Long id;
    private String numeroFactura;
    private String concepto;
    private Integer montoUsd;
    private String origen;
    private LocalDate registradoEn;
}