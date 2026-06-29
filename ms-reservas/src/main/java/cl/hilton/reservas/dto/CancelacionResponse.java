package cl.hilton.reservas.dto;

import java.time.LocalDate;
import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CancelacionResponse extends RepresentationModel<CancelacionResponse> {
    private Long id;
    private String codigoReserva;
    private String motivo;
    private String canceladoPor;
    private LocalDate canceladoEn;
    private Integer penalidadUsd;
}