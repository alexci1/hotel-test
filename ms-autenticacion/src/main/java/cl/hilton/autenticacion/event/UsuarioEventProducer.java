package cl.hilton.autenticacion.event;

import cl.hilton.common.event.UsuarioCreatedEvent;
import cl.hilton.common.event.UsuarioDeletedEvent;
import cl.hilton.common.event.UsuarioUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioEventProducer {

    public static final String USUARIO_CREATED_TOPIC = "usuario.created";
    public static final String USUARIO_UPDATED_TOPIC = "usuario.updated";
    public static final String USUARIO_DELETED_TOPIC = "usuario.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCreated(UsuarioCreatedEvent event) {
        kafkaTemplate.send(USUARIO_CREATED_TOPIC, event.getEmail(), event);
    }

    public void publishUpdated(UsuarioUpdatedEvent event) {
        kafkaTemplate.send(USUARIO_UPDATED_TOPIC, event.getEmail(), event);
    }

    public void publishDeleted(UsuarioDeletedEvent event) {
        kafkaTemplate.send(USUARIO_DELETED_TOPIC, event.getEmail(), event);
    }
}
