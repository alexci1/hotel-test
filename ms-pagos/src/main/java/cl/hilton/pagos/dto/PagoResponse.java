package cl.hilton.pagos.dto;

import lombok.Data;

@Data
public class PagoResponse {

    private Long id;
    private String numeroFactura;
    private Integer montoUsd;
    private String metodo;
    private String referencia;
    private String pagadoEn;
}