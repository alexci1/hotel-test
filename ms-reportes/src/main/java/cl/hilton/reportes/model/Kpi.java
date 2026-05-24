package cl.hilton.reportes.model;

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
    name = "kpi",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_kpi_nombre", columnNames = "nombre")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Kpi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "valor_actual")
    private Integer valorActual;

    @Column(name = "valor_objetivo")
    private Integer valorObjetivo;

    @Column(name = "unidad", length = 30)
    private String unidad;

    @Column(name = "periodo", nullable = false, length = 20)
    private String periodo;

    @Column(name = "actualizado_en", nullable = false)
    private LocalDate actualizadoEn;
}
