package cl.hilton.tarifas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "tarifas",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_tarifas", columnNames = {"codigo_temporada", "tipo_habitacion"})
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
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "codigo_temporada", referencedColumnName = "codigo", nullable = false)
    private Temporada temporada;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipo_habitacion", referencedColumnName = "codigo", nullable = false)
    private ProjTipoHabitacion tipoHabitacion;

    @Column(name = "precio_noche_usd", nullable = false)
    private Integer precioNocheUsd;

    @Column(name = "incluye_desayuno", nullable = false)
    private Boolean incluyeDesayuno;

    @Column(name = "activa", nullable = false)
    private Boolean activa;
}
