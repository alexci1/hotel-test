package cl.hilton.habitaciones.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TipoHabitacionEventProducer {

    public static final String TIPO_HABITACION_CREATED_TOPIC = "tipo-habitacion.created";
    public static final String TIPO_HABITACION_UPDATED_TOPIC = "tipo-habitacion.updated";
    public static final String TIPO_HABITACION_DELETED_TOPIC = "tipo-habitacion.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public TipoHabitacionEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishCreated(String key, Object event) {
        kafkaTemplate.send(TIPO_HABITACION_CREATED_TOPIC, key, event);
    }

    public void publishUpdated(String key, Object event) {
        kafkaTemplate.send(TIPO_HABITACION_UPDATED_TOPIC, key, event);
    }

    public void publishDeleted(String key, Object event) {
        kafkaTemplate.send(TIPO_HABITACION_DELETED_TOPIC, key, event);
    }
}
