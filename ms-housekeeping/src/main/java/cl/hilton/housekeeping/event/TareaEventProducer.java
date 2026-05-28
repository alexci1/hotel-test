package cl.hilton.housekeeping.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TareaEventProducer {

    public static final String TAREA_CREATED_TOPIC = "tarea.created";
    public static final String TAREA_UPDATED_TOPIC = "tarea.updated";
    public static final String TAREA_DELETED_TOPIC = "tarea.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public TareaEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishCreated(String key, Object event) {
        kafkaTemplate.send(TAREA_CREATED_TOPIC, key, event);
    }

    public void publishUpdated(String key, Object event) {
        kafkaTemplate.send(TAREA_UPDATED_TOPIC, key, event);
    }

    public void publishDeleted(String key, Object event) {
        kafkaTemplate.send(TAREA_DELETED_TOPIC, key, event);
    }
}
