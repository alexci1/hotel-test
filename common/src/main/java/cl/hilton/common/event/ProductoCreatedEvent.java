package cl.hilton.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoCreatedEvent implements ProductoEvent {

    private Long id;
    private String codigoProducto;
    private String nombre;
    private String categoria;
    private Integer stockActual;
    private Integer stockMinimo;
    private String unidad;
}
