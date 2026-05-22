package cl.hilton.tarifas.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tarifa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false, length = 40)
    private String codigo;

    @Column(name = "precio_base_usd", nullable = false)
    private BigDecimal precioBaseUsd;

    @Column(name = "activa", nullable = false)
    private Boolean activa;

    @Column(name = "incluye_desayuno")
    private Boolean incluyeDesayuno;
}
