package cl.hilton.reservas.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CancelacionResponse {

    private Long id;

    private String codigoReserva;

    private String motivo;

    private String canceladoPor;

    private LocalDate canceladoEn;

    private BigDecimal penalidadUsd;
}