package cl.hilton.restaurante.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MesaRestauranteEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public MesaRestauranteEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishCreated(String key, Object event) {
        kafkaTemplate.send("mesa-restaurante.created", key, event);
    }

    public void publishUpdated(String key, Object event) {
        kafkaTemplate.send("mesa-restaurante.updated", key, event);
    }

    public void publishDeleted(String key, Object event) {
        kafkaTemplate.send("mesa-restaurante.deleted", key, event);
    }
}
