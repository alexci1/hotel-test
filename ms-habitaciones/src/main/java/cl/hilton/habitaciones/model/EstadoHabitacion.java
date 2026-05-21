package cl.hilton.habitaciones.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "estado_habitacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoHabitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "numero_habitacion", referencedColumnName = "numero_habitacion", nullable = false, unique = true)
    private Habitacion habitacion;

    @Column(name = "estado", nullable = false, length = 30)
    private String estado;

    @Lob
    @Column(name = "observacion")
    private String observacion;

    @Column(name = "actualizado_en", nullable = false)
    private OffsetDateTime actualizadoEn;
}
