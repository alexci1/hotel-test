package cl.hilton.autenticacion.event;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class UsuarioEventProducer {

    public static final String USUARIO_CREATED_TOPIC = "usuario.created";
    public static final String USUARIO_UPDATED_TOPIC = "usuario.updated";
    public static final String USUARIO_DELETED_TOPIC = "usuario.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public UsuarioEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishCreated(String key, Object event) {
        kafkaTemplate.send(USUARIO_CREATED_TOPIC, key, event);
    }

    public void publishUpdated(String key, Object event) {
        kafkaTemplate.send(USUARIO_UPDATED_TOPIC, key, event);
    }

    public void publishDeleted(String key, Object event) {
        kafkaTemplate.send(USUARIO_DELETED_TOPIC, key, event);
    }
}
