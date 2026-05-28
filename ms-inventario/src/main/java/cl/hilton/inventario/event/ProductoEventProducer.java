package cl.hilton.inventario.event;

import cl.hilton.common.event.ProductoCreatedEvent;
import cl.hilton.common.event.ProductoDeletedEvent;
import cl.hilton.common.event.ProductoUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductoEventProducer {

    public static final String PRODUCTO_CREATED_TOPIC = "producto.created";
    public static final String PRODUCTO_UPDATED_TOPIC = "producto.updated";
    public static final String PRODUCTO_DELETED_TOPIC = "producto.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCreated(ProductoCreatedEvent event) {
        kafkaTemplate.send(PRODUCTO_CREATED_TOPIC, event.getCodigoProducto(), event);
    }

    public void publishUpdated(ProductoUpdatedEvent event) {
        kafkaTemplate.send(PRODUCTO_UPDATED_TOPIC, event.getCodigoProducto(), event);
    }

    public void publishDeleted(ProductoDeletedEvent event) {
        kafkaTemplate.send(PRODUCTO_DELETED_TOPIC, event.getCodigoProducto(), event);
    }
}
