package cl.hilton.inventario.dto;


import lombok.Data;
@Data
public class ProductoResponse {
   private Integer id;
    private String codigoProducto;
    private String nombre;
    private String categoria;
    private Integer stockActual;
    private Integer stockMinimo;
    private String unidad;
}
