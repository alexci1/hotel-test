package cl.hilton.reservas.dto;

import lombok.Data;

@Data
public class ProjHuespedRequest {

    private String email;

    private String nombreCompleto;

    private String telefono;
}