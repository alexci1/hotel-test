package cl.hilton.checkin.event;

import cl.hilton.common.event.CheckoutCreatedEvent;
import cl.hilton.common.event.CheckoutDeletedEvent;
import cl.hilton.common.event.CheckoutUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckoutEventProducer {

    public static final String CHECKOUT_CREATED_TOPIC = "checkout.created";
    public static final String CHECKOUT_UPDATED_TOPIC = "checkout.updated";
    public static final String CHECKOUT_DELETED_TOPIC = "checkout.deleted";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishCreated(CheckoutCreatedEvent event) {
        kafkaTemplate.send(CHECKOUT_CREATED_TOPIC, event.getCodigoReserva(), event);
    }

    public void publishUpdated(CheckoutUpdatedEvent event) {
        kafkaTemplate.send(CHECKOUT_UPDATED_TOPIC, event.getCodigoReserva(), event);
    }

    public void publishDeleted(CheckoutDeletedEvent event) {
        kafkaTemplate.send(CHECKOUT_DELETED_TOPIC, event.getCodigoReserva(), event);
    }
}
