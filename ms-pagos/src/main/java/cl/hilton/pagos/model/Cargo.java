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
@Table(name = "cargos")
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
    private LocalDate registradoEn;
}
