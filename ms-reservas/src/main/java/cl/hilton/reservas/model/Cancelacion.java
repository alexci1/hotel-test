package cl.hilton.reservas.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "cancelacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cancelacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "codigo_reserva", referencedColumnName = "codigo_reserva", nullable = false, unique = true)
    private Reserva reserva;

    @Lob
    @Column(name = "motivo")
    private String motivo;

    @Column(name = "cancelado_por", length = 80)
    private String canceladoPor;

    @Column(name = "cancelado_en", nullable = false)
    private OffsetDateTime canceladoEn;

    @Column(name = "penalidad_usd", nullable = false)
    private BigDecimal penalidadUsd;
}
