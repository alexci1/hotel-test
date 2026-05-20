package cl.hilton.restaurante.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "mesa",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_mesa_numero_mesa",
            columnNames = "numero_mesa"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "numero_mesa", nullable = false, length = 10)
    private String numeroMesa;

    @Column(name = "capacidad", nullable = false)
    private Short capacidad;

    @Column(name = "zona", nullable = false, length = 40)
    private String zona;

    @Column(name = "disponible", nullable = false)
    private Boolean disponible;

    @OneToMany(mappedBy = "mesa")
    @Builder.Default
    private List<Pedido> pedidos = new ArrayList<>();
}

