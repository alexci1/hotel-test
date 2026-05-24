package cl.hilton.reservas.model;

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
    name = "disponibilidades",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_disponibilidades", columnNames = {"numero_habitacion", "fecha"})
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
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "numero_habitacion", referencedColumnName = "numero_habitacion", nullable = false)
    private ProjHabitacion habitacion;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "disponible", nullable = false)
    private Boolean disponible;
}
