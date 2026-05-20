package model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(
    name = "kpi",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_kpi_nombre",
            columnNames = "nombre"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Kpi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "valor_actual", precision = 15, scale = 4)
    private BigDecimal valorActual;

    @Column(name = "valor_objetivo", precision = 15, scale = 4)
    private BigDecimal valorObjetivo;

    @Column(name = "unidad", length = 30)
    private String unidad;

    @Column(name = "periodo", nullable = false, length = 20)
    private String periodo;

    @Column(name = "actualizado_en", nullable = false)
    private OffsetDateTime actualizadoEn;
}
