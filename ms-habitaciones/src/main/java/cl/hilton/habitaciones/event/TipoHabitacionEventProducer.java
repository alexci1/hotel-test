package cl.hilton.habitaciones.event;

import cl.hilton.common.event.TipoHabitacionCreatedEvent;
import cl.hilton.common.event.TipoHabitacionDeletedEvent;
import cl.hilton.common.event.TipoHabitacionUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TipoHabitacionEventProducer {

    public static final String TIPO_HABITACION_CREATED_TOPIC = "tipo-habitacion.created";
    public static final String TIPO_HABITACION_UPDATED_TOPIC = "tipo-habitacion.updated";
    public static final String TIPO_HABITACION_DELETED_TOPIC = "tipo-habitacion.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCreated(TipoHabitacionCreatedEvent event) {
        kafkaTemplate.send(TIPO_HABITACION_CREATED_TOPIC, event.getCodigo(), event);
    }

    public void publishUpdated(TipoHabitacionUpdatedEvent event) {
        kafkaTemplate.send(TIPO_HABITACION_UPDATED_TOPIC, event.getCodigo(), event);
    }

    public void publishDeleted(TipoHabitacionDeletedEvent event) {
        kafkaTemplate.send(TIPO_HABITACION_DELETED_TOPIC, event.getCodigo(), event);
    }
}
