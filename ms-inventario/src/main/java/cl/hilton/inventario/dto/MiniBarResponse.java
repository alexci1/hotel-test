package cl.hilton.inventario.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MiniBarResponse {

    private Long id;
    private String numeroHabitacion;
    private String codigoProducto;
    private String nombreProducto;
    private Short cantidad;
    private BigDecimal precioUnitUsd;
}