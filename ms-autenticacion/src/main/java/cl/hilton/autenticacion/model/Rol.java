package cl.hilton.autenticacion.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "roles",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_roles_codigo", columnNames = "codigo")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "codigo", nullable = false, length = 30)
    private String codigo;

    @Column(name = "descripcion", length = 100)
    private String descripcion;

    @Column(name = "activo", nullable = false)
    private Boolean activo;
}
