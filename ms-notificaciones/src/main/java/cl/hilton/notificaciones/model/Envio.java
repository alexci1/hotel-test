package cl.hilton.notificaciones.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(
    name = "envio",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_envio_notificacion_id", columnNames = "notificacion_id")
    },
    indexes = {
        @Index(name = "idx_envio_estado", columnList = "estado")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Envio {

    public enum Estado {
        PENDIENTE, ENVIADO, FALLIDO, RECHAZADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "notificacion_id", nullable = false)
    private Notificacion notificacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private Estado estado;

    @Column(name = "intentos", nullable = false)
    private Short intentos;

    @Column(name = "enviado_en")
    private OffsetDateTime enviadoEn;

    @Lob
    @Column(name = "error_msg")
    private String errorMsg;
}
