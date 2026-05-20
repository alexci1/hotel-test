package cl.hilton.restaurante.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "pedido",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_pedido_numero_pedido",
            columnNames = "numero_pedido"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "numero_pedido", nullable = false, length = 20)
    private String numeroPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "numero_mesa",
        referencedColumnName = "numero_mesa"
    )
    private Mesa mesa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "email_huesped",
        referencedColumnName = "email"
    )
    private ProjHuesped huesped;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "total_usd", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalUsd;

    @Column(name = "creado_en", nullable = false)
    private OffsetDateTime creadoEn;

    @OneToMany(
        mappedBy = "pedido",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @Builder.Default
    private List<ItemPedido> items = new ArrayList<>();
}

