package cl.hilton.autenticacion.event;

import cl.hilton.common.event.SesionCreatedEvent;
import cl.hilton.common.event.SesionDeletedEvent;
import cl.hilton.common.event.SesionUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SesionEventProducer {

    public static final String SESION_CREATED_TOPIC = "sesion.created";
    public static final String SESION_UPDATED_TOPIC = "sesion.updated";
    public static final String SESION_DELETED_TOPIC = "sesion.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCreated(SesionCreatedEvent event) {
        kafkaTemplate.send(SESION_CREATED_TOPIC, event.getUsuarioEmail(), event);
    }

    public void publishUpdated(SesionUpdatedEvent event) {
        kafkaTemplate.send(SESION_UPDATED_TOPIC, event.getUsuarioEmail(), event);
    }

    public void publishDeleted(SesionDeletedEvent event) {
        kafkaTemplate.send(SESION_DELETED_TOPIC, event.getUsuarioEmail(), event);
    }
}
