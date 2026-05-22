package cl.hilton.housekeeping.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "asignacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asignacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(
        name = "numero_habitacion",
        referencedColumnName = "numero_habitacion",
        nullable = false
    )
    private ProjHabitacion habitacion;

    @ManyToOne
    @JoinColumn(
        name = "codigo_tarea",
        referencedColumnName = "codigo",
        nullable = false
    )
    private Tarea tarea;

    @Column(name = "email_camarero", nullable = false, length = 120)
    private String emailCamarero;

    @Column(name = "fecha_programada", nullable = false)
    private LocalDate fechaProgramada;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "prioridad", nullable = false)
    private Long prioridad;

    @Column(name = "iniciada_en")
    private LocalDate iniciadaEn;

    @Column(name = "completada_en")
    private LocalDate completadaEn;

    @OneToOne(mappedBy = "asignacion")
    private Reporte reporte;
}