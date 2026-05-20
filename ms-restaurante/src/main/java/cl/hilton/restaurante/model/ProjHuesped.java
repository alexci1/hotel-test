package cl.hilton.restaurante.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
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
    @Column(name = "email", nullable = false, length = 120)
    private String email;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @Column(name = "numero_habitacion", length = 10)
    private String numeroHabitacion;

    @Column(name = "actualizado_en", nullable = false)
    private OffsetDateTime actualizadoEn;

    @OneToMany(mappedBy = "huesped")
    @Builder.Default
    private List<Pedido> pedidos = new ArrayList<>();
}
