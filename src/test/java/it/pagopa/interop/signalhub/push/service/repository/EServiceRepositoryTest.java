package it.pagopa.interop.signalhub.push.service.repository;

import it.pagopa.interop.signalhub.push.service.config.BaseTest;
import it.pagopa.interop.signalhub.push.service.entities.EService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.Instant;


class EServiceRepositoryTest extends BaseTest.WithR2DBC {

    private static final String correctEservice = "1234";
    private static final String correctProducer = "1234";
    private static final String correctState = "PUBLISHED";
    private static final String incorrectState = "ACTIVE";

    @Autowired
    private EServiceRepository eServiceRepository;

    @BeforeEach
    void setUp(){
        eServiceRepository.save(getEntity());
    }

    @Test
    void whenFindOrganizationWithBadlyParamThenReturnNull(){
        Mono<EService> entity =
                eServiceRepository.findByProducerIdAndEServiceIdAndState(
                        correctEservice,
                        correctProducer,
                        incorrectState);

        Assertions.assertNull(entity);
    }


    @Test
    void whenFindOrganizationWithCorrectParamThenReturnEntity(){
        Mono<EService> entity =
                eServiceRepository.findByProducerIdAndEServiceIdAndState(
                        correctEservice,
                        correctProducer,
                        correctState);

        Assertions.assertNotNull(entity);
        Assertions.assertEquals(entity.block(), getEntity());
    }


    private EService getEntity(){
        EService entity = new EService();
        entity.setEserviceId(correctEservice);
        entity.setProducerId(correctProducer);
        entity.setState("published");
        entity.setTmstInsert(Timestamp.from(Instant.now()));
        return entity;
    }

}