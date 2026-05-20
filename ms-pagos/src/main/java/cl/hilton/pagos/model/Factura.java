package cl.hilton.pagos.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "factura",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_factura_numero",         columnNames = "numero_factura"),
        @UniqueConstraint(name = "uk_factura_codigo_reserva", columnNames = "codigo_reserva")
    },
    indexes = {
        @Index(name = "idx_factura_reserva", columnList = "codigo_reserva"),
        @Index(name = "idx_factura_estado",  columnList = "estado")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Factura {

    public enum Estado {
        PENDIENTE, PARCIAL, PAGADA, ANULADA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "numero_factura", nullable = false, length = 20)
    private String numeroFactura;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "codigo_reserva", referencedColumnName = "codigo_reserva", nullable = false)
    private ProjReserva reserva;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "email_huesped", referencedColumnName = "email", nullable = false)
    private ProjHuesped huesped;

    @Column(name = "total_usd", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalUsd;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private Estado estado;

    @Builder.Default
    @OneToMany(mappedBy = "factura", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pago> pagos = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "factura", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Cargo> cargos = new ArrayList<>();
}
