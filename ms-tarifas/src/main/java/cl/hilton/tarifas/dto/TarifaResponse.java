package cl.hilton.tarifas.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TarifaResponse {

    private Long id;

    private String codigo;

    private BigDecimal precioBaseUsd;

    private Boolean activa;

    private Boolean incluyeDesayuno;
}