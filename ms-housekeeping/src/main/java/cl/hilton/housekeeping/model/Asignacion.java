package cl.hilton.housekeeping.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "asignaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asignacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "numero_habitacion", referencedColumnName = "numero_habitacion", nullable = false)
    private ProjHabitacion habitacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "codigo_tarea", referencedColumnName = "codigo", nullable = false)
    private Tarea tarea;

    @Column(name = "email_camarero", nullable = false, length = 120)
    private String emailCamarero;

    @Column(name = "fecha_programada", nullable = false)
    private LocalDate fechaProgramada;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "prioridad", nullable = false)
    private Integer prioridad;

    @Column(name = "iniciada_en")
    private LocalDate iniciadaEn;

    @Column(name = "completada_en")
    private LocalDate completadaEn;

    @OneToOne(mappedBy = "asignacion")
    private Reporte reporte;
}
