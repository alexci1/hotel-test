package cl.hilton.pagos.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class FacturaResponse {

    private Long id;
    private String numeroFactura;
    private String codigoReserva;
    private String emailHuesped;
    private Integer totalUsd;
    private String estado;
    private LocalDate emitidaEn;
}
