package cl.hilton.reservas.model;

import jakarta.persistence.*;
import lombok.*;


import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "proj_habitacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjHabitacion {

    @Id
    @Column(name = "numero_habitacion", nullable = false, length = 10)
    private String numeroHabitacion;

    @Column(name = "tipo", nullable = false, length = 40)
    private String tipo;

    @Column(name = "activa", nullable = false)
    private Boolean activa;

    @Column(name = "actualizado_en", nullable = false)
    private OffsetDateTime actualizadoEn;

    @OneToMany(mappedBy = "habitacion")
    private List<Reserva> reservas;

    @OneToMany(mappedBy = "habitacion")
    private List<Disponibilidad> disponibilidades;
}
