package cl.hilton.autenticacion.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SesionResponse {
    private Long id;
    private String usuarioEmail;
    private String tokenHash;
    private String ipOrigen;
    private String userAgent;
    private LocalDate expiraEn;
    private LocalDate creadaEn;
    private Boolean invalidada;
}
