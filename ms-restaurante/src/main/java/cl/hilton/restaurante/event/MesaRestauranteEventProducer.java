package cl.hilton.restaurante.event;

import cl.hilton.common.event.MesaRestauranteCreatedEvent;
import cl.hilton.common.event.MesaRestauranteDeletedEvent;
import cl.hilton.common.event.MesaRestauranteUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MesaRestauranteEventProducer {

    public static final String MESA_CREATED_TOPIC = "mesa-restaurante.created";
    public static final String MESA_UPDATED_TOPIC = "mesa-restaurante.updated";
    public static final String MESA_DELETED_TOPIC = "mesa-restaurante.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCreated(MesaRestauranteCreatedEvent event) {
        kafkaTemplate.send(MESA_CREATED_TOPIC, event.getNumeroMesa(), event);
    }

    public void publishUpdated(MesaRestauranteUpdatedEvent event) {
        kafkaTemplate.send(MESA_UPDATED_TOPIC, event.getNumeroMesa(), event);
    }

    public void publishDeleted(MesaRestauranteDeletedEvent event) {
        kafkaTemplate.send(MESA_DELETED_TOPIC, event.getNumeroMesa(), event);
    }
}
