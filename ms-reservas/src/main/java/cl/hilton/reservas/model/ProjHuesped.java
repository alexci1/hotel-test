package cl.hilton.reservas.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "proj_huesped")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjHuesped {

    @Id
    @Column(name = "email", nullable = false, length = 120)
    private String email;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "actualizado_en", nullable = false)
    private OffsetDateTime actualizadoEn;

    @OneToMany(mappedBy = "huesped")
    private List<Reserva> reservas;
}
