package cl.hilton.autenticacion.dto;

import lombok.Data;

@Data
public class RolResponse {
    private Long id;
    private String codigo;
    private String descripcion;
    private Boolean activo;
}
