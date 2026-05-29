package cl.hilton.reservas.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ProjHuespedResponse {

    private String email;
    private String nombreCompleto;
    private String telefono;
    private LocalDate actualizadoEn;
}
