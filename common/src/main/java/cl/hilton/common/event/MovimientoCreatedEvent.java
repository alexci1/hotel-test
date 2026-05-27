package cl.hilton.common.event;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoCreatedEvent implements MovimientoEvent {

    private Long id;
    private String codigoProducto;
    private String nombreProducto;
    private String tipo;
    private Integer cantidad;
    private String motivo;
    private String registradoPor;
    private LocalDate registradoEn;
}
