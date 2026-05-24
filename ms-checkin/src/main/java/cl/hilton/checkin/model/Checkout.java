package cl.hilton.checkin.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
    name = "checkout",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_checkout_codigo_reserva",
            columnNames = "codigo_reserva"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Checkout {

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

    @Column(name = "fecha_hora", nullable = false)
    private LocalDate fechaHora;

    @Column(name = "realizado_por", nullable = false, length = 80)
    private String realizadoPor;

    @Column(name = "observaciones", length = 255)
    private String observaciones;
}