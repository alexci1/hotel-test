package cl.hilton.habitaciones.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "habitaciones",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_habitaciones_numero_habitacion", columnNames = "numero_habitacion")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "numero_habitacion", nullable = false, length = 10, unique = true)
    private String numeroHabitacion;

    @Column(name = "piso", nullable = false)
    private Integer piso;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "codigo_tipo", referencedColumnName = "codigo", nullable = false)
    private TipoHabitacion tipoHabitacion;

    @Column(name = "activa", nullable = false)
    private Boolean activa;

    @OneToMany(mappedBy = "habitacion")
    @Builder.Default
    private List<EstadoHabitacion> estadosHabitacion = new ArrayList<>();
}
