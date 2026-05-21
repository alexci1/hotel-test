package cl.hilton.habitaciones.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tipo_habitacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TipoHabitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo", nullable = false, length = 40, unique = true)
    private String codigo;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "capacidad_max", nullable = false)
    private Short capacidadMax;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "tipoHabitacion")
    private List<Habitacion> habitaciones;
}
