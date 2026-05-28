package cl.hilton.inventario.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductoEventProducer {

    public static final String PRODUCTO_CREATED_TOPIC = "producto.created";
    public static final String PRODUCTO_UPDATED_TOPIC = "producto.updated";
    public static final String PRODUCTO_DELETED_TOPIC = "producto.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ProductoEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishCreated(String key, Object event) {
        kafkaTemplate.send(PRODUCTO_CREATED_TOPIC, key, event);
    }

    public void publishUpdated(String key, Object event) {
        kafkaTemplate.send(PRODUCTO_UPDATED_TOPIC, key, event);
    }

    public void publishDeleted(String key, Object event) {
        kafkaTemplate.send(PRODUCTO_DELETED_TOPIC, key, event);
    }
}
