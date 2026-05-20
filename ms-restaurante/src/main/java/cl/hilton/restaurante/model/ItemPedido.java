package cl.hilton.restaurante.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "item_pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "numero_pedido",
        referencedColumnName = "numero_pedido",
        nullable = false
    )
    private Pedido pedido;

    @Column(name = "nombre_producto", nullable = false, length = 80)
    private String nombreProducto;

    @Column(name = "cantidad", nullable = false)
    private Short cantidad;

    @Column(name = "precio_unit_usd", nullable = false, precision = 8, scale = 2)
    private BigDecimal precioUnitUsd;

    @Lob
    @Column(name = "observacion")
    private String observacion;
}
