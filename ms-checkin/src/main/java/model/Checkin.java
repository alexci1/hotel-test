package cl.hilton.checkin.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
    name = "checkin",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_checkin_codigo_reserva",
            columnNames = "codigo_reserva"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Checkin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(
        name = "codigo_reserva",
        referencedColumnName = "codigo_reserva",
        nullable = false
    )
    private ProjReserva reserva;

    @ManyToOne
    @JoinColumn(
        name = "email_huesped",
        referencedColumnName = "email",
        nullable = false
    )
    private ProjHuesped huesped;

    @Column(name = "numero_habitacion", nullable = false, length = 10)
    private String numeroHabitacion;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDate fechaHora;

    @Column(name = "realizado_por", nullable = false, length = 80)
    private String realizadoPor;
}