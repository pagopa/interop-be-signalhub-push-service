package it.pagopa.interop.signalhub.push.service.queue.producer;

import reactor.core.publisher.Mono;

public interface SqsProducer<T> {

    Mono<T> push(T event);

}
