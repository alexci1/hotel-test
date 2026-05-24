package cl.hilton.restaurante.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "mesas",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_mesas_numero_mesa", columnNames = "numero_mesa")
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
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "numero_mesa", nullable = false, length = 10, unique = true)
    private String numeroMesa;

    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @Column(name = "zona", nullable = false, length = 40)
    private String zona;

    @Column(name = "disponible", nullable = false)
    private Boolean disponible;

    @OneToMany(mappedBy = "mesa")
    @Builder.Default
    private List<Pedido> pedidos = new ArrayList<>();
}
