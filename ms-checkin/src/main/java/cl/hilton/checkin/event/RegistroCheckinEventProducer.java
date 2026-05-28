package cl.hilton.checkin.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class RegistroCheckinEventProducer {

    public static final String CHECKIN_CREATED_TOPIC = "checkin.created";
    public static final String CHECKIN_UPDATED_TOPIC = "checkin.updated";
    public static final String CHECKIN_REMOVED_TOPIC = "checkin.removed";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public RegistroCheckinEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishCreated(String key, Object event) {
        kafkaTemplate.send(CHECKIN_CREATED_TOPIC, key, event);
    }

    public void publishUpdated(String key, Object event) {
        kafkaTemplate.send(CHECKIN_UPDATED_TOPIC, key, event);
    }

    public void publishRemoved(String key, Object event) {
        kafkaTemplate.send(CHECKIN_REMOVED_TOPIC, key, event);
    }
}
