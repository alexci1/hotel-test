package cl.hilton.huespedes.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "preferencia",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_preferencia_email_huesped",
            columnNames = "email_huesped"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Preferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(
        name = "email_huesped",
        referencedColumnName = "email",
        nullable = false
    )
    private Huesped huesped;

    @Column(name = "piso_preferido")
    private Long pisoPreferido;

    @Column(name = "tipo_cama", length = 30)
    private String tipoCama;

    @Column(name = "alergias", length = 255)
    private String alergias;

    @Column(name = "observaciones", length = 255)
    private String observaciones;
}