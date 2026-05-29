package cl.hilton.checkin.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjHuespedResponse {

    private String email;
    private String nombreCompleto;
    private LocalDate actualizadoEn;
}
