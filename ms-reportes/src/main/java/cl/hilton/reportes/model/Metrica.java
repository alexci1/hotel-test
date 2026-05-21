package cl.hilton.reportes.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(
    name = "metrica",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_metrica",
            columnNames = {"codigo_reporte", "periodo", "nombre_metrica"}
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Metrica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "codigo_reporte",
        referencedColumnName = "codigo",
        nullable = false
    )
    private Reporte reporte;

    @Column(name = "periodo", nullable = false)
    private LocalDate periodo;

    @Column(name = "nombre_metrica", nullable = false, length = 80)
    private String nombreMetrica;

    @Column(name = "valor", nullable = false, precision = 15, scale = 4)
    private BigDecimal valor;

    @Column(name = "unidad", length = 30)
    private String unidad;

    @Column(name = "calculado_en", nullable = false)
    private OffsetDateTime calculadoEn;
}
