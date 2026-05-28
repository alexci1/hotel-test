package cl.hilton.pagos.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PagoResponse {

    private Long id;
    private String numeroFactura;
    private Integer montoUsd;
    private String metodo;
    private String referencia;
    private LocalDate pagadoEn;
}
