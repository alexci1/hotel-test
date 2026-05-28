package cl.hilton.checkin.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CheckoutEventProducer {

    public static final String CHECKOUT_CREATED_TOPIC = "checkout.created";
    public static final String CHECKOUT_UPDATED_TOPIC = "checkout.updated";
    public static final String CHECKOUT_DELETED_TOPIC = "checkout.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CheckoutEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishCreated(String key, Object event) {
        kafkaTemplate.send(CHECKOUT_CREATED_TOPIC, key, event);
    }

    public void publishUpdated(String key, Object event) {
        kafkaTemplate.send(CHECKOUT_UPDATED_TOPIC, key, event);
    }

    public void publishDeleted(String key, Object event) {
        kafkaTemplate.send(CHECKOUT_DELETED_TOPIC, key, event);
    }
}
