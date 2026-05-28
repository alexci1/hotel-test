package cl.hilton.tarifas.event;

import cl.hilton.common.event.TemporadaCreatedEvent;
import cl.hilton.common.event.TemporadaDeletedEvent;
import cl.hilton.common.event.TemporadaUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TemporadaEventProducer {

    public static final String TEMPORADA_CREATED_TOPIC = "temporada.created";
    public static final String TEMPORADA_UPDATED_TOPIC = "temporada.updated";
    public static final String TEMPORADA_DELETED_TOPIC = "temporada.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCreated(TemporadaCreatedEvent event) {
        kafkaTemplate.send(TEMPORADA_CREATED_TOPIC, event.getCodigo(), event);
    }

    public void publishUpdated(TemporadaUpdatedEvent event) {
        kafkaTemplate.send(TEMPORADA_UPDATED_TOPIC, event.getCodigo(), event);
    }

    public void publishDeleted(TemporadaDeletedEvent event) {
        kafkaTemplate.send(TEMPORADA_DELETED_TOPIC, event.getCodigo(), event);
    }
}
