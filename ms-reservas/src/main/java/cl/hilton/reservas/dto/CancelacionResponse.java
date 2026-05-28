package cl.hilton.reservas.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CancelacionResponse {
    
    private Long id;
    private String codigoReserva;
    private String motivo;
    private String canceladoPor;
    private LocalDate canceladoEn;
    private Integer penalidadUsd;
}
