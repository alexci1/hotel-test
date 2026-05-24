package cl.hilton.autenticacion.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "sesiones",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_sesiones_token_hash", columnNames = "token_hash")
    },
    indexes = {
        @Index(name = "idx_sesiones_usuario", columnList = "email_usuario"),
        @Index(name = "idx_sesiones_expira",  columnList = "expira_en"),
        @Index(name = "idx_sesiones_activa",  columnList = "invalidada")
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
    @JoinColumn(name = "usuario_email", referencedColumnName = "email", nullable = false)
    private Usuario usuario;

    @Column(name = "token_hash", nullable = false, length = 255,unique = true)
    private String tokenHash;

    @Column(name = "ip_origen", nullable = false, length = 45)
    private String ipOrigen;

    @Column(name = "user_agent", length = 250)
    private String userAgent;

    @Column(name = "expira_en", nullable = false)
    private LocalDate expiraEn;

    @Column(name = "creada_en", nullable = false)
    private LocalDate creadaEn;

    @Column(name = "invalidada", nullable = false)
    private Boolean invalidada;
}