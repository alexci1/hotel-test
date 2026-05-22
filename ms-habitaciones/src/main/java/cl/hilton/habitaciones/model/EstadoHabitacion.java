package cl.hilton.habitaciones.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "habitacion_id", nullable = false)
    private Habitacion habitacion;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Column(name = "observacion", length = 200)
    private String observacion;

    @Column(name = "actualizado_en", nullable = false)
    private LocalDate actualizadoEn;
}