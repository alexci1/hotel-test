package cl.hilton.restaurante.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PedidoRestauranteEventProducer {

    public static final String PEDIDO_CREATED_TOPIC = "pedido.created";
    public static final String PEDIDO_UPDATED_TOPIC = "pedido.updated";
    public static final String PEDIDO_DELETED_TOPIC = "pedido.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PedidoRestauranteEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishCreated(String key, Object event) {
        kafkaTemplate.send(PEDIDO_CREATED_TOPIC, key, event);
    }

    public void publishUpdated(String key, Object event) {
        kafkaTemplate.send(PEDIDO_UPDATED_TOPIC, key, event);
    }

    public void publishDeleted(String key, Object event) {
        kafkaTemplate.send(PEDIDO_DELETED_TOPIC, key, event);
    }
}
