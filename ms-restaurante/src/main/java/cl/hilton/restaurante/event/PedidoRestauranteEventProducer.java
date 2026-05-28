package cl.hilton.restaurante.event;

import cl.hilton.common.event.PedidoCreatedEvent;
import cl.hilton.common.event.PedidoDeletedEvent;
import cl.hilton.common.event.PedidoUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoRestauranteEventProducer {

    public static final String PEDIDO_CREATED_TOPIC = "pedido.created";
    public static final String PEDIDO_UPDATED_TOPIC = "pedido.updated";
    public static final String PEDIDO_DELETED_TOPIC = "pedido.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCreated(PedidoCreatedEvent event) {
        kafkaTemplate.send(PEDIDO_CREATED_TOPIC, event.getNumeroPedido(), event);
    }

    public void publishUpdated(PedidoUpdatedEvent event) {
        kafkaTemplate.send(PEDIDO_UPDATED_TOPIC, event.getNumeroPedido(), event);
    }

    public void publishDeleted(PedidoDeletedEvent event) {
        kafkaTemplate.send(PEDIDO_DELETED_TOPIC, event.getNumeroPedido(), event);
    }
}
