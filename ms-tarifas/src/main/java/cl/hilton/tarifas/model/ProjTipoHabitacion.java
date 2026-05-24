package cl.hilton.tarifas.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "proj_tipos_habitacion")
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
    private Integer capacidadMax;

    @Column(name = "actualizado_en", nullable = false)
    private LocalDate actualizadoEn;

    @OneToMany(mappedBy = "tipoHabitacion")
    @Builder.Default
    private List<Tarifa> tarifas = new ArrayList<>();
}
