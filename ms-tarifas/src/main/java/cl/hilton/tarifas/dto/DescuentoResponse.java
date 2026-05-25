package cl.hilton.tarifas.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DescuentoResponse {

    private Long id;

    private String codigoDescuento;

    private String descripcion;

    private Integer porcentaje;

    private String aplicaA;

    private LocalDate validoDesde;

    private LocalDate validoHasta;

    private Boolean activo;
}
