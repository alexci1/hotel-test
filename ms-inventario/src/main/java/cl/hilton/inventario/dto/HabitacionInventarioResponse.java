package cl.hilton.inventario.dto;

import lombok.Data;

@Data
public class HabitacionInventarioResponse {

    private Long id;
    private String numeroHabitacion;
    private Integer piso;
    private String codigoTipo;
    private Boolean activa;
}
