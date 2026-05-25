package cl.hilton.inventario.dto;

import lombok.*;

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
    private Integer cantidad;
    private Integer precioUnitUsd;
}