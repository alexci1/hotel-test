package cl.hilton.checkin.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(
    name = "checkins",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_checkins_codigo_reserva", columnNames = "codigo_reserva")
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
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "codigo_reserva",
            referencedColumnName = "codigo_reserva",
            nullable = false,
            unique = true
    )
    private ProjReserva reserva;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
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