package it.pagopa.interop.signalhub.push.service.queue.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import it.pagopa.interop.signalhub.push.service.queue.model.SignalEvent;
import it.pagopa.interop.signalhub.push.service.utils.Utility;
import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Component
@AllArgsConstructor
public class InternalSqsProducer implements SqsProducer<SignalEvent> {
    private final SqsTemplate sqsTemplate;
    private final ObjectMapper objectMapper;


    @Override
    public Mono<SignalEvent> push(SignalEvent event) {
        Message<String> message = MessageBuilder.withPayload(convertToJson(event))
                .setHeader("correlationId", UUID.randomUUID().toString()).build();
        return Mono.fromFuture(this.sqsTemplate.sendAsync(message))
                .thenReturn(event);
    }


    private String convertToJson(SignalEvent body){
        return Utility.objectToJson(this.objectMapper, body);
    }

}
