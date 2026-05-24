package cl.hilton.habitaciones.model;

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
    name = "estados_habitacion",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_estados_habitacion_numero_habitacion", columnNames = "numero_habitacion")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoHabitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "numero_habitacion", referencedColumnName = "numero_habitacion", nullable = false, unique = true)
    private Habitacion habitacion;

    @Column(name = "estado", nullable = false, length = 30)
    private String estado;

    @Column(name = "observacion", length = 200)
    private String observacion;

    @Column(name = "actualizado_en", nullable = false)
    private LocalDate actualizadoEn;
}
