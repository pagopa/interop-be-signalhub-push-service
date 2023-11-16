package it.pagopa.interop.signalhub.push.service.repository;

import it.pagopa.interop.signalhub.push.service.config.BaseTest;
import it.pagopa.interop.signalhub.push.service.entities.EService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;


class EServiceRepositoryTest extends BaseTest.WithR2DBC {

    private static final String correctEservice = "1234";
    private static final String correctProducer = "1234";
    private static final String correctState = "PUBLISHED";
    private static final String incorrectState = "ACTIVE";
    private EService entitySaved;

    @Autowired
    private EServiceRepository eServiceRepository;

    @BeforeEach
    void setUp(){
        eServiceRepository.save(getEntity()).block();
    }

    @AfterEach
    void after(){
        eServiceRepository.deleteAll().block();
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
        Assertions.assertEquals(entitySaved, entity.get(0));
    }


    private EService getEntity(){
        entitySaved = new EService();
        entitySaved.setEserviceId(correctEservice);
        entitySaved.setProducerId(correctProducer);
        entitySaved.setDescriptorId("1234");
        entitySaved.setState(correctState);
        entitySaved.setTmstInsert(Instant.now());
        entitySaved.setTmstLastEdit(Instant.now());
        return entitySaved;
    }

}