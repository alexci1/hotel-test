package cl.hilton.notificaciones.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "plantilla",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_plantilla_codigo", columnNames = "codigo")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plantilla {

    public enum Canal {
        EMAIL, SMS, PUSH, WHATSAPP
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false, length = 50)
    private String codigo;

    @Enumerated(EnumType.STRING)
    @Column(name = "canal", nullable = false, length = 20)
    private Canal canal;

    @Column(name = "asunto", length = 200)
    private String asunto;

    @Lob
    @Column(name = "cuerpo", nullable = false)
    private String cuerpo;

    @Column(name = "activa", nullable = false)
    private Boolean activa;

    @Builder.Default
    @OneToMany(mappedBy = "plantilla", fetch = FetchType.LAZY)
    private List<Notificacion> notificaciones = new ArrayList<>();

}
