package cl.hilton.habitaciones.event;

import cl.hilton.common.event.HabitacionCreatedEvent;
import cl.hilton.common.event.HabitacionDeletedEvent;
import cl.hilton.common.event.HabitacionUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HabitacionEventProducer {

    public static final String HABITACION_CREATED_TOPIC = "habitacion.created";
    public static final String HABITACION_UPDATED_TOPIC = "habitacion.updated";
    public static final String HABITACION_DELETED_TOPIC = "habitacion.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCreated(HabitacionCreatedEvent event) {
        kafkaTemplate.send(HABITACION_CREATED_TOPIC, event.getNumeroHabitacion(), event);
    }

    public void publishUpdated(HabitacionUpdatedEvent event) {
        kafkaTemplate.send(HABITACION_UPDATED_TOPIC, event.getNumeroHabitacion(), event);
    }

    public void publishDeleted(HabitacionDeletedEvent event) {
        kafkaTemplate.send(HABITACION_DELETED_TOPIC, event.getNumeroHabitacion(), event);
    }
}
