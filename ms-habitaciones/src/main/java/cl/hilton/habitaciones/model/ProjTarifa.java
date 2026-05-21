package cl.hilton.habitaciones.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "proj_tarifa")
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
    private BigDecimal precioBaseUsd;

    @Column(name = "actualizado_en", nullable = false)
    private OffsetDateTime actualizadoEn;
}
