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
import java.util.List;


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
        List<EService> entity =
                eServiceRepository.findByProducerIdAndEServiceIdAndState(
                        correctEservice,
                        correctProducer,
                        incorrectState).collectList().block();

        Assertions.assertTrue(entity.isEmpty());
    }


    @Test
    void whenFindOrganizationWithCorrectParamThenReturnEntity(){
        List<EService> entity =
                eServiceRepository.findByProducerIdAndEServiceIdAndState(
                        correctEservice,
                        correctProducer,
                        correctState).collectList().block();

        Assertions.assertFalse(entity.isEmpty());
        Assertions.assertEquals(entity.get(0), getEntity());
    }


    private EService getEntity(){
        EService entity = new EService();
        entity.setEserviceId(correctEservice);
        entity.setProducerId(correctProducer);
        entity.setState(correctState);
        entity.setTmstInsert(Timestamp.from(Instant.now()));
        return entity;
    }

}