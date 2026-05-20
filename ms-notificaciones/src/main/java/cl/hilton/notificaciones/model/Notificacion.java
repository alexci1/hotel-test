package cl.hilton.notificaciones.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(
    name = "notificacion",
    indexes = {
        @Index(name = "idx_noti_huesped", columnList = "email_huesped"),
        @Index(name = "idx_noti_evento",  columnList = "evento_origen")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "codigo_plantilla", referencedColumnName = "codigo", nullable = false)
    private Plantilla plantilla;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "email_huesped", referencedColumnName = "email", nullable = false)
    private ProjHuesped huesped;

    @Column(name = "evento_origen", nullable = false, length = 80)
    private String eventoOrigen;

    @Lob
    @Column(name = "payload_json")
    private String payloadJson;

    @Column(name = "creado_en", nullable = false)
    private OffsetDateTime creadoEn;

    @OneToOne(mappedBy = "notificacion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Envio envio;
}
