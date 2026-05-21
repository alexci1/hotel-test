package cl.hilton.inventario.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "producto",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_producto_codigo_producto",
            columnNames = "codigo_producto"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "codigo_producto", nullable = false, length = 30)
    private String codigoProducto;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "categoria", nullable = false, length = 40)
    private String categoria;

    @Column(name = "stock_actual", nullable = false)
    private Long stockActual;

    @Column(name = "stock_minimo", nullable = false)
    private Long stockMinimo;

    @Column(name = "unidad", nullable = false, length = 20)
    private String unidad;

    @OneToMany(mappedBy = "producto")
    @Builder.Default
    private List<Movimiento> movimientos = new ArrayList<>();

    @OneToMany(mappedBy = "producto")
    @Builder.Default
    private List<Minibar> minibares = new ArrayList<>();
}