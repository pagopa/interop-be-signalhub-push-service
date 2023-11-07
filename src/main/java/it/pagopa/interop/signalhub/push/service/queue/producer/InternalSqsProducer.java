package it.pagopa.interop.signalhub.push.service.queue.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import it.pagopa.interop.signalhub.push.service.queue.model.SignalEvent;
import it.pagopa.interop.signalhub.push.service.utils.Utility;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Slf4j
@Component
@AllArgsConstructor
public class InternalSqsProducer implements SqsProducer<SignalEvent> {
    private final SqsTemplate sqsTemplate;
    private final ObjectMapper objectMapper;


    @Override
    public Mono<SignalEvent> push(SignalEvent event) {
        String correlationId = UUID.randomUUID().toString();
        Message<String> message = MessageBuilder.withPayload(convertToJson(event))
                .setHeader("correlationId", correlationId).build();
        log.info("[{} - {}] Pushing signal on internal queue", event.getIndexSignal(), correlationId);
        return Mono.fromFuture(this.sqsTemplate.sendAsync(message))
                .doOnNext(response -> log.info("[{} - {}] Signal pushed on internal queue", event.getIndexSignal(), correlationId))
                .thenReturn(event);
    }


    private String convertToJson(SignalEvent body){
        return Utility.objectToJson(this.objectMapper, body);
    }

}
