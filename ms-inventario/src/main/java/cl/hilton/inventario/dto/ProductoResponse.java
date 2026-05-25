package cl.hilton.inventario.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoResponse {

    private Long id;
    private String codigoProducto;
    private String nombre;
    private String categoria;
    private Integer stockActual;
    private Integer stockMinimo;
    private String unidad;
}