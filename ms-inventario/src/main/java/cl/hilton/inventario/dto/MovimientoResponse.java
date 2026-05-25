package cl.hilton.inventario.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoResponse {

    private Long id;
    private String codigoProducto;
    private String nombreProducto;
    private String tipo;
    private Integer cantidad;
    private String motivo;
    private String registradoPor;
    private LocalDate registradoEn;
}