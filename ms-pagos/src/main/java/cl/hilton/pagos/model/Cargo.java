package cl.hilton.pagos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "cargo",
    indexes = {
        @Index(name = "idx_cargo_factura", columnList = "numero_factura"),
        @Index(name = "idx_cargo_origen",  columnList = "origen")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "numero_factura", referencedColumnName = "numero_factura", nullable = false)
    private Factura factura;

    @Column(name = "concepto", nullable = false, length = 100)
    private String concepto;

    @Column(name = "monto_usd", nullable = false)
    private Integer montoUsd;

    @Column(name = "origen", nullable = false, length = 30)
    private String origen;

    @Column(name = "registrado_en", nullable = false)
    private String registradoEn;
}