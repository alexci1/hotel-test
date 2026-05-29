package cl.hilton.checkin.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjHuespedResponse {

    private String email;
    private String nombreCompleto;
}
