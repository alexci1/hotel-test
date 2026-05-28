package cl.hilton.housekeeping.event;

import cl.hilton.common.event.TareaCreatedEvent;
import cl.hilton.common.event.TareaDeletedEvent;
import cl.hilton.common.event.TareaUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TareaEventProducer {

    public static final String TAREA_CREATED_TOPIC = "tarea.created";
    public static final String TAREA_UPDATED_TOPIC = "tarea.updated";
    public static final String TAREA_DELETED_TOPIC = "tarea.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCreated(TareaCreatedEvent event) {
        kafkaTemplate.send(TAREA_CREATED_TOPIC, event.getCodigo(), event);
    }

    public void publishUpdated(TareaUpdatedEvent event) {
        kafkaTemplate.send(TAREA_UPDATED_TOPIC, event.getCodigo(), event);
    }

    public void publishDeleted(TareaDeletedEvent event) {
        kafkaTemplate.send(TAREA_DELETED_TOPIC, event.getCodigo(), event);
    }
}
