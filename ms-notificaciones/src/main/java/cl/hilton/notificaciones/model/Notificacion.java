package cl.hilton.notificaciones.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;

@Entity
@Table(name = "notificacion")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_plantilla", referencedColumnName = "codigo", nullable = false)
    private Plantilla plantilla;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email_huesped", referencedColumnName = "email", nullable = false)
    private ProjHuesped huesped;

    @Column(name = "evento_origen", nullable = false, length = 80)
    private String eventoOrigen;

    @Lob
    @Column(name = "payload_json")
    private String payloadJson;

    @CreatedDate
    @Column(name = "creado_en", nullable = false, updatable = false)
    private OffsetDateTime creadoEn;

    @OneToOne(mappedBy = "notificacion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Envio envio;

}
