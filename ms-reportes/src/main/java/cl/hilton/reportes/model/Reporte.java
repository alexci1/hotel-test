package cl.hilton.reportes.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "reporte",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_reporte_codigo",
            columnNames = "codigo"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "codigo", nullable = false, length = 50)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "tipo", nullable = false, length = 30)
    private String tipo;

    @Column(name = "frecuencia", nullable = false, length = 20)
    private String frecuencia;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @OneToMany(mappedBy = "reporte")
    @Builder.Default
    private List<Metrica> metricas = new ArrayList<>();
}
