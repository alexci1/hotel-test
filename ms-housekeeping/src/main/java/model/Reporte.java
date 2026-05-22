package cl.hilton.housekeeping.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "reporte")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(
        name = "asignacion_id",
        referencedColumnName = "id",
        nullable = false,
        unique = true
    )
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