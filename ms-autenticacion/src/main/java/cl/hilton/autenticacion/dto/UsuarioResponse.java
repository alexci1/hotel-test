package cl.hilton.autenticacion.dto;

import cl.hilton.autenticacion.model.Rol;
import lombok.Data;

@Data
public class UsuarioResponse {
    private Long id;
    private String email;
    private String nombreCompleto;
    private Rol rol;
    private String hashPassword;
    private Boolean activo;
}
