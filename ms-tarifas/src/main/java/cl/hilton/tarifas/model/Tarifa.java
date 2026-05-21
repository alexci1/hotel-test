package cl.hilton.tarifas.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
    name = "tarifa",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"codigo_temporada", "tipo_habitacion"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "codigo_temporada", referencedColumnName = "codigo", nullable = false)
    private Temporada temporada;

    @ManyToOne
    @JoinColumn(name = "tipo_habitacion", referencedColumnName = "codigo", nullable = false)
    private ProjTipoHabitacion tipoHabitacion;

    @Column(name = "precio_noche_usd", nullable = false)
    private BigDecimal precioNocheUsd;

    @Column(name = "incluye_desayuno", nullable = false)
    private Boolean incluyeDesayuno;

    @Column(name = "activa", nullable = false)
    private Boolean activa;
}
