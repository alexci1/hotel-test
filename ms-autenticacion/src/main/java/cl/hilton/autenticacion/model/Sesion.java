package cl.hilton.autenticacion.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "sesion",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_sesion_token_hash", columnNames = "token_hash")
    },
    indexes = {
        @Index(name = "idx_sesion_usuario", columnList = "email_usuario"),
        @Index(name = "idx_sesion_expira",  columnList = "expira_en"),
        @Index(name = "idx_sesion_activa",  columnList = "invalidada")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "email_usuario", referencedColumnName = "email", nullable = false)
    private Usuario usuario;

    @Column(name = "token_hash", nullable = false, length = 255)
    private String tokenHash;

    @Column(name = "ip_origen", nullable = false, length = 45)
    private String ipOrigen;

    @Column(name = "user_agent", length = 250)
    private String userAgent;

    @Column(name = "expira_en", nullable = false)
    private String expiraEn;

    @Column(name = "invalidada", nullable = false)
    private String invalidada;
}