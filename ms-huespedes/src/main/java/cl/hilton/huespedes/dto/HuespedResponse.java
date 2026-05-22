package cl.hilton.huespedes.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HuespedResponse {

    private Long id;
    private String email;
    private String nombreCompleto;
    private String telefono;
    private Boolean activo;
    private LocalDate creadoEn;
}