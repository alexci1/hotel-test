package cl.hilton.checkin.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
    @Column(name = "email", length = 120)
    private String email;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @Column(name = "actualizado_en", nullable = false)
    private LocalDate actualizadoEn;

    @OneToMany(mappedBy = "huesped")
    @Builder.Default
    private List<Checkin> checkins = new ArrayList<>();
}