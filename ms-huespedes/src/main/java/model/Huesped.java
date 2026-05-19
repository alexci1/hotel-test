package s.hilton.huespedes.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(
    name = "huesped",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_huesped_email", columnNames = "email")
    }
)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Huesped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, length = 120, unique = true)
    private String email;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @CreatedDate
    @Column(name = "creado_en", nullable = false, updatable = false)
    private OffsetDateTime creadoEn;

    @OneToMany(mappedBy = "huesped", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentos;

    @OneToOne(mappedBy = "huesped", cascade = CascadeType.ALL, orphanRemoval = true)
    private Preferencia preferencia;

    }