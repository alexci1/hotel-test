package cl.hilton.inventario.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "movimiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "codigo_producto",
        referencedColumnName = "codigo_producto",
        nullable = false
    )
    private Producto producto;

    @Column(name = "tipo", nullable = false, length = 20)
    private String tipo;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "motivo", length = 100)
    private String motivo;

    @Column(name = "registrado_por", nullable = false, length = 120)
    private String registradoPor;

    @Column(name = "registrado_en", nullable = false)
    private OffsetDateTime registradoEn;
}