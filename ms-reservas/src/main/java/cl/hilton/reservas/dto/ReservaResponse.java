package cl.hilton.reservas.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservaResponse {

    private Long id;

    private String codigoReserva;

    private String emailHuesped;

    private String numeroHabitacion;

    private LocalDate fechaEntrada;

    private LocalDate fechaSalida;

    private String estado;

    private LocalDate creadoEn;
}