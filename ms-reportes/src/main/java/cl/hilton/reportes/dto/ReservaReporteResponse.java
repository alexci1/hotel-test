package cl.hilton.reportes.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReservaReporteResponse {

    private Long id;
    private String codigoReserva;
    private String emailHuesped;
    private String numeroHabitacion;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private String estado;
    private LocalDate creadoEn;
}
