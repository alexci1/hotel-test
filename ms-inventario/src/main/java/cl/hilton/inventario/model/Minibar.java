package cl.hilton.inventario.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
    name = "minibar",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_minibar",
            columnNames = {"numero_habitacion", "codigo_producto"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Minibar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "numero_habitacion",
        referencedColumnName = "numero_habitacion",
        nullable = false
    )
    private ProjHabitacion habitacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "codigo_producto",
        referencedColumnName = "codigo_producto",
        nullable = false
    )
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    private Short cantidad;

    @Column(name = "precio_unit_usd", nullable = false, precision = 8, scale = 2)
    private BigDecimal precioUnitUsd;
}