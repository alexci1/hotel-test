package cl.hilton.reportes.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KpiResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private Integer valorActual;
    private Integer valorObjetivo;
    private String unidad;
    private String periodo;
    private LocalDate actualizadoEn;
}