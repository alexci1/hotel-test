package cl.hilton.reservas.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(
    name = "disponibilidad",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"numero_habitacion", "fecha"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Disponibilidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "numero_habitacion", referencedColumnName = "numero_habitacion", nullable = false)
    private ProjHabitacion habitacion;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "disponible", nullable = false)
    private Boolean disponible;
}
