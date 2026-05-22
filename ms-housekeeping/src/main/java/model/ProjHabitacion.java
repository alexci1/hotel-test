package cl.hilton.housekeeping.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
    @Column(name = "numero_habitacion", length = 10)
    private String numeroHabitacion;

    @Column(name = "tipo", nullable = false, length = 40)
    private String tipo;

    @Column(name = "piso", nullable = false)
    private Long piso;

    @Column(name = "actualizado_en", nullable = false)
    private LocalDate actualizadoEn;

    @OneToMany(mappedBy = "habitacion")
    @Builder.Default
    private List<Asignacion> asignaciones = new ArrayList<>();
}