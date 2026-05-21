package cl.hilton.tarifas.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "descuento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Descuento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "codigo_descuento", nullable = false, length = 30, unique = true)
    private String codigoDescuento;

    @Column(name = "descripcion", length = 100)
    private String descripcion;

    @Column(name = "porcentaje", nullable = false)
    private BigDecimal porcentaje;

    @Column(name = "aplica_a", length = 40)
    private String aplicaA;

    @Column(name = "valido_desde", nullable = false)
    private LocalDate validoDesde;

    @Column(name = "valido_hasta", nullable = false)
    private LocalDate validoHasta;

    @Column(name = "activo", nullable = false)
    private Boolean activo;
}
