package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "codigo_reserva", nullable = false, unique = true, length = 20)
    private String codigoReserva;

    @Column(name = "email_huesped", nullable = false, length = 120)
    private String emailHuesped;

    @Column(name = "numero_habitacion", nullable = false, length = 10)
    private String numeroHabitacion;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDate fechaHora;

    @Column(name = "realizado_por", nullable = false, length = 80)
    private String realizadoPor;

    @PrePersist
    public void prePersist() {
        if (fechaHora == null) {
            fechaHora = LocalDate.now();
        }
    }
}
