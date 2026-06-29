package cl.hilton.inventario.dto;

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
public class MovimientoResponse extends RepresentationModel<MovimientoResponse> {
    private Long id;
    private String codigoProducto;
    private String nombreProducto;
    private String tipo;
    private Integer cantidad;
    private String motivo;
    private String registradoPor;
    private LocalDate registradoEn;
}