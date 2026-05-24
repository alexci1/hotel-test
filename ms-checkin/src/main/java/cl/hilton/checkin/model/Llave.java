package cl.hilton.checkin.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
    name = "llave",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_llave_codigo_llave",
            columnNames = "codigo_llave"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Llave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_habitacion", nullable = false, length = 10)
    private String numeroHabitacion;

    @Column(name = "codigo_llave", nullable = false, length = 40)
    private String codigoLlave;

    @Column(name = "activa", nullable = false)
    private Boolean activa;

    @ManyToOne
    @JoinColumn(
        name = "codigo_reserva",
        referencedColumnName = "codigo_reserva"
    )
    private ProjReserva reserva;

    @Column(name = "emitida_en", nullable = false)
    private LocalDate emitidaEn;
}