package it.pagopa.interop.signalhub.push.service.exception;

import lombok.Getter;

@Getter
public enum ExceptionTypeEnum {

    CORRESPONDENCE_NOT_FOUND("CORRESPONDENCE_NOT_FOUND", "Non risulta corrispondenza tra l'erogatore e l'id del servizio: "),
    JWT_NOT_VALID("JWT_NOT_VALID", "Il vaucher passato non è valido"),
    JWT_TYPE_INCORRECT("JWT_TYPE_INCORRECT", "Il vaucher passato non è del tipo atteso"),
    JWT_EMPTY("JWT_EMPTY", "Il vaucher passato è vuoto"),
    JWT_NOT_PRESENT("JWT_NOT_PRESENT", "Il vaucher non è stato passato"),
    GENERIC_ERROR("GENERIC_ERROR", "Si è verificato un errore interno"),
    SIGNALID_ALREADY_EXISTS("SIGNALID_ALREADY_EXISTS", "Esiste già un signalId per il determinato servizio");

    private final String title;
    private final String message;


    ExceptionTypeEnum(String title, String message) {
        this.title = title;
        this.message = message;
    }
}
