package cl.hilton.inventario.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Long stockActual;
    private Long stockMinimo;
    private String unidad;
}