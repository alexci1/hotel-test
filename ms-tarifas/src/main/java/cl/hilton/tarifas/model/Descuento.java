package cl.hilton.tarifas.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "descuentos",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_descuentos_codigo_descuento", columnNames = "codigo_descuento")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Descuento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "codigo_descuento", nullable = false, length = 30, unique = true)
    private String codigoDescuento;

    @Column(name = "descripcion", length = 100)
    private String descripcion;

    @Column(name = "porcentaje", nullable = false)
    private Integer porcentaje;

    @Column(name = "aplica_a", length = 40)
    private String aplicaA;

    @Column(name = "valido_desde", nullable = false)
    private LocalDate validoDesde;

    @Column(name = "valido_hasta", nullable = false)
    private LocalDate validoHasta;

    @Column(name = "activo", nullable = false)
    private Boolean activo;
}
