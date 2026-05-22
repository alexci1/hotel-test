package cl.hilton.autenticacion.dto;

import lombok.Data;

@Data
public class UsuarioResponse {
    private Long id;
    private String email;
    private String nombreCompleto;
    private String rol;
    private String hashPassword;
    private Boolean activo;
}
