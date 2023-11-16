package it.pagopa.interop.signalhub.push.service.repository;

import it.pagopa.interop.signalhub.push.service.config.BaseTest;
import it.pagopa.interop.signalhub.push.service.entities.Signal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.Instant;


class SignalRepositoryTest extends BaseTest.WithR2DBC {

    private static final String correctEservice = "1234";
    private static final long correctSignal = 1l;
    private static final long incorrectSignal = 0l;

    @Autowired
    private SignalRepository signalRepository;

    @BeforeEach
    void setUp(){
        signalRepository.save(getEntity());
    }

    @Test
    void whenFindOrganizationWithBadlyParamThenReturnNull(){
        Mono<Signal> entity =
                signalRepository.findBySignalIdAndEServiceId(
                        incorrectSignal,
                        correctEservice);

        Assertions.assertNull(entity);
    }


    @Test
    void whenFindOrganizationWithCorrectParamThenReturnEntity(){
        Mono<Signal> entity =
                signalRepository.findBySignalIdAndEServiceId(
                        correctSignal,
                        correctEservice);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(entity.block(), getEntity());
    }


    private Signal getEntity(){
        Signal entity = new Signal();
        entity.setEserviceId(correctEservice);
        entity.setSignalId(correctSignal);
        entity.setTmstInsert(Timestamp.from(Instant.now()));
        return entity;
    }

}