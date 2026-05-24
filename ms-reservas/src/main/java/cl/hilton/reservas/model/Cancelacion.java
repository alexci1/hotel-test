package cl.hilton.reservas.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "cancelaciones",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_cancelaciones_codigo_reserva", columnNames = "codigo_reserva")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cancelacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "codigo_reserva", referencedColumnName = "codigo_reserva", nullable = false, unique = true)
    private Reserva reserva;

    @Column(name = "motivo", length = 200)
    private String motivo;

    @Column(name = "cancelado_por", length = 80)
    private String canceladoPor;

    @Column(name = "cancelado_en", nullable = false)
    private LocalDate canceladoEn;

    @Column(name = "penalidad_usd", nullable = false)
    private Integer penalidadUsd;
}
