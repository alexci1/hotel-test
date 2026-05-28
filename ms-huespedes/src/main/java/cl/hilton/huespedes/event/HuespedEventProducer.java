package cl.hilton.huespedes.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class HuespedEventProducer {

    public static final String HUESPED_CREATED_TOPIC = "huesped.created";
    public static final String HUESPED_UPDATED_TOPIC = "huesped.updated";
    public static final String HUESPED_DELETED_TOPIC = "huesped.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public HuespedEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishCreated(String key, Object event) {
        kafkaTemplate.send(HUESPED_CREATED_TOPIC, key, event);
    }

    public void publishUpdated(String key, Object event) {
        kafkaTemplate.send(HUESPED_UPDATED_TOPIC, key, event);
    }

    public void publishDeleted(String key, Object event) {
        kafkaTemplate.send(HUESPED_DELETED_TOPIC, key, event);
    }
}
