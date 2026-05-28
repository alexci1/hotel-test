package cl.hilton.pagos.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CargoResponse {

    private Long id;
    private String numeroFactura;
    private String concepto;
    private Integer montoUsd;
    private String origen;
    private LocalDate registradoEn;
}
