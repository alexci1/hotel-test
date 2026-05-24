package cl.hilton.huespedes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "preferencias",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_preferencias_email_huesped", columnNames = "email_huesped")
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
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "email_huesped", referencedColumnName = "email", nullable = false, unique = true)
    private Huesped huesped;

    @Column(name = "piso_preferido")
    private Integer pisoPreferido;

    @Column(name = "tipo_cama", length = 30)
    private String tipoCama;

    @Column(name = "alergias", length = 255)
    private String alergias;

    @Column(name = "observaciones", length = 255)
    private String observaciones;
}
