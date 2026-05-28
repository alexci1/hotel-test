package cl.hilton.pagos.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ProjReservaResponse {

    private String codigoReserva;
    private String emailHuesped;
    private String numeroHabitacion;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private LocalDate actualizadoEn;
}
