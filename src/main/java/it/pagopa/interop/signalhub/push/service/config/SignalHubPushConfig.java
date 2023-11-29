package it.pagopa.interop.signalhub.push.service.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application.eservice.push")
public class SignalHubPushConfig {
    private String id;
    private String audience;
    private String headerTraceIdKey;
}
