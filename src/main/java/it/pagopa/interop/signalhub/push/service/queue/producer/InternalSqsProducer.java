package it.pagopa.interop.signalhub.push.service.queue.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import it.pagopa.interop.signalhub.push.service.queue.model.SignalEvent;
import it.pagopa.interop.signalhub.push.service.utils.Utility;
import lombok.AllArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class InternalSqsProducer implements SqsProducer<SignalEvent> {
    private SqsTemplate sqsTemplate;
    private final ObjectMapper objectMapper;


    @Override
    public Mono<SignalEvent> push(SignalEvent event) {
        return Mono.fromFuture(this.sqsTemplate.sendAsync(
                MessageBuilder.withPayload(convertToJson(event))
        )).thenReturn(event);
    }


    private String convertToJson(SignalEvent body){
        return Utility.objectToJson(this.objectMapper, body);
    }

}
