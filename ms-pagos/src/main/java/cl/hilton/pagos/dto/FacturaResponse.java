package cl.hilton.pagos.dto;

import java.time.LocalDate;
import java.util.List;
import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FacturaResponse extends RepresentationModel<FacturaResponse> {
    private Long id;
    private String numeroFactura;
    private String codigoReserva;
    private String emailHuesped;
    private Integer totalUsd;
    private String estado;
    private LocalDate emitidaEn;
    private List<PagoResponse> pagos;
    private List<CargoResponse> cargos;
}