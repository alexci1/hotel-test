package cl.hilton.pagos.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pagos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "numero_factura", referencedColumnName = "numero_factura", nullable = false)
    private Factura factura;

    @Column(name = "monto_usd", nullable = false)
    private Integer montoUsd;

    @Column(name = "metodo", nullable = false, length = 30)
    private String metodo;

    @Column(name = "referencia", length = 80)
    private String referencia;

    @Column(name = "pagado_en", nullable = false)
    private LocalDate pagadoEn;
}
