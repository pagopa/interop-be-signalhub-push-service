package it.pagopa.interop.signalhub.push.service.repository;

import it.pagopa.interop.signalhub.push.service.config.BaseTest;
import it.pagopa.interop.signalhub.push.service.entities.Signal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.Instant;


class SignalRepositoryTest extends BaseTest.WithR2DBC {

    private static final String correctEservice = "BC-eservice";
    private static final long correctSignal = 1l;
    private static final long incorrectSignal = 0l;

    @Autowired
    private SignalRepository signalRepository;


    @Test
    void whenFindOrganizationWithBadlyParamThenReturnNull(){
        Signal entity =
                signalRepository.findBySignalIdAndEServiceId(
                        incorrectSignal,
                        correctEservice).block();

        Assertions.assertNull(entity);
    }


    @Test
    void whenFindOrganizationWithCorrectParamThenReturnEntity(){
        Signal entity =
                signalRepository.findBySignalIdAndEServiceId(
                        correctSignal,
                        correctEservice).block();

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(entity, getEntity());
    }


    private Signal getEntity(){
        Signal entity = new Signal();
        entity.setEserviceId(correctEservice);
        entity.setSignalId(correctSignal);
        entity.setTmstInsert(Instant.now());
        return entity;
    }

}