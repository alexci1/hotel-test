package cl.hilton.tarifas.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DescuentoResponse {

    private Long id;

    private String codigoDescuento;

    private String descripcion;

    private BigDecimal porcentaje;

    private Boolean activo;
}