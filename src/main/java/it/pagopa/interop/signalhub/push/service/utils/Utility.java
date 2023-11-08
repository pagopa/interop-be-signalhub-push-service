package it.pagopa.interop.signalhub.push.service.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.pagopa.interop.signalhub.push.service.auth.PrincipalAgreement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import reactor.core.publisher.Mono;

@Slf4j
public class Utility {

    private Utility(){
        // private constructor
    }

    public static Mono<PrincipalAgreement> getPrincipalFromSecurityContext(){
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> (PrincipalAgreement) securityContext.getAuthentication().getPrincipal());
    }

    public static <T> T jsonToObject(ObjectMapper objectMapper, String json, Class<T> tClass){
        try {
            return objectMapper.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            log.error("Error with mapping : {}", e.getMessage(), e);
            return null;
        }
    }

    public static <T> String objectToJson(ObjectMapper objectMapper, T object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error with mapping : {}", e.getMessage(), e);
            return null;
        }
    }
}
