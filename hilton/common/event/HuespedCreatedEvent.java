package cl.hilton.common.event;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HuespedCreatedEvent implements HuespedEvent {

    private Long id;
    private String email;
    private String nombreCompleto;
}
