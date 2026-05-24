package cl.hilton.notificaciones.model;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notificaciones")
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

    @Column(name = "payload_json", length = 500)
    private String payloadJson;

    @Column(name = "creado_en", nullable = false)
    private LocalDate creadoEn;

    @OneToOne(mappedBy = "notificacion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Envio envio;
}
