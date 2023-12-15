package it.pagopa.interop.signalhub.push.service.mapper;

import it.pagopa.interop.signalhub.push.service.auth.PrincipalAgreement;
import it.pagopa.interop.signalhub.push.service.dto.ProblemError;
import it.pagopa.interop.signalhub.push.service.generated.openapi.client.interop.model.v1.Agreement;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.validation.FieldError;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProblemErrorMapperTest {

    private ProblemErrorMapper problemErrorMapper = Mappers.getMapper(ProblemErrorMapper.class);


    @Test
    void toProblemError() {
        FieldError fieldError= new FieldError("objectName", "field", "defaultMessage");
        ProblemError problemError= problemErrorMapper.toProblemError(fieldError);
        assertEquals(problemError.getCode(), fieldError.getField());

    }
}