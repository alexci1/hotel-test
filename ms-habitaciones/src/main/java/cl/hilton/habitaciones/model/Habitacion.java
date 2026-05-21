package cl.hilton.habitaciones.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "habitacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "numero_habitacion", nullable = false, length = 10, unique = true)
    private String numeroHabitacion;

    @Column(name = "piso", nullable = false)
    private Short piso;

    @ManyToOne
    @JoinColumn(name = "codigo_tipo", referencedColumnName = "codigo", nullable = false)
    private TipoHabitacion tipoHabitacion;

    @Column(name = "activa", nullable = false)
    private Boolean activa;

    @OneToOne(mappedBy = "habitacion")
    private EstadoHabitacion estadoHabitacion;
}
