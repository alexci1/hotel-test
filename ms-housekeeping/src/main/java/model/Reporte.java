package cl.hilton.housekeeping.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "reportes",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_reportes_asignacion_id", columnNames = "asignacion_id")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "asignacion_id", referencedColumnName = "id", nullable = false, unique = true)
    private Asignacion asignacion;

    @Column(name = "aprobado", nullable = false)
    private Boolean aprobado;

    @Column(name = "observaciones", length = 255)
    private String observaciones;

    @Column(name = "inspector", nullable = false, length = 120)
    private String inspector;

    @Column(name = "inspeccionado_en", nullable = false)
    private LocalDate inspeccionadoEn;
}
