package cl.hilton.huespedes.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreferenciaResponse {

    private Long id;
    private String emailHuesped;
    private String nombreHuesped;
    private Long pisoPreferido;
    private String tipoCama;
    private String alergias;
    private String observaciones;
}