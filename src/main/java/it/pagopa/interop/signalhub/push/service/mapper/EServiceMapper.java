package it.pagopa.interop.signalhub.push.service.mapper;

import it.pagopa.interop.signalhub.push.service.entities.EService;
import it.pagopa.interop.signalhub.push.service.cache.model.EServiceCache;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EServiceMapper {

    EService toEntity(EServiceCache fromCache);

    EServiceCache toCache(EService fromEntity);

}
