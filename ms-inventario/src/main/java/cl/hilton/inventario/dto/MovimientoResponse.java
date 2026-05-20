package cl.hilton.inventario.dto;


import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoResponse {

    private Integer id;
    private String codigoProducto;
    private String nombreProducto;
    private String tipo;
    private Integer cantidad;
    private String motivo;
    private String registradoPor;
    private OffsetDateTime registradoEn;
}
