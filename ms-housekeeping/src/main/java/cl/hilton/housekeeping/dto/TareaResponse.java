package cl.hilton.housekeeping.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TareaResponse {

    private Long id;
    private String codigo;
    private String descripcion;
    private Integer duracionMin;
    private Boolean activa;
}