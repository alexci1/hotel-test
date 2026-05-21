package cl.hilton.pagos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "pago",
    indexes = {
        @Index(name = "idx_pago_factura", columnList = "numero_factura"),
        @Index(name = "idx_pago_metodo",  columnList = "metodo")
    }
)
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
    private String pagadoEn;
}