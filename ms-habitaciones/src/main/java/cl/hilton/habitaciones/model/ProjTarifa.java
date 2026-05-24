package cl.hilton.habitaciones.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "proj_tarifas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjTarifa {

    @Id
    @Column(name = "tipo_habitacion", nullable = false, length = 40)
    private String tipoHabitacion;

    @Column(name = "precio_base_usd", nullable = false)
    private Integer precioBaseUsd;

    @Column(name = "actualizado_en", nullable = false)
    private LocalDate actualizadoEn;
}
