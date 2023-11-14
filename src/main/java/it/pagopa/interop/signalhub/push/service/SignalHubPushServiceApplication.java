package it.pagopa.interop.signalhub.push.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@Slf4j
@SpringBootApplication
@EnableR2dbcAuditing
public class SignalHubPushServiceApplication {

	public static void main(String[] args) {

		log.error("AVAILABLE PROCESSOR: {}", Runtime.getRuntime().availableProcessors() );

		SpringApplication.run(SignalHubPushServiceApplication.class, args);
	}

}
