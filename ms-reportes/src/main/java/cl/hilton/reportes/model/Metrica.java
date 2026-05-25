package cl.hilton.reportes.model;

import java.time.LocalDate;

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
    name = "metricas",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_metricas", columnNames = {"codigo_reporte", "periodo", "nombre_metrica"})
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
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "codigo_reporte", referencedColumnName = "codigo", nullable = false)
    private Reporte reporte;

    @Column(name = "periodo", nullable = false)
    private LocalDate periodo;

    @Column(name = "nombre_metrica", nullable = false, length = 80)
    private String nombreMetrica;

    @Column(name = "valor", nullable = false)
    private Integer valor;

    @Column(name = "unidad", length = 30)
    private String unidad;

    @Column(name = "calculado_en", nullable = false)
    private LocalDate calculadoEn;
}