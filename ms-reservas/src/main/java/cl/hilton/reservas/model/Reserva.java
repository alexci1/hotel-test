package cl.hilton.reservas.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "reserva")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "codigo_reserva", nullable = false, length = 20, unique = true)
    private String codigoReserva;

    @Column(name = "email_huesped", nullable = false, length = 120)
    private String emailHuesped;

    @Column(name = "numero_habitacion", nullable = false, length = 10)
    private String numeroHabitacion;

    @Column(name = "fecha_entrada", nullable = false)
    private LocalDate fechaEntrada;

    @Column(name = "fecha_salida", nullable = false)
    private LocalDate fechaSalida;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "creado_en", nullable = false)
    private OffsetDateTime creadoEn;
}
