package cl.hilton.pagos.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "proj_reserva")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjReserva {

    @Id
    @Column(name = "codigo_reserva", nullable = false, length = 20)
    private String codigoReserva;

    @Column(name = "email_huesped", nullable = false, length = 120)
    private String emailHuesped;

    @Column(name = "numero_habitacion", nullable = false, length = 10)
    private String numeroHabitacion;

    @Column(name = "fecha_entrada", nullable = false)
    private LocalDate fechaEntrada;

    @Column(name = "fecha_salida", nullable = false)
    private LocalDate fechaSalida;

    @Builder.Default
    @OneToMany(mappedBy = "reserva", fetch = FetchType.LAZY)
    private List<Factura> facturas = new ArrayList<>();
}
