package cl.hilton.tarifas.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TemporadaEventProducer {

    public static final String TEMPORADA_CREATED_TOPIC = "temporada.created";
    public static final String TEMPORADA_UPDATED_TOPIC = "temporada.updated";
    public static final String TEMPORADA_DELETED_TOPIC = "temporada.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public TemporadaEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishCreated(String key, Object event) {
        kafkaTemplate.send(TEMPORADA_CREATED_TOPIC, key, event);
    }

    public void publishUpdated(String key, Object event) {
        kafkaTemplate.send(TEMPORADA_UPDATED_TOPIC, key, event);
    }

    public void publishDeleted(String key, Object event) {
        kafkaTemplate.send(TEMPORADA_DELETED_TOPIC, key, event);
    }
}
