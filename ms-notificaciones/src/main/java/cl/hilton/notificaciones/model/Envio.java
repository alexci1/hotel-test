package cl.hilton.notificaciones.model;

import jakarta.persistence.*;
import lombok.*;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "notificacion_id", nullable = false)
    private Notificacion notificacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "intentos", nullable = false)
    private Integer intentos;

    @Column(name = "enviado_en")
    private String enviadoEn;

    @Lob
    @Column(name = "error_msg")
    private String errorMsg;
}