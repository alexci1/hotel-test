package cl.hilton.habitaciones.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class HabitacionEventProducer {

    public static final String HABITACION_CREATED_TOPIC = "habitacion.created";
    public static final String HABITACION_UPDATED_TOPIC = "habitacion.updated";
    public static final String HABITACION_DELETED_TOPIC = "habitacion.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public HabitacionEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishCreated(String key, Object event) {
        kafkaTemplate.send(HABITACION_CREATED_TOPIC, key, event);
    }

    public void publishUpdated(String key, Object event) {
        kafkaTemplate.send(HABITACION_UPDATED_TOPIC, key, event);
    }

    public void publishDeleted(String key, Object event) {
        kafkaTemplate.send(HABITACION_DELETED_TOPIC, key, event);
    }
}
