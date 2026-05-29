package cl.hilton.huespedes.dto;

import lombok.Data;

@Data
public class PreferenciaResponse {

    private Long id;
    private String emailHuesped;
    private String nombreHuesped;
    private Integer pisoPreferido;
    private String tipoCama;
    private String alergias;
    private String observaciones;
}
