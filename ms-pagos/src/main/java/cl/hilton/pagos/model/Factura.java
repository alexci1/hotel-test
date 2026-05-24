package cl.hilton.pagos.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    name = "facturas",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_facturas_numero_factura", columnNames = "numero_factura"),
        @UniqueConstraint(name = "uk_facturas_codigo_reserva", columnNames = "codigo_reserva")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "numero_factura", nullable = false, length = 20, unique = true)
    private String numeroFactura;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "codigo_reserva", referencedColumnName = "codigo_reserva", nullable = false, unique = true)
    private ProjReserva reserva;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "email_huesped", referencedColumnName = "email", nullable = false)
    private ProjHuesped huesped;

    @Column(name = "total_usd", nullable = false)
    private Integer totalUsd;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "emitida_en", nullable = false)
    private LocalDate emitidaEn;

    @OneToMany(mappedBy = "factura", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Pago> pagos = new ArrayList<>();

    @OneToMany(mappedBy = "factura", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Cargo> cargos = new ArrayList<>();
}
