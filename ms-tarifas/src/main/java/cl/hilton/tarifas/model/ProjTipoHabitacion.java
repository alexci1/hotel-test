package cl.hilton.tarifas.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "proj_tipo_habitacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ProjTipoHabitacion {

    @Id
    @Column(name = "codigo", nullable = false, length = 40)
    private String codigo;

    @Column(name = "descripcion", length = 100)
    private String descripcion;

    @Column(name = "capacidad_max", nullable = false)
    private long capacidadMax;

    @Column(name = "actualizado_en", nullable = false)
    private OffsetDateTime actualizadoEn;

    @OneToMany(mappedBy = "tipoHabitacion")
    private List<Tarifa> tarifas;
}