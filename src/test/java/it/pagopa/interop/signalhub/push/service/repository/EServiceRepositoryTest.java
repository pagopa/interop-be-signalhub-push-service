package it.pagopa.interop.signalhub.push.service.repository;

import it.pagopa.interop.signalhub.push.service.config.BaseTest;
import it.pagopa.interop.signalhub.push.service.entities.EService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


class EServiceRepositoryTest extends BaseTest.WithR2DBC {

    private static final String correctEservice = "BC-eservice";
    private static final String correctProducer = "BC-producer";
    private static final String correctDescriptor = "BC-descriptor";
    private static final String correctState = "PUBLISHED";
    private static final String incorrectState = "ACTIVE";
    @Autowired
    private EServiceRepository eServiceRepository;


    @Test
    void whenFindOrganizationWithBadlyParamThenReturnNull(){
        List<EService> entity =
                eServiceRepository.findByProducerIdAndEServiceIdAndState(
                        correctEservice,
                        correctProducer,
                        incorrectState).collectList().block();

        Assertions.assertNotNull(entity);
        Assertions.assertTrue(entity.isEmpty());
    }


    @Test
    void whenFindOrganizationWithCorrectParamThenReturnEntity(){
        List<EService> entity =
                eServiceRepository.findByProducerIdAndEServiceIdAndState(
                        correctEservice,
                        correctProducer,
                        correctState).collectList().block();

        Assertions.assertNotNull(entity);
        Assertions.assertFalse(entity.isEmpty());
        Assertions.assertEquals(getEntity(), entity.get(0));
    }


    private EService getEntity(){
        EService entitySaved = new EService();
        entitySaved.setEserviceId(correctEservice);
        entitySaved.setProducerId(correctProducer);
        entitySaved.setDescriptorId(correctDescriptor);
        entitySaved.setState(correctState);
        entitySaved.setEventId(12L);
        return entitySaved;
    }

}