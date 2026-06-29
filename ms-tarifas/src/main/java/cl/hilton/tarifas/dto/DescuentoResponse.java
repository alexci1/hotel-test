package cl.hilton.tarifas.dto;

import java.time.LocalDate;
import org.springframework.hateoas.RepresentationModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DescuentoResponse extends RepresentationModel<DescuentoResponse> {
    private Long id;
    private String codigoDescuento;
    private String descripcion;
    private Integer porcentaje;
    private String aplicaA;
    private LocalDate validoDesde;
    private LocalDate validoHasta;
    private Boolean activo;
}